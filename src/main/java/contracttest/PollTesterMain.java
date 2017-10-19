package contracttest;

import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * Created by Patri on 12.10.2017.
 */
public class PollTesterMain {

    private Web3j web3;
    private Credentials credentials;
    private PollCon pollContract;

    public PollTesterMain() throws IOException, CipherException {
        // creates the interface to the blockchain and initialize path to the wallet
        web3 = Web3j.build(new HttpService());
        credentials = WalletUtils.loadCredentials("1234", "D:\\Ethereum\\geth_data\\keystore\\UTC--2017-10-19T14-38-48.526096700Z--dcc97f1bd80b47137480d2a3d9a54a0af6aa92be");
    }

    public void createContract() {
        try {
            pollContract = PollCon.deploy(web3, credentials, BigInteger.ZERO).get();
            System.out.println(pollContract.getContractAddress());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void setTitle(String title)
    {
        if(pollContract != null)
        {
            Utf8String utf8String = new Utf8String(title);
            pollContract.setTitle(utf8String);
        }
    }

    public String getTitle() throws ExecutionException, InterruptedException {
        if(pollContract != null)
        {
            return pollContract.getTitle().get().getValue().toString();
        }
        return null;
    }

    public void loadSmartContract(String adress) {
        pollContract = PollCon.load(adress, web3, credentials);
        System.out.println(pollContract.getContractAddress());
    }

    public static void main(String[] args) {
        try {
            String adress = "0xbb165f4E6153Cc69923748f98341b177f5CbA5f4";
            PollTesterMain pollTest = new PollTesterMain();
            //pollTest.createContract();

            pollTest.loadSmartContract(adress);
            pollTest.setTitle("Testtitle");


            System.out.println(pollTest.getTitle());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
