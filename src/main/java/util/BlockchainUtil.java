package util;

import handler.ElectionHandler;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import handler.PollHandler;
import test.VoteType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BlockchainUtil {

    private static String PATH;

    public static void setPATH(String newPath)
    {
        PATH = newPath + File.separator;
    }

    public static String getPATH()
    {
        return PATH;
    }

    public static Credentials loginToBlockhain(String address, String passwd) throws IOException, CipherException {
        return WalletUtils.loadCredentials(passwd,new File(PATH + getFileName(address)));
    }

    public static Credentials loginToBlockhain(String address, String passwd, String alternativePath) throws IOException, CipherException {
        return WalletUtils.loadCredentials(passwd,new File(alternativePath + getFileName(address, alternativePath)));
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

    public static String getFileName(String address, String alternativePath)
    {
        //5365a53ffbeadb2bd0d02a16d2f73c50a6999b78
        address = address.substring(2);
        File file = new File(alternativePath);
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


    //old method. DONT USE!!!!
    @Deprecated
    public static boolean checkIfAdmin(String adminAddress, String contractAddress, VoteType type, Credentials cr) throws Exception {
        if(type.equals(VoteType.ELECTION))
        {
            ElectionHandler handler = new ElectionHandler(cr);
            handler.loadSmartContract(new Address(contractAddress));
            String realAdminAddress = handler.getAdminAddress();
            if(adminAddress.equals(realAdminAddress))
            {
                return true;
            }
        }
        else if(type.equals(VoteType.POLL))
        {
            PollHandler handler = new PollHandler(cr);
            handler.loadSmartContract(new Address(contractAddress));
            String realAdminAddress = handler.getAdminAddress();
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
