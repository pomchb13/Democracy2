package example;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * Created by Patrick Windegger on 18.07.2017.
 */
@Deprecated
public class TransactionTester {
    public static void main(String[] args) {
        try {


            Web3j web3 = Web3j.build(new HttpService());

            // account-address of the wallet
            String account = web3.ethAccounts().sendAsync().get().getAccounts().get(0);

            // address of the contract
            String storage = "0xA13a30C315E2dE522Dc4Aa34e55BBc63177B2840";

            // data that we want to send to the contract
            String data = "0x4ef65c3b000000000000000000000000000000000000000000000000000000000000000a"; // a = 10

            // index for the transaction
            BigInteger nonce = new BigInteger("100");

            // gas price for the transaction
            EthGasPrice ethGasPrice = web3.ethGasPrice().sendAsync().get();
            BigInteger gasPrice = ethGasPrice.getGasPrice();

            // gas limit for the transaction
            BigInteger gaslimit = new BigInteger("300000");

            Transaction transaction = new Transaction(account, nonce, gasPrice, gaslimit, storage, BigDecimal.valueOf(0.0).toBigInteger(), data);

            EthSendTransaction result = web3.ethSendTransaction(transaction).sendAsync().get();

            System.out.println(result.getResult());
            if (result.hasError()) {
                System.out.println("Transaction Failed!");
                System.out.println(result.getError().getMessage());
            } else {
                System.out.println("Transaction successful!");
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}