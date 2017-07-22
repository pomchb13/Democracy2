package example;

import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * Created by Patri on 22.07.2017.
 */
public class SmartContractTester {

    public static void main(String[] args) {
        try {
            Web3j web3 = Web3j.build(new HttpService());

            Credentials credentials = WalletUtils.loadCredentials("1234", "D:\\Ethereum\\geth_data\\keystore\\UTC--2017-07-11T14-18-36.244202600Z--53f9e575980cfa1bd10b5b547e3c4311e8270b93");


           // SimpleStorage contract = SimpleStorage.deploy(web3, credentials, BigInteger.ZERO).get();
           // contract.setUint(new Uint256(15));
           // System.out.println(contract.getUint().get().toString());

            SimpleStorage2 contract2 = SimpleStorage2.deploy(web3, credentials, new BigInteger("0"), new BigInteger("300000"), BigInteger.ZERO).get();
            contract2.set(new Uint256(new BigInteger("10")));

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
