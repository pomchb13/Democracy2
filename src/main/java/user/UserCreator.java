package user;

import handler.*;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import beans.VoteType;
import util.AdminReader;
import util.PasswordGenerator;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Patrick Windegger on 10.12.2017.
 */
public class UserCreator {

    private Web3j web3j;
    private static final int PASSWORDLENGTH = 15;

    /**
     * Initialization for the Web3j-Service
     */
    public UserCreator() {
        web3j = Web3j.build(new HttpService());
    }

    /**
     * Creates a new user account
     *
     * @param password        - password of the account
     * @param destinationPath - destination path where the wallet file is saved
     * @throws CipherException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws IOException
     * @returns the address of the account
     */
    public String createNewUserAddress(String password, String destinationPath) throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        String filename = WalletUtils.generateFullNewWalletFile(password, new File(destinationPath));
        Credentials credentials = WalletUtils.loadCredentials(password, destinationPath + filename);
        return credentials.getAddress();
    }


    public void createNewUsers(String path, String walletPath, String contractAddress, VoteType vt, Credentials cr, int anzVoters,String adminFilepath) throws Exception {
        int anzSheets = anzVoters / 1048576;
        if(anzVoters%1048576>0)anzSheets++;
        AdminHandler ah = new AdminHandler(cr);
        ah.loadSmartContract(AdminReader.getAdminContractAddress(adminFilepath));

        TreeMap<String, String> map = new TreeMap<>();
        ElectionHandler el = null;
        PollHandler pl = null;
        System.out.println("Create New Users --> VoteType:"+vt);
        if (vt.equals(VoteType.ELECTION)) {
            el = new ElectionHandler(cr);
            el.loadSmartContract(new Address(contractAddress));
        } else if(vt.equals(VoteType.POLL)) {
            pl = new PollHandler(cr);
            pl.loadSmartContract(new Address(contractAddress));
        }
        TransactionHandler th = new TransactionHandler(cr);

        for (int i = 0; i < anzVoters; i++) {
            String password = PasswordGenerator.createPassword(PASSWORDLENGTH);
            String username = createNewUserAddress(password, walletPath);
            map.put(username, password);
            ah.addContractAddressToVoter(new Address(contractAddress),new Address(username),new Address(cr.getAddress()));
            th.sendTransaction(new Address(username));
            if (vt.equals(VoteType.ELECTION)) {
                el.giveRightToVote(new Address(username));

            } else if(vt.equals(VoteType.POLL)) {
                pl.giveRightToVote(new Address(username));
            }
        }
        ExcelHandler.createExcelFile(path, map, anzSheets);
    }

    /**
     * Main-method for testing purposes
     *
     * @param args
     */
    public static void main(String[] args) {
   /*     String keyStore = "D:\\Ethereum\\geth_data\\keystore\\";
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
        }*/
    }
}
