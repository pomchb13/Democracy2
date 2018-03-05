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
import java.util.Arrays;
import java.util.List;

/**
 * Created by Patrick on 01.08.2017.
 */

public class ElectionHandler {

    private Web3j web3;
    private Credentials credentials;
    private ElectionContract election;

    // for testing purpose only
    public ElectionHandler() throws IOException, CipherException {
        web3 = Web3j.build(new HttpService());
        credentials = BlockchainUtil.loginToBlockhain("0x67db9880d62389799691b9b1806ab59f90b49259", "1234");
    }

    public ElectionHandler(Credentials credentials) {
        web3 = Web3j.build(new HttpService());
        this.credentials = credentials;
    }


    /***
     * Method responsible for creating a new smart contract
     */
    public String createContract(int numProps, String title, LocalDate dateFrom, LocalDate dateDue, boolean showDiagram) throws Exception {
        System.out.println("Handler --> create 1");
        BigInteger dateFromInMilliseconds = new BigInteger(dateFrom.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + "");
        BigInteger dateDueInMilliseconds = new BigInteger(dateDue.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + "");

        System.out.println("Handler --> create 2");
        System.out.println(web3);
        System.out.println(credentials);
        election = ElectionContract.deploy(web3, credentials, new BigInteger("300000"), new BigInteger("4700000"), new BigInteger(numProps + ""), title, dateFromInMilliseconds, dateDueInMilliseconds, showDiagram).send();
        System.out.println("Handler --> create 3");
        return election.getContractAddress();
    }

    public void giveRightToVote(Address voter) throws Exception {
        if (election != null) {
            election.giveRightToVote(voter.toString()).send();
        } else {
            throw new Exception("election object is null!");
        }
    }

    public void vote(Uint8 proposal, Address address) throws Exception {
        if (election != null) {
            election.vote(proposal.getValue(), address.toString()).send();
        } else {
            throw new Exception("election object is null!");
        }
    }


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


    public void storeCandidateData(int candidate, String title, String firstname, String lastname, LocalDate birthday, String party, String slogan) throws Exception {
        if (election != null) {
            BigInteger birthdayInMilliseconds = new BigInteger(birthday.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + "");
            election.storeCandidateData(new BigInteger(candidate + ""), title, firstname, lastname, birthdayInMilliseconds, party, slogan).send();
        } else {
            throw new Exception("election object is null!");
        }
    }

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

    public CandidateData getCandidateData(int can) throws Exception {
        if (election != null) {
            BigInteger candidate = new BigInteger(can + "");
            String title = election.getCandidate(candidate).send().getValue1();
            String firstname = election.getCandidate(candidate).send().getValue2();
            String lastname = election.getCandidate(candidate).send().getValue3();
            BigInteger birthday = election.getCandidate(candidate).send().getValue4();
            String party = election.getCandidate(candidate).send().getValue5();
            String slogan = election.getCandidate(candidate).send().getValue6();
            LocalDate ldBirthday = Instant.ofEpochMilli(birthday.longValue()).atZone(ZoneId.systemDefault()).toLocalDate();
            BigInteger voteCount = election.getCandidate(candidate).send().getValue7();
            return new CandidateData(title, firstname, lastname, ldBirthday, party, slogan, voteCount.intValue());
        } else {
            throw new Exception("election object is null!");
        }
    }


    /***
     * Method responsible for loading an existing contract with a specific address
     * @param address
     */
    public void loadSmartContract(Address address) {
        election = ElectionContract.load(address.toString(), web3, credentials, new BigInteger("300000"), new BigInteger("4700000"));
    }

    public String getAdminAddress() throws Exception {
        if (election != null) {
            return election.getAdminAddress().send();
        } else {
            throw new Exception("election object is null!");
        }
    }

    public String getContractAddress() throws Exception {
        if (election != null) {
            return election.getContractAddress();
        } else {
            throw new Exception("election object is null!");
        }
    }

    public boolean getAlreadyVotedForVoter(Address address) throws Exception {
        if (election != null) {
            return election.getAlreadyVotedForVoter(address.toString()).send();
        } else {
            throw new Exception("election object is null!");
        }
    }

    public String getVoteAddressForVoter(Address address) throws Exception {
        if (election != null) {
            return election.getVoteAddressForVoter(address.toString()).send();
        } else {
            throw new Exception("election object is null!");
        }
    }

    // for testing purpose only
    public static void main(String[] args) {
        try {
            String address = "0x39ed290931679c83e1f96ba6ce2181ae0989854a";

            String users[] = {"0xdCc97F1Bd80b47137480D2A3D9a54a0aF6aA92Be",
                    "0x1fA240651d34b5abc091F1CF3387fd278e714098",
                    "0x8060735949f5244b8bC3FbAc129A4e0B9578dF25",
                    "0x44D6e503b8028Ab6B6a4f5bB8959e1258Cd9a584"};

            BlockchainUtil.setPATH("D:\\Daten\\Git\\Democracy2\\out\\artifacts\\test_webapp2_war_exploded\\res\\geth_data\\keystore\\");

            CandidateData p1 = new CandidateData("Dr", "F1", "L1", LocalDate.of(1999, 9, 3), "ÖVP", "S1", null);
            CandidateData p2 = new CandidateData("Mag", "F2", "L2", LocalDate.of(1980, 9, 3), "FPÖ", "S2", null);
            CandidateData p3 = new CandidateData("DI", "F3", "L3", LocalDate.of(1970, 9, 3), "SPÖ", "S3", null);


            ElectionHandler tester = new ElectionHandler();
            tester.createContract(3, "TestTitle", LocalDate.of(2017, 3, 2), LocalDate.of(2018, 1, 1), true);
            tester.storeCandidateData(0, p1.getTitle(), p1.getForename(), p1.getSurname(), p1.getBirthday(), p1.getParty(), p1.getSlogan());
            tester.storeCandidateData(1, p2.getTitle(), p2.getForename(), p2.getSurname(), p2.getBirthday(), p2.getParty(), p2.getSlogan());
            tester.storeCandidateData(2, p3.getTitle(), p3.getForename(), p3.getSurname(), p3.getBirthday(), p3.getParty(), p3.getSlogan());
            //   tester.loadSmartContract(address);
            System.out.println(tester.getElectionData());
            System.out.println(tester.getCandidateData(0));
            System.out.println(tester.getCandidateData(1));
            System.out.println(tester.getCandidateData(2));

           /* tester.giveRightToVote(new Address(users[0]));
            tester.giveRightToVote(new Address(users[1]));
            tester.giveRightToVote(new Address(users[2]));
            tester.giveRightToVote(new Address(users[3]));
            tester.vote(new Uint8(2), new Address(users[0]));
            tester.vote(new Uint8(1), new Address(users[1]));
            tester.vote(new Uint8(1), new Address(users[3]));
            tester.vote(new Uint8(2), new Address(users[2]));

            CandidateData c1 = tester.getCandidateData(0);
            CandidateData c2 = tester.getCandidateData(1);
            CandidateData c3 = tester.getCandidateData(2);
            List<CandidateData> candidateDataList = new ArrayList<>();
            candidateDataList.add(c1);
            candidateDataList.add(c2);
            candidateDataList.add(c3);
            System.out.println(Arrays.toString(tester.getWinners(candidateDataList).toArray()));*/

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
