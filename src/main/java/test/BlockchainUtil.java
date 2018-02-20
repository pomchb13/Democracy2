package test;

import election.ElectionData;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class BlockchainUtil {

    private Web3j web3;


    public BlockchainUtil()
    {
        web3 = Web3j.build(new HttpService());
    }


    public Credentials loginToBlockhain(String address, String passwd)
    {
        Credentials cr = null;
        try {
            cr = WalletUtils.loadCredentials(passwd,address);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (CipherException e) {
            e.printStackTrace();
            return null;
        }
        return cr;
    }






}
