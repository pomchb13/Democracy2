package handler;

import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

/**
 * Author:          Patrick Windegger
 * Created on:
 * Description:     Class responsible for sending Ether from one account to another account
 */
public class TransactionHandler {

    private Web3j web3;
    private Credentials credentials;


    /**
     * Constructor for initializing the web3 Http service and credentials
     *
     * @param credentials of the admin
     */
    public TransactionHandler(Credentials credentials) {
        web3 = Web3j.build(new HttpService());
        this.credentials = credentials;
    }

    /**
     * Method responsible for sending a small amount of Ether (0.0001 Ether) from the admin account to a second account.
     * This method is only needed because voter would not be able to vote because they cannot pay the gas price for the
     * transaction.
     *
     * @param toAddress address of the second account where the Ether goes to
     * @throws Exception if the transaction fails
     */
    public void sendTransaction(Address toAddress) throws Exception {
        Transfer.sendFunds(web3, credentials, toAddress.toString(), BigDecimal.valueOf(0.0001), Convert.Unit.ETHER).send();
    }
}
