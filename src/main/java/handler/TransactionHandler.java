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

/**
 * Author:          Patrick Windegger
 * Created on:
 * Description:
 */
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
}
