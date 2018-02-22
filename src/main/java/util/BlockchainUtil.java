package util;

import election.ElectionData;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
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


    public static void main(String[] args) {
        System.out.println(PATH+BlockchainUtil.getFileName("0x5365a53ffbeadb2bd0d02a16d2f73c50a6999b78"));
    }





}
