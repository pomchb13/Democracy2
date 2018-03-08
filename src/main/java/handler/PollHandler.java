package handler;

import beans.PollAnswer;
import beans.PollData;
import contracts.PollContract;
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
 * Created by Patrick on 03.01.2018.
 */
public class PollHandler {

    private Web3j web3;
    private Credentials credentials;
    private PollContract poll;

    // for testing purpose only
    public PollHandler() throws IOException, CipherException {
        web3 = Web3j.build(new HttpService());
        credentials = BlockchainUtil.loginToBlockhain("0xdCc97F1Bd80b47137480D2A3D9a54a0aF6aA92Be", "1234");
    }


    /**
     * Constructor to initiliaze the web3j web-service
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
     * @return
     * @throws Exception if an error occurs
     */
    public String createContract(int numAnswers, String title, LocalDate dateFrom, LocalDate dateDue, boolean showDiagram) throws Exception {
        BigInteger dateFromInMilliseconds = new BigInteger(dateFrom.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + "");
        BigInteger dateDueInMilliseconds = new BigInteger(dateDue.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + "");
        poll = PollContract.deploy(web3, credentials, BigInteger.ZERO, new BigInteger("4700000"), new BigInteger(numAnswers + ""), title, dateFromInMilliseconds, dateDueInMilliseconds, showDiagram).send();
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
     * @param answer:      index of the answer
     * @param title:       title of the answer
     * @param description: description of the answer
     * @throws Exception if the poll contract is not loaded
     */
    public void storeAnswerData(int answer, String title, String description) throws Exception {
        if (poll != null) {
            poll.storeAnswerData(new BigInteger(answer + ""), title, description).send();
        } else {
            throw new Exception("poll object is null!");
        }
    }

    /**
     * Method responsible for getting the data of a specific answer
     *
     * @param answer: index of the answer
     * @return PollAnswer object
     * @throws Exception if the poll contract is not loaded
     */
    public PollAnswer getAnswerData(int answer) throws Exception {
        if (poll != null) {
            BigInteger answerBigInt = new BigInteger(answer + "");
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
     * Method responsible for loading an existing contract with a specific address
     *
     * @param address: address of the contract
     * @return address of the contract
     */
    public String loadSmartContract(Address address) {
        poll = PollContract.load(address.toString(), web3, credentials, new BigInteger("300000"), new BigInteger("4700000"));
        return poll.getContractAddress();
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
     * @param address: address of the voter
     * @return boolean if the given voter has already voted
     * @throws Exception if the poll contract is not loaded
     */
    public boolean getAlreadyVotedForVoter(Address address) throws Exception {
        if (poll != null) {
            return poll.getAlreadyVotedForVoter(address.toString()).send();
        } else {
            throw new Exception("poll object is null!");
        }
    }

    /**
     * Method responsible for returning the correct contract address of the contract where the voter is allowed to vote
     *
     * @param address: address of the voter
     * @return the contract address of the contract where the voter is allowed to vote
     * @throws Exception if the poll contract is not loaded
     */
    public String getContractAddressForVoter(Address address) throws Exception {
        if (poll != null) {
            return poll.getVoteAddressForVoter(address.toString()).send();
        } else {
            throw new Exception("poll object is null!");
        }
    }


    // for testing purpose only
    public static void main(String[] args) {
        try {
            String address = "0x36b158126df76110aa16e29b76172d8b3ed82dbf";
            String users[] = {"0xdCc97F1Bd80b47137480D2A3D9a54a0aF6aA92Be",
                    "0x1fA240651d34b5abc091F1CF3387fd278e714098",
                    "0x8060735949f5244b8bC3FbAc129A4e0B9578dF25",
                    "0x44D6e503b8028Ab6B6a4f5bB8959e1258Cd9a584"};

            PollAnswer a1 = new PollAnswer("A1", "B1");
            PollAnswer a2 = new PollAnswer("A2", "B2");
            PollAnswer a3 = new PollAnswer("A3", "B3");

            PollHandler tester = new PollHandler();
          /*  tester.createContract(3, "TestTitle", LocalDate.of(2017, 3, 2), LocalDate.of(2018, 1, 1), true);
            tester.storeAnswerData(0, a1.getTitle(), a1.getDescription());
            tester.storeAnswerData(1, a2.getTitle(), a2.getDescription());
            tester.storeAnswerData(2, a3.getTitle(), a3.getDescription());*/

            tester.loadSmartContract(new Address(address));

            System.out.println(tester.getPollData());
            System.out.println(tester.getAnswerData(0));
            System.out.println(tester.getAnswerData(1));
            System.out.println(tester.getAnswerData(2));


            /*tester.giveRightToVote(new Address(users[0]));
            tester.giveRightToVote(new Address(users[1]));
            tester.giveRightToVote(new Address(users[2]));
            tester.giveRightToVote(new Address(users[3]));
            tester.vote(new Uint8(2), new Address(users[0]));
            tester.vote(new Uint8(1), new Address(users[1]));
            tester.vote(new Uint8(1), new Address(users[3]));
            tester.vote(new Uint8(2), new Address(users[2]));*/

           /* PollAnswer pa1 = tester.getAnswerData(0);
            PollAnswer pa2 = tester.getAnswerData(1);
            PollAnswer pa3 = tester.getAnswerData(2);
            List<PollAnswer> pollAnswerList = new ArrayList<>();
            pollAnswerList.add(pa1);
            pollAnswerList.add(pa2);
            pollAnswerList.add(pa3);
            System.out.println(Arrays.toString(tester.getWinners(pollAnswerList).toArray()));*/

            // System.out.println(tester.getContractAddressForVoter("0xdCc97F1Bd80b47137480D2A3D9a54a0aF6aA92Be"));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
