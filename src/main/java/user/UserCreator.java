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
 * Author:          Christoph Pommer
 * Created on:
 * Description:     This class is responsible creating a new wallet file and all users needed for the election or poll
 */
public class UserCreator {

    private Web3j web3j;
    //Length of the created password
    private static final int PASSWORDLENGTH = 15;


    /**
     * initializes the web3j object with a new httpservice
     */
    public UserCreator() {
        web3j = Web3j.build(new HttpService());
    }


    /**
     *
     * @param password password for the Blockchain user
     * @param destinationPath path where the Blockchain user wallet file gets saved
     * @return String with the new created users address
     * @throws CipherException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws IOException
     * throws a lot of exceptions because there are a lot of things that can go wrong while creating a new Blockchain user
     */
    public String createNewUserAddress(String password, String destinationPath) throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        String filename = WalletUtils.generateFullNewWalletFile(password, new File(destinationPath));
        Credentials credentials = WalletUtils.loadCredentials(password, destinationPath + filename);
        return credentials.getAddress();
    }


    /**
     * method for creating all the new users.
     * the needed amount of sheets getting calculated
     * with the given votetype the election or poll gets loaded
     * with the given number of needed user accounts, a new map gets created with all the needed data
     * the transactionhandler is needed to give the user a small amount of ether which is needed to vote
     * the for-loop creates the needed amount of useraccounts.
     * in the for-loop a new password gets created with the passwordgenerator class
     * with the newly created password a new username/ address gets created with the createNewUserAddress method
     * the new user gets put into the map
     * after the user is created the user will be mapped to the election or poll in the admincontract via the adminhandler
     * then the user gets his small amount of ether with the Transactionhandler method sendTransaction
     * at last the user gets the rightToVote in the specific election or poll
     * the excelhandler gets the excelFilePath, the newly created usermap and the sheetcount which will create the excel
     * file
     * @param path where the created excel file gets saved for the download
     * @param walletPath path where the wallet files gets saved
     * @param contractAddress every user is created for a specific election or poll and this is the address to this
     *                        election or poll
     * @param vt the type of the vote, election or poll, so we do not to test it
     * @param cr the credentials of the admin for giving the user the right to vote on the specific election or poll
     * @param votersCount count of the voters allowed on the poll or election, also the amount of users that need to be
     *                    created
     * @param adminFilepath because we needed the admincontract, we need this path to load the admincontract
     * @throws Exception throws the exception of the excel handler along, and all the exceptions of the contracts
     */
    public void createNewUsers(String path, String walletPath, String contractAddress, VoteType vt, Credentials cr, int votersCount,String adminFilepath) throws Exception {
        int sheetCount = votersCount / 1048576;
        if(votersCount%1048576>0 &&votersCount<1048576)sheetCount++;
        AdminHandler ah = new AdminHandler(cr);
        ah.loadSmartContract(AdminReader.getAdminContractAddress(adminFilepath));

        TreeMap<String, String> map = new TreeMap<>();
        ElectionHandler el = null;
        PollHandler pl = null;
        if (vt.equals(VoteType.ELECTION)) {
            el = new ElectionHandler(cr);
            el.loadSmartContract(new Address(contractAddress));
        } else if(vt.equals(VoteType.POLL)) {
            pl = new PollHandler(cr);
            pl.loadSmartContract(new Address(contractAddress));
        }
        TransactionHandler th = new TransactionHandler(cr);

        for (int i = 0; i < votersCount; i++) {
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
        ExcelHandler.createExcelFile(path, map, sheetCount);
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
