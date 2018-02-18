package election;

import beans.Politician;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * Created by Patrick on 01.08.2017.
 * working correctly
 */
public class ElectionTester {

    private Web3j web3;
    private Credentials credentials;
    private Election election;

    public ElectionTester() throws IOException, CipherException {
        // creates the interface to the blockchain and initialize path to the wallet
        web3 = Web3j.build(new HttpService());
        credentials = WalletUtils.loadCredentials("1234", "D:\\Ethereum\\geth_data\\keystore\\UTC--2017-10-19T14-38-48.526096700Z--dcc97f1bd80b47137480d2a3d9a54a0af6aa92be");
    }

    /***
     * Method responsible for creating a new smart contract
     */
    public void createContract(int numProps, String title, LocalDate dateFrom, LocalDate dateDue, boolean showDiagram) throws Exception {
        BigInteger dateFromInMilliseconds = new BigInteger(dateFrom.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + "");
        BigInteger dateDueInMilliseconds = new BigInteger(dateDue.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + "");

        election = Election.deploy(web3, credentials, new BigInteger("300000"), new BigInteger("4700000"), new BigInteger(numProps + ""), title, dateFromInMilliseconds, dateDueInMilliseconds, showDiagram).send();
        System.out.println(election.getContractAddress());
    }

    public void giveRightToVote(Address voter) throws Exception {
        if(election != null)
        {
            election.giveRightToVote(voter.toString()).send();
        }
    }

    public void vote(Uint8 proposal, Address address) throws Exception {
        if(election != null)
        {
            election.vote(proposal.getValue(), address.toString()).send();
        }
    }

    public int winningCandidate() throws Exception {
        if(election != null)
        {
            return election.winningCandidate().send().intValue();
        }
        return -1;
    }

    public void storeCandidateData(int prop, String title, String firstname, String lastname, LocalDate birthday, String party, String slogan) throws Exception {
        BigInteger birthdayInMilliseconds = new BigInteger(birthday.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + "");
        election.storeCandidateData(new BigInteger(prop + ""), title, firstname, lastname, birthdayInMilliseconds, party, slogan).send();
    }

    public ElectionData getElectionData() throws Exception {
        String title = election.getElectionData().send().getValue1();
        BigInteger dateFrom = election.getElectionData().send().getValue2();
        BigInteger dateDue = election.getElectionData().send().getValue3();
        Boolean showDiagram = election.getElectionData().send().getValue4();

        LocalDate ldDateFrom = Instant.ofEpochMilli(dateFrom.longValue()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ldDateDue = Instant.ofEpochMilli(dateDue.longValue()).atZone(ZoneId.systemDefault()).toLocalDate();
        return new ElectionData(title, ldDateFrom, ldDateDue, showDiagram);
    }

    public CandidateData getCandidateData(int prop) throws Exception {
        BigInteger proposal = new BigInteger(prop + "");
        String title = election.getCandidate(proposal).send().getValue1();
        String firstname = election.getCandidate(proposal).send().getValue2();
        String lastname = election.getCandidate(proposal).send().getValue3();
        BigInteger birthday = election.getCandidate(proposal).send().getValue4();
        String party = election.getCandidate(proposal).send().getValue5();
        String slogan = election.getCandidate(proposal).send().getValue6();
        LocalDate ldBirthday = Instant.ofEpochMilli(birthday.longValue()).atZone(ZoneId.systemDefault()).toLocalDate();
        return new CandidateData(title, firstname, lastname, ldBirthday, party, slogan);
    }


    /***
     * Method responsible for loading an existing contract with a specific adress
     * @param adress
     */
    public void loadSmartContract(String adress) {
        election = Election.load(adress, web3, credentials, new BigInteger("300000"), new BigInteger("4700000"));
        System.out.println(election.getContractAddress());
    }



    public static void main(String[] args) {
        try {
            String address = "0xf3c3fb99addb47a0f433db61e5610b71b77ee156";

            String users[] = {"0xdCc97F1Bd80b47137480D2A3D9a54a0aF6aA92Be",
                    "0x1fA240651d34b5abc091F1CF3387fd278e714098",
                    "0x8060735949f5244b8bC3FbAc129A4e0B9578dF25",
                    "0x44D6e503b8028Ab6B6a4f5bB8959e1258Cd9a584"};

            Politician p1 = new Politician("Dr", "F1", "L1", LocalDate.of(1999,9,3), "ÖVP", "S1", null);
            Politician p2 = new Politician("Mag", "F2", "L2", LocalDate.of(1980,9,3), "FPÖ", "S2", null);
            Politician p3 = new Politician("DI", "F3", "L3", LocalDate.of(1970,9,3), "SPÖ", "S3", null);


            ElectionTester tester = new ElectionTester();
            tester.createContract(3, "TestTitle", LocalDate.of(2017, 3, 2), LocalDate.of(2018, 1, 1), true);
            tester.storeCandidateData(0, p1.getTitle(), p1.getForename(), p1.getSurname(), p1.getBirthday(), p1.getParty(), p1.getSlogan());
            tester.storeCandidateData(1, p2.getTitle(), p2.getForename(), p2.getSurname(), p2.getBirthday(), p2.getParty(), p2.getSlogan());
            tester.storeCandidateData(2, p3.getTitle(), p3.getForename(), p3.getSurname(), p3.getBirthday(), p3.getParty(), p3.getSlogan());
             //tester.loadSmartContract(address);
            System.out.println(tester.getElectionData());
            System.out.println(tester.getCandidateData(0));
            System.out.println(tester.getCandidateData(1));
            System.out.println(tester.getCandidateData(2));

            /*tester.giveRightToVote(new Address(users[0]));
            tester.giveRightToVote(new Address(users[1]));
            tester.giveRightToVote(new Address(users[2]));
            tester.giveRightToVote(new Address(users[3]));
            tester.vote(new Uint8(0), new Address(users[0]));
            tester.vote(new Uint8(1), new Address(users[1]));
            tester.vote(new Uint8(1), new Address(users[3]));
            tester.vote(new Uint8(2), new Address(users[2]));


            int winner = tester.winningCandidate();
            System.out.println("\nWinner: "+tester.getCandidateData(winner));*/



        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
