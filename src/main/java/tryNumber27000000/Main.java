package tryNumber27000000;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.RawTransaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;


import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class Main {

    private Web3j web3;

    public Main()
    {
        web3 = Web3j.build(new HttpService());

        try
        {
            firstRequest();
            sendTransaction();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

   public void firstRequest() throws ExecutionException, InterruptedException {

       Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().sendAsync().get();
       String clientVersion = web3ClientVersion.getWeb3ClientVersion();
       print(clientVersion);
   }


   public void print(String str)
   {
       System.out.println(str);
   }


   public void sendTransaction() throws IOException, CipherException, ExecutionException, InterruptedException {
     Credentials credentials = WalletUtils.loadCredentials("1234","D:/Blockchain/geth/geth_data/keystore/UTC--2017-07-14T08-57-20.373735900Z--39f7373999930b3385d241f27aff6c6d21b273d7");

       EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount("0x39f7373999930b3385D241F27AFf6C6d21b273d7", DefaultBlockParameterName.LATEST).sendAsync().get();
       BigInteger nonce = ethGetTransactionCount.getTransactionCount();

       RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce,null,null,"0x6205EF7Ea17795F4DBC8494D669B265f7E51a9cd",new BigInteger("10"));

       byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction,credentials);
       String hexValue = Numeric.toHexString(signedMessage);
       EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();

       print(ethSendTransaction.getError().toString());

   }


    public static void main(String[] args) {
        Main main = new Main();
    }








}
