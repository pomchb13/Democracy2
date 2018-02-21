package poll;

import beans.PollAnswer;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import util.BlockchainUtil;
import util.Utilorschloader;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * Created by Patri on 03.01.2018.
 */
public class PollTester {

    private Web3j web3;
    private Credentials credentials;
    private Poll poll;

    public PollTester() {
        web3 = Web3j.build(new HttpService());
        credentials = BlockchainUtil.loginToBlockhain("0xdcc97f1bd80b47137480d2a3d9a54a0af6aa92be", "1234");
    }


    public PollTester(Credentials credentials) {
        web3 = Web3j.build(new HttpService());
        this.credentials = credentials;
    }


    public void createContract(int numAnswers, String title, LocalDate dateFrom, LocalDate dateDue, boolean showDiagram) throws Exception {
        BigInteger dateFromInMilliseconds = new BigInteger(dateFrom.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + "");
        BigInteger dateDueInMilliseconds = new BigInteger(dateDue.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + "");

        poll = Poll.deploy(web3, credentials, new BigInteger("300000"), new BigInteger("4700000"), new BigInteger(numAnswers + ""), title, dateFromInMilliseconds, dateDueInMilliseconds, showDiagram).send();
        System.out.println(poll.getContractAddress());

    }

    public void giveRightToVote(Address voter) throws Exception {
        if(poll != null)
        {
            poll.giveRightToVote(voter.toString()).send();
        }
    }

    public void vote(Uint8 proposal, Address address) throws Exception {
        if(poll != null)
        {
            poll.vote(proposal.getValue(), address.toString()).send();
        }
    }

    public int winningAnswer() throws Exception {
        if(poll != null)
        {
            return poll.winningAnswer().send().intValue();
        }
        return -1;
    }

    public void storeAnswerData(int answer, String title, String description) throws Exception
    {
        poll.storeAnswerData(new BigInteger(answer + ""), title, description).send();
    }

    public PollAnswer getAnswerData(int answer) throws Exception {
        BigInteger answerBigInt = new BigInteger(answer + "");
        String title = poll.getAnswerData(answerBigInt).send().getValue1();
        String description = poll.getAnswerData(answerBigInt).send().getValue2();
        BigInteger voteCount = poll.getAnswerData(answerBigInt).send().getValue3();
        return new PollAnswer(title, description, voteCount.intValue());
    }

    public PollData getPollData() throws Exception {
        String title = poll.getPollData().send().getValue1();
        BigInteger dateFrom = poll.getPollData().send().getValue2();
        BigInteger dateDue = poll.getPollData().send().getValue3();
        Boolean showDiagram = poll.getPollData().send().getValue4();

        LocalDate ldDateFrom = Instant.ofEpochMilli(dateFrom.longValue()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ldDateDue = Instant.ofEpochMilli(dateDue.longValue()).atZone(ZoneId.systemDefault()).toLocalDate();
        return new PollData(title, ldDateFrom, ldDateDue, showDiagram);
    }



    public void loadSmartContract(String address) {
        poll = Poll.load(address, web3, credentials, new BigInteger("300000"), new BigInteger("4700000"));
        System.out.println(poll.getContractAddress());
    }

    public String getContractAddress()
    {
        return poll.getContractAddress();
    }

    public String getAdminAddress() throws Exception {
        return poll.getAdminAddress().send();
    }

    public boolean getAlreadyVotedForVoter(String address) throws Exception {
        return poll.getAlreadyVotedForVoter(address).send();
    }



    public static void main(String[] args) {
        try
        {
            String address = "0x31f8a4c3e67a15757b8a6c734a5480576c0b9410";
            String users[] = {"0xdCc97F1Bd80b47137480D2A3D9a54a0aF6aA92Be",
                    "0x1fA240651d34b5abc091F1CF3387fd278e714098",
                    "0x8060735949f5244b8bC3FbAc129A4e0B9578dF25",
                    "0x44D6e503b8028Ab6B6a4f5bB8959e1258Cd9a584"};

            PollAnswer a1 = new PollAnswer("A1", "B1");
            PollAnswer a2 = new PollAnswer("A2", "B2");
            PollAnswer a3 = new PollAnswer("A3", "B3");

            PollTester tester = new PollTester();
          /*  tester.createContract(3, "TestTitle", LocalDate.of(2017, 3, 2), LocalDate.of(2018, 1, 1), true);
            tester.storeAnswerData(0, a1.getTitle(), a1.getDescription());
            tester.storeAnswerData(1, a2.getTitle(), a2.getDescription());
            tester.storeAnswerData(2, a3.getTitle(), a3.getDescription());*/

            tester.loadSmartContract(address);

            System.out.println(tester.getPollData());
            System.out.println(tester.getAnswerData(0));
            System.out.println(tester.getAnswerData(1));
            System.out.println(tester.getAnswerData(2));


            tester.giveRightToVote(new Address(users[0]));
            tester.giveRightToVote(new Address(users[1]));
            tester.giveRightToVote(new Address(users[2]));
            tester.giveRightToVote(new Address(users[3]));
            tester.vote(new Uint8(0), new Address(users[1]));
            tester.vote(new Uint8(1), new Address(users[1]));
            tester.vote(new Uint8(1), new Address(users[3]));
           // tester.vote(new Uint8(2), new Address(users[2]));

            int winner = tester.winningAnswer();
            System.out.println("\nWinner: " + tester.getAnswerData(winner));

            System.out.println("Admin: " + tester.getAdminAddress());
            System.out.println(users[0] + " --> " + tester.getAlreadyVotedForVoter(users[0]));
            System.out.println(users[1] + " --> " + tester.getAlreadyVotedForVoter(users[1]));
            System.out.println(users[2] + " --> " + tester.getAlreadyVotedForVoter(users[2]));
            System.out.println(users[3] + " --> " + tester.getAlreadyVotedForVoter(users[3]));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
