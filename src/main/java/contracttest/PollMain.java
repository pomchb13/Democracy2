package contracttest;

import org.web3j.abi.datatypes.Utf8String;
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
public class PollMain {

    private Web3j web3j;
    private Credentials credentials;
    private PollTest pollTest;

    public PollMain() throws IOException, CipherException {
        web3j = Web3j.build(new HttpService());
        credentials = WalletUtils.loadCredentials("1234", "D:\\Ethereum\\geth_data\\keystore\\UTC--2017-10-19T14-38-48.526096700Z--dcc97f1bd80b47137480d2a3d9a54a0af6aa92be");
    }

    public void createContract() throws ExecutionException, InterruptedException {
        pollTest = PollTest.deploy(web3j, credentials, BigInteger.ZERO).get();
        System.out.println(pollTest.getContractAddress());
    }

    public void loadContract(String adress)
    {
        pollTest = PollTest.load(adress, web3j, credentials);
        System.out.println(pollTest.getContractAddress());
    }

    public void setValue(String val) throws ExecutionException, InterruptedException {
        if(pollTest != null)
        {
            pollTest.setTitle(new Utf8String(val)).get();
        }
    }

    public void getValue() throws ExecutionException, InterruptedException {
        if(pollTest != null)
        {
            System.out.println(pollTest.getTitle().get().getValue().toString());
        }
    }

    public static void main(String[] args) {
        String adress = "0xc94f8669016af0fc60a1908458a33015765ce471";
        try
        {
            PollMain main = new PollMain();
          //  main.createContract();
            main.loadContract(adress);
            main.setValue("test");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

}
