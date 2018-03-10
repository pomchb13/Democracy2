package util;


import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Author:          Christoph Pommer
 * Created on:
 * Description:     This class is responsible for a lot of things needed for the the Blockchain like logging in
 *                  and get the filenames + saving them
 */

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

    /**
     * Method to log in to the Blockchain via the website
     * @param address of the user
     * @param passwd  password of the user
     * @return the credentials of the user
     * @throws IOException
     * @throws CipherException
     */
    public static Credentials loginToBlockhain(String address, String passwd) throws IOException, CipherException {
        return WalletUtils.loadCredentials(passwd,new File(PATH + getFileName(address)));
    }

    /**
     * Method to log in to the Blcokchain with the BlockchainApp because the BlockchainApp can not access the
     * serveltcontext for the path of the walletfiles
     * @param address of the user or admin
     * @param passwd of the user
     * @param alternativePath path where the BlockchainUserfiles are saved
     * @return the credentials of the user
     * @throws IOException
     * @throws CipherException
     */
    public static Credentials loginToBlockhain(String address, String passwd, String alternativePath) throws IOException, CipherException {
        return WalletUtils.loadCredentials(passwd,new File(alternativePath + getFileName(address, alternativePath)));
    }

    /**
     * method to get the filename of the users wallet file
     * because the wallet files that get created do not have the address as the filename we need to get the right
     * filename
     * the path is the path of the directory where the walletfiles are saved
     * @param address of the user
     * @return the filename for the user walletfile
     */
    public static String getFileName(String address)
    {
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

    /**
     * more or like the same method as before, except the path of the directory, because the BlockchainApp can not
     * acess the serveltcontext
     * @param address of the user
     * @param alternativePath path of the walletfiles because the BlockchainApp can not access the servletcontext
     * @return
     */
    public static String getFileName(String address, String alternativePath)
    {
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








}
