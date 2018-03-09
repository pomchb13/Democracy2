package handler;

import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import util.BlockchainUtil;

import java.io.File;
import java.math.BigDecimal;

public class TransactionHandler {

    private Web3j web3;
    private Credentials credentials;


    public TransactionHandler(Credentials credentials) {
        web3 = Web3j.build(new HttpService());
        this.credentials = credentials;
    }


    public void sendTransaction(Address to) throws Exception {
        Transfer.sendFunds(web3, credentials, to.toString(), BigDecimal.valueOf(0.0001), Convert.Unit.ETHER).send();
    }


    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + File.separator + "out" + File.separator + "artifacts"
                + File.separator + "test_webapp2_war_exploded" + File.separator + "res"
                + File.separator + "geth_data" + File.separator + "keystore" + File.separator;
        try {
            Credentials cr = BlockchainUtil.loginToBlockhain("0x6514c5525dcb6fdb4dab3f8301c6d3f7a77454cf", "fZVLl{d=x]]vNthJB9/u", path);
            TransactionHandler th = new TransactionHandler(cr);
            th.sendTransaction(new Address("0x06a8a2da16cdbf9320a704057a17094586a7f3aa"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
