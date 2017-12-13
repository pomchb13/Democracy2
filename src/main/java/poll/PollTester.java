package poll;

import contracttest.SimpleStorage;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Patrick on 01.08.2017.
 * working correctly
 */
public class PollTester {

    private Web3j web3;
    private Credentials credentials;
    private Poll poll;

    public PollTester() throws IOException, CipherException {
        // creates the interface to the blockchain and initialize path to the wallet
        web3 = Web3j.build(new HttpService());
        credentials = WalletUtils.loadCredentials("1234", "D:\\Ethereum\\geth_data\\keystore\\UTC--2017-10-19T14-38-48.526096700Z--dcc97f1bd80b47137480d2a3d9a54a0af6aa92be");
    }

    /***
     * Method responsible for creating a new smart contract
     */
    public void createContract(int numProps) {
        try {
            poll = Poll.deploy(web3, credentials, BigInteger.ZERO,new Uint8(numProps)).get();
            System.out.println(poll.getContractAddress());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void giveRightToVote(Address voter) throws ExecutionException, InterruptedException {
        if(poll != null)
        {
            poll.giveRightToVote(voter).get();
        }
    }

    public void vote(Uint8 proposal, Address address) throws ExecutionException, InterruptedException {
        if(poll != null)
        {
            poll.vote(proposal, address).get();
        }
    }

    public int[] getProposal(int num) throws ExecutionException, InterruptedException {
        if(poll != null)
        {
            Uint256 count = poll.getProposalVoteCount(new Uint256(num)).get();

            return new int[]{num, Integer.parseInt(count.toString())};

        }
        return null;
    }

    public int winner() throws ExecutionException, InterruptedException {
        int winner = -1;
        int count = 0;
        for(int i = 0; i < 3; i++)
        {
            int[] prop = getProposal(i);
            if(count < prop[1])
            {
                count = prop[1];
                winner = prop[0];
            }
        }
        return winner;
    }


    public void reset() throws ExecutionException, InterruptedException {
        if(poll != null)
        {
            poll.reset().get();
        }

    }


    /***
     * Method responsible for loading an existing contract with a specific adress
     * @param adress
     */
    public void loadSmartContract(String adress) {
        poll = Poll.load(adress, web3, credentials);
        System.out.println(poll.getContractAddress());
    }



    public static void main(String[] args) {
        try {
            String address = "0xbe025bcc10999ecc4626ba8162a1670bf632f2b9";

            String users[] = {"0xdCc97F1Bd80b47137480D2A3D9a54a0aF6aA92Be",
                    "0x1fA240651d34b5abc091F1CF3387fd278e714098",
                    "0x8060735949f5244b8bC3FbAc129A4e0B9578dF25",
                    "0x44D6e503b8028Ab6B6a4f5bB8959e1258Cd9a584"};

            PollTester tester = new PollTester();
           // tester.createContract(3);
             tester.loadSmartContract(address);
          /*  tester.giveRightToVote(new Address(users[0]));
            tester.giveRightToVote(new Address(users[1]));
            tester.giveRightToVote(new Address(users[2]));
            tester.giveRightToVote(new Address(users[3]));*/
            tester.vote(new Uint8(1), new Address(users[0]));

            System.out.println(tester.winner());


           // tester.reset();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
