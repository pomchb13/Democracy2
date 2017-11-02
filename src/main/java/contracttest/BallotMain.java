package contracttest;

import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * Created by Patri on 02.11.2017.
 */
public class BallotMain {

    private Web3j web3j;
    private Credentials credentials;
    private Poll poll;

    public BallotMain() throws IOException, CipherException {
        web3j = Web3j.build(new HttpService());
        credentials = WalletUtils.loadCredentials("1234", "D:\\Ethereum\\geth_data\\keystore\\UTC--2017-10-19T14-38-48.526096700Z--dcc97f1bd80b47137480d2a3d9a54a0af6aa92be");
    }

    public void createContract() throws ExecutionException, InterruptedException {
        poll = Poll.deploy(web3j, credentials, BigInteger.ZERO).get();
        System.out.println(poll.getContractAddress());
    }

    public void loadContract(String address)
    {
        poll = Poll.load(address, web3j, credentials);
        System.out.println(poll.getContractBinary());
        System.out.println(poll.getContractAddress());
    }

    public void ballot(int numProposals)
    {
        if(poll != null)
        {
            poll.Ballot(new Uint8(numProposals));
        }
    }

    public void vote(int proposal)
    {
        if(poll != null)
        {
            poll.vote(new Uint8(proposal));
        }
    }

    public void winner() throws ExecutionException, InterruptedException {
        if(poll != null)
        {
            System.out.println(poll.winningProposal().get().getValue().toString());
        }
    }

    public static void main(String[] args) {
        try {
            String address = "0x05438e3a975e400077405af47503271abff23e00";
            BallotMain main = new BallotMain();
            main.loadContract(address);
            //main.createContract();
            main.ballot(4);
            main.vote(2);
            main.winner();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
