package util;

import election.ElectionData;
import election.ElectionTester;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import poll.PollTester;
import test.VoteType;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class BlockchainUtil {

    private static final String PATH = "D:\\Ethereum\\geth_data\\keystore\\";

    public static Credentials loginToBlockhain(String address, String passwd)
    {
        Credentials cr = null;
        try {
            cr = WalletUtils.loadCredentials(passwd,new File(PATH + getFileName(address)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (CipherException e) {
            e.printStackTrace();
            return null;
        }
        return cr;
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
