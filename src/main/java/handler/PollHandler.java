package handler;

import beans.PollAnswer;
import beans.PollData;
import contracts.PollContract;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:          Patrick Windegger
 * Description:     Class responsible for handling a poll.
 * All methods of the contract are implemented in this handler.
 * The communication between java and the Blockchain is also implemented here.
 */
public class PollHandler {

    private Web3j web3;
    private Credentials credentials;
    private PollContract poll;

        /**
     * Constructor to initiliaze the web3j web-service and the credentials
     *
     * @param credentials: credentials of the user (wallet file)
     */
    public PollHandler(Credentials credentials) {
        web3 = Web3j.build(new HttpService());
        this.credentials = credentials;
    }


    /**
     * Method responsible for creating a new poll contract
     *
     * @param numAnswers:  number of answers of the poll
     * @param title:       title of the poll
     * @param dateFrom:    start date of the poll
     * @param dateDue:     end date of the poll
     * @param showDiagram: boolean if a diagram is shown on the web page
     * @return address of the contract
     * @throws Exception if an error occurs
     */
    public String createContract(int numAnswers, String title, LocalDate dateFrom, LocalDate dateDue, boolean showDiagram) throws Exception {
        BigInteger dateFromInMilliseconds = new BigInteger(dateFrom.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + "");
        BigInteger dateDueInMilliseconds = new BigInteger(dateDue.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + "");
        poll = PollContract.deploy(web3, credentials, BigInteger.ZERO, new BigInteger("4700000"), new BigInteger(numAnswers + ""), title, dateFromInMilliseconds, dateDueInMilliseconds, showDiagram).send();
        return poll.getContractAddress();
    }

    /**
     * Method responsible for loading an existing contract with a specific address
     *
     * @param contractAddress: address of the contract
     * @return address of the contract
     */
    public String loadSmartContract(Address contractAddress) {
        poll = PollContract.load(contractAddress.toString(), web3, credentials, new BigInteger("300000"), new BigInteger("4700000"));
        return poll.getContractAddress();
    }

    /**
     * Method responsible for giving the voter the right to vote for an election
     *
     * @param voter: address of the voter
     * @throws Exception if the poll contract is not loaded
     */
    public void giveRightToVote(Address voter) throws Exception {
        if (poll != null) {
            poll.giveRightToVote(voter.toString()).send();
        } else {
            throw new Exception("poll object is null!");
        }
    }

    /**
     * Method responsible for voting for a specific answer of the poll
     *
     * @param answerIndex: index of the answer
     * @param voter:       address of the voter
     * @throws Exception if the poll contract is not loaded
     */
    public void vote(Uint8 answerIndex, Address voter) throws Exception {
        if (poll != null) {
            poll.vote(answerIndex.getValue(), voter.toString()).send();
        } else {
            throw new Exception("poll object is null!");
        }
    }

    /**
     * Method responsible for getting the winning answer or answers of a poll
     *
     * @param allAnswers: list of all answers of the poll
     * @return
     */
    public List<PollAnswer> getWinners(List<PollAnswer> allAnswers) {
        List<PollAnswer> winners = new ArrayList<>();
        int winningVoteCount = 0;
        for (int i = 0; i < allAnswers.size(); i++) {
            if (allAnswers.get(i).getVoteCount() > winningVoteCount) {
                winningVoteCount = allAnswers.get(i).getVoteCount();
            }
        }

        for (int i = 0; i < allAnswers.size(); i++) {
            if (allAnswers.get(i).getVoteCount() == winningVoteCount) {
                winners.add(allAnswers.get(i));
            }
        }

        return winners;
    }

    /**
     * Method responsible for storing the data of an answer
     *
     * @param answerIndex: index of the answer
     * @param title:       title of the answer
     * @param description: description of the answer
     * @throws Exception if the poll contract is not loaded
     */
    public void storeAnswerData(int answerIndex, String title, String description) throws Exception {
        if (poll != null) {
            poll.storeAnswerData(new BigInteger(answerIndex + ""), title, description).send();
        } else {
            throw new Exception("poll object is null!");
        }
    }

    /**
     * Method responsible for getting the data of a specific answer
     *
     * @param answerIndex: index of the answer
     * @return PollAnswer object
     * @throws Exception if the poll contract is not loaded
     */
    public PollAnswer getAnswerData(int answerIndex) throws Exception {
        if (poll != null) {
            BigInteger answerBigInt = new BigInteger(answerIndex + "");
            String title = poll.getAnswerData(answerBigInt).send().getValue1();
            String description = poll.getAnswerData(answerBigInt).send().getValue2();
            BigInteger voteCount = poll.getAnswerData(answerBigInt).send().getValue3();
            return new PollAnswer(title, description, voteCount.intValue());
        } else {
            throw new Exception("poll object is null!");
        }
    }

    /**
     * Method responsible for getting the data of a poll
     *
     * @return PollData object
     * @throws Exception if the poll contract is not loaded
     */
    public PollData getPollData() throws Exception {
        if (poll != null) {
            String title = poll.getPollData().send().getValue1();
            BigInteger dateFrom = poll.getPollData().send().getValue2();
            BigInteger dateDue = poll.getPollData().send().getValue3();
            Boolean showDiagram = poll.getPollData().send().getValue4();

            LocalDate ldDateFrom = Instant.ofEpochMilli(dateFrom.longValue()).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate ldDateDue = Instant.ofEpochMilli(dateDue.longValue()).atZone(ZoneId.systemDefault()).toLocalDate();
            return new PollData(title, ldDateFrom, ldDateDue, showDiagram);
        } else {
            throw new Exception("poll object is null!");
        }
    }


    /**
     * Method responsible for returning the contract address of the poll contract
     *
     * @return contract address of the poll
     * @throws Exception if the poll contract is not loaded
     */
    public String getContractAddress() throws Exception {
        if (poll != null) {
            return poll.getContractAddress();
        } else {
            throw new Exception("poll object is null!");
        }
    }

    /**
     * Method responsible for returning the address of the admin of a poll
     *
     * @return the address of the admin of a poll
     * @throws Exception if the poll contract is not loaded
     */
    public String getAdminAddress() throws Exception {
        if (poll != null) {
            return poll.getAdminAddress().send();
        } else {
            throw new Exception("poll object is null!");
        }

    }

    /**
     * Method responsible for determining whether the voter has already voted
     *
     * @param voterAddress: address of the voter
     * @return boolean if the given voter has already voted
     * @throws Exception if the poll contract is not loaded
     */
    public boolean getAlreadyVotedForVoter(Address voterAddress) throws Exception {
        if (poll != null) {
            return poll.getAlreadyVotedForVoter(voterAddress.toString()).send();
        } else {
            throw new Exception("poll object is null!");
        }
    }

    /**
     * Method responsible for returning the size of the answer array in the contract
     *
     * @return size of array
     * @throws Exception if the poll contract is not loaded
     */
    public int getAnswerArraySize() throws Exception {
        if (poll != null) {
            return poll.getAnswerSize().send().intValue();
        } else {
            throw new Exception("election object is null!");
        }
    }
}
