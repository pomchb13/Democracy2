package user;

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
 * Created by Patrick Windegger on 10.12.2017.
 */
public class UserCreator {

    private Web3j web3j;

    /**
     * Initialization for the Web3j-Service
     */
    public UserCreator()
    {
        web3j = Web3j.build(new HttpService());
    }

    /**
     * Creates a new user account
     * @param password - password of the account
     * @param destinationPath - destination path where the wallet file is saved
     * @returns the address of the account
     * @throws CipherException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws IOException
     */
    public String createNewUser(String password, String destinationPath) throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        String filename = WalletUtils.generateFullNewWalletFile(password, new File(destinationPath));
        Credentials credentials = WalletUtils.loadCredentials(password, destinationPath + filename);
        
        return credentials.getAddress();
    }

    /**
     * Main-method for testing purposes
     * @param args
     */
    public static void main(String[] args) {
        String keyStore = "D:\\Ethereum\\geth_data\\keystore\\";
        String password = "1234";
        UserCreator creator = new UserCreator();
        try {
            String address = creator.createNewUser(password, keyStore);
            System.out.println(address);
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
