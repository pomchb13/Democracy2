package handler;

import beans.CandidateData;
import beans.ElectionData;
import contracts.ElectionContract;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import util.BlockchainUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:          Patrick Windegger
 * Created on:
 * Description:     Class responsible for handling an election.
 * All methods of the contract are implemented in this handler.
 * The communication between java and the Blockchain is also implemented here.
 */
public class ElectionHandler {

    private Web3j web3;
    private Credentials credentials;
    private ElectionContract election;

    /**
     * Constructor to initiliaze the web3j web-service
     *
     * @param credentials: credentials of the user (wallet file)
     */
    public ElectionHandler(Credentials credentials) {
        web3 = Web3j.build(new HttpService());
        this.credentials = credentials;
    }


    /**
     * Method responsible for creating a new election contract
     *
     * @param numCandidates: number of candidates of the election
     * @param title:         title of the election
     * @param dateFrom:      start date of the election
     * @param dateDue:       end date of the election
     * @param showDiagram:   boolean if a diagram is shown on the web page
     * @return address of the contract
     * @throws Exception if an error occurs
     */
    //TODO: souts entfernen
    public String createContract(int numCandidates, String title, LocalDate dateFrom, LocalDate dateDue, boolean showDiagram) throws Exception {
        BigInteger dateFromInMilliseconds = new BigInteger(dateFrom.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + "");
        BigInteger dateDueInMilliseconds = new BigInteger(dateDue.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + "");
        election = ElectionContract.deploy(web3, credentials, BigInteger.ZERO, new BigInteger("4700000"), new BigInteger(numCandidates + ""), title, dateFromInMilliseconds, dateDueInMilliseconds, showDiagram).send();
        return election.getContractAddress();
    }

    /**
     * Method responsible for loading an existing contract with a specific address
     *
     * @param contractAddress: address of the contract
     * @return address of the contract
     */
    public String loadSmartContract(Address contractAddress) {
        election = ElectionContract.load(contractAddress.toString(), web3, credentials, new BigInteger("300000"), new BigInteger("4700000"));
        return election.getContractAddress();
    }

    /**
     * Method responsible for giving the voter the right to vote for an election
     *
     * @param voter: address of the voter
     * @throws Exception if the election contract is not loaded
     */
    public void giveRightToVote(Address voter) throws Exception {
        if (election != null) {
            election.giveRightToVote(voter.toString()).send();
        } else {
            throw new Exception("election object is null!");
        }
    }

    /**
     * Method responsible for voting for a specific candidate
     *
     * @param candidate: index of the candidate
     * @param voter:     address of the voter
     * @throws Exception if the election contract is not loaded
     */
    public void vote(Uint8 candidate, Address voter) throws Exception {
        if (election != null) {
            election.vote(candidate.getValue(), voter.toString()).send();
        } else {
            throw new Exception("election object is null!");
        }
    }


    /**
     * Method responsible for getting the winner or winners of an election
     *
     * @param allCandidates: list of all candidates of the election
     * @return
     */
    public List<CandidateData> getWinners(List<CandidateData> allCandidates) {
        List<CandidateData> winners = new ArrayList<>();
        int winningVoteCount = 0;
        for (int i = 0; i < allCandidates.size(); i++) {
            if (allCandidates.get(i).getVoteCount() > winningVoteCount) {
                winningVoteCount = allCandidates.get(i).getVoteCount();
            }
        }

        for (int i = 0; i < allCandidates.size(); i++) {
            if (allCandidates.get(i).getVoteCount() == winningVoteCount) {
                winners.add(allCandidates.get(i));
            }
        }

        return winners;
    }


    /**
     * Method responsible for storing a candidate in the Ethereum blockchain
     *
     * @param candidateIndex: index of the candidate
     * @param title:          academic title of the candidate
     * @param firstname:      firstname of the candidate
     * @param lastname:       lastname of the candidate
     * @param birthday:       birthday of the candidate
     * @param party:          party of the candidate
     * @param slogan:         slogan of the candidate
     * @param imagePath:      path to the portrait of the candidate
     * @throws Exception if the election contract is not loaded
     */
    public void storeCandidateData(int candidateIndex, String title, String firstname, String lastname, LocalDate birthday, String party, String slogan, String imagePath) throws Exception {
        if (election != null) {
            BigInteger birthdayInMilliseconds = new BigInteger(birthday.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + "");
            election.storeCandidateData(new BigInteger(candidateIndex + ""), title, firstname, lastname, birthdayInMilliseconds, party, slogan, imagePath).send();
        } else {
            throw new Exception("election object is null!");
        }
    }

    /**
     * Method responsible for getting the data of an election
     *
     * @return ElectionData object
     * @throws Exception if the election contract is not loaded
     */
    public ElectionData getElectionData() throws Exception {
        if (election != null) {
            String title = election.getElectionData().send().getValue1();
            BigInteger dateFrom = election.getElectionData().send().getValue2();
            BigInteger dateDue = election.getElectionData().send().getValue3();
            Boolean showDiagram = election.getElectionData().send().getValue4();
            LocalDate ldDateFrom = Instant.ofEpochMilli(dateFrom.longValue()).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate ldDateDue = Instant.ofEpochMilli(dateDue.longValue()).atZone(ZoneId.systemDefault()).toLocalDate();
            return new ElectionData(title, ldDateFrom, ldDateDue, showDiagram);
        } else {
            throw new Exception("election object is null!");
        }
    }

    /**
     * Method responsible for getting the candidate with a specific index
     *
     * @param candidateIndex: index of the candidate
     * @return CandidateData object
     * @throws Exception if the election contract is not loaded
     */
    public CandidateData getCandidateData(int candidateIndex) throws Exception {
        if (election != null) {
            BigInteger candidate = new BigInteger(candidateIndex + "");
            String title = election.getCandidate(candidate).send().getValue1();
            String firstname = election.getCandidate(candidate).send().getValue2();
            String lastname = election.getCandidate(candidate).send().getValue3();
            BigInteger birthday = election.getCandidate(candidate).send().getValue4();
            String party = election.getCandidate(candidate).send().getValue5();
            String slogan = election.getCandidate(candidate).send().getValue6();
            LocalDate ldBirthday = Instant.ofEpochMilli(birthday.longValue()).atZone(ZoneId.systemDefault()).toLocalDate();
            BigInteger voteCount = election.getCandidate(candidate).send().getValue7();
            CandidateData data = new CandidateData(title, firstname, lastname, ldBirthday, party, slogan, voteCount.intValue());
            data.setPortraitPath(getCandidateImagePath(candidateIndex));
            return data;
        } else {
            throw new Exception("election object is null!");
        }
    }


    /**
     * Method responsible for returning the address of the admin of an election
     *
     * @return the address of the admin of an election
     * @throws Exception if the election contract is not loaded
     */
    public String getAdminAddress() throws Exception {
        if (election != null) {
            return election.getAdminAddress().send();
        } else {
            throw new Exception("election object is null!");
        }
    }

    /**
     * Method responsible for returning the contract address of the election contract
     *
     * @return contract address of the election
     * @throws Exception if the election contract is not loaded
     */
    public String getContractAddress() throws Exception {
        if (election != null) {
            return election.getContractAddress();
        } else {
            throw new Exception("election object is null!");
        }
    }

    /**
     * Method responsible for determining whether the voter has already voted
     *
     * @param voterAddress: address of the voter
     * @return boolean if the given voter has already voted
     * @throws Exception if the election contract is not loaded
     */
    public boolean getAlreadyVotedForVoter(Address voterAddress) throws Exception {
        if (election != null) {
            return election.getAlreadyVotedForVoter(voterAddress.toString()).send();
        } else {
            throw new Exception("election object is null!");
        }
    }

    /**
     * Method responsible for getting the path to the image of the candidate
     *
     * @param candidateIndex: index of the candidate
     * @return the path to the image of the candidate
     * @throws Exception if the election contract is not loaded
     */
    public String getCandidateImagePath(int candidateIndex) throws Exception {
        if (election != null) {
            return election.getCandidateImagePath(new BigInteger(candidateIndex + "")).send();
        } else {
            throw new Exception("election object is null!");
        }
    }

    /**
     * Method responsible for returning the size of the candidate array in the contract
     *
     * @return size of array
     * @throws Exception if the election contract is not loaded
     */
    public int getCandidateArraySize() throws Exception {
        if (election != null) {
            return election.getCandidateSize().send().intValue();
        } else {
            throw new Exception("election object is null!");
        }
    }
}
