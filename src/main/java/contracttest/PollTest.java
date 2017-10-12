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
 * Created by Patri on 12.10.2017.
 */
public class PollTest {

    private Web3j web3;
    private Credentials credentials;
    private PollContract pollContract;

    public PollTest() throws IOException, CipherException {
        // creates the interface to the blockchain and initialize path to the wallet
        web3 = Web3j.build(new HttpService());
        credentials = WalletUtils.loadCredentials("1234", "D:\\Ethereum\\geth_data\\keystore\\UTC--2017-09-17T15-16-20.800696700Z--2f9f430557f022a3217ee94191adca1d648706b5");
    }

    public void createContract() {
        try {
            pollContract = PollContract.deploy(web3, credentials, BigInteger.ZERO).get();
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
        pollContract = PollContract.load(adress, web3, credentials);
        System.out.println(pollContract.getContractAddress());
    }

    public static void main(String[] args) {
        try {
            String adress = "0x65798d24b6b5bf463ac792bd25ba8270f9e5e85c";
            PollTest pollTest = new PollTest();
            pollTest.loadSmartContract(adress);
            pollTest.setTitle("Testtitle");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }

}
