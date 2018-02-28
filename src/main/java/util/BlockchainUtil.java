package util;

import election.ElectionTester;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import poll.PollTester;
import test.VoteType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BlockchainUtil {

    private static final String PATH = "D:\\Ethereum\\geth_data\\keystore\\";

    public static Credentials loginToBlockhain(String address, String passwd) throws IOException, CipherException {
        return WalletUtils.loadCredentials(passwd,new File(PATH + getFileName(address)));
    }

    public static String getFileName(String address)
    {

        //5365a53ffbeadb2bd0d02a16d2f73c50a6999b78

        address = address.substring(2);
        File file = new File(PATH);
        if(file.exists() && file.isDirectory())
        {
            String[] files = file.list();
            for(String fileName : files)
            {
                if(fileName.toLowerCase().contains(address.toLowerCase()))
                {
                    return fileName;
                }
            }
        }
        return "";
    }


    //TODO: umschreiben wenn Admin contract
    public static boolean checkIfAdmin(String adminAddress, String contractAddress, VoteType type, Credentials cr) throws Exception {
        if(type.equals(VoteType.ELECTION))
        {
            ElectionTester et = new ElectionTester(cr);
            et.loadSmartContract(contractAddress);
            String realAdminAddress = et.getAdminAddress();
            if(adminAddress.equals(realAdminAddress))
            {
                return true;
            }
        }
        else if(type.equals(VoteType.POLL))
        {
            PollTester pt = new PollTester(cr);
            pt.loadSmartContract(contractAddress);
            String realAdminAddress = pt.getAdminAddress();
            if(adminAddress.equals(realAdminAddress))
            {
                return true;
            }
        }
        return false;
    }

    public static void saveContractAddress(String address,String path) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(address);
        bw.flush();
        bw.close();
    }

    public static String readContractAdress(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String address = br.readLine();
        return address;
    }

    public static void afterVote(String userpath) throws IOException {
        Files.delete(Paths.get(userpath));
    }

    public static void main(String[] args) {
        System.out.println(PATH+BlockchainUtil.getFileName("0x5365a53ffbeadb2bd0d02a16d2f73c50a6999b78"));
    }





}
