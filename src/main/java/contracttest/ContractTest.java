package contracttest;

import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * Created by Patrick on 01.08.2017.
 * working correctly
 */
public class ContractTest {

    private Web3j web3;
    private Credentials credentials;
    private SimpleStorage simpleStorage;

    public ContractTest() throws IOException, CipherException {
        // creates the interface to the blockchain and initialize path to the wallet
        web3 = Web3j.build(new HttpService());
        credentials = WalletUtils.loadCredentials("1234", "D:\\Ethereum\\geth_data\\keystore\\UTC--2017-07-31T09-23-09.749158100Z--fd8d9bdb0e2c1951a428126761c3dfdf13bf28fb");
    }

    /***
     * Method responsible for creating a new smart contract
     */
    public void createContract() {
        try {
            simpleStorage = SimpleStorage.deploy(web3, credentials, BigInteger.ZERO).get();
            System.out.println(simpleStorage.getContractAddress());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    /***
     * Method responsible for loading an existing contract with a specific adress
     * @param adress
     */
    public void loadSmartContract(String adress) {
        simpleStorage = SimpleStorage.load(adress, web3, credentials);
        System.out.println(simpleStorage.getContractAddress());
    }

    /***
     * Method responsible for setting a value in the contract
     */
    public void set() {
        if (simpleStorage != null) {
            try {
                simpleStorage.setUint(new Uint256(150)).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * Method responsible getting the value of the contract
     */
    public void get() {
        if (simpleStorage != null) {
            try {
                System.out.println(simpleStorage.getUint().get().getValue().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            String adress = "0x2a9e0977428e82e1bc6a0b987398ea2baec67a11";
            ContractTest tester = new ContractTest();
            tester.createContract();
            tester.set();
            tester.get();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }


    }
}
