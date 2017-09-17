package example;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by Patrick on 17.09.2017.
 */
public class UserCreationTest {

    private Web3j web3;

    public UserCreationTest() {
        web3 = Web3j.build(new HttpService());
    }

    public void addUser() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
        String filename = WalletUtils.generateNewWalletFile("1234", new File("D:/eth_test/"), false);
    }

    public static void main(String[] args) {
        UserCreationTest uct = new UserCreationTest();
        try {
            uct.addUser();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
