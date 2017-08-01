package contracttest;

import java.lang.String;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;

import org.web3j.tx.Contract;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * changed things:
 * super-constructor in SimpleStorage --> gasPrice and gasLimit added
 * removed <> from Function(...) in getUint and setUint
 * added gasPrice and gasLimit to deployAsync method in deploy
 * changed binarx bytecode to the contract bytecode of the online compiler
 * Please use {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.</p>
 */
public final class SimpleStorage extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b5b60b88061001e6000396000f300606060405263ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416620267a4811460455780634ef65c3b146067575b600080fd5b3415604f57600080fd5b6055607c565b60405190815260200160405180910390f35b3415607157600080fd5b607a6004356083565b005b6000545b90565b60008190555b505600a165627a7a72305820569feb0218a177897f5234b991a3daed3efb02336b4fb46a8357d3024e6b4a7f0029";

    public SimpleStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public SimpleStorage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public Future<Uint256> getUint() {
        Function function = new Function("getUint",
                Arrays.asList(),
                Arrays.asList(new TypeReference<Uint256>() {
                }));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setUint(Uint256 x) {
        Function function = new Function("setUint", Arrays.asList(x), Collections.emptyList());
        return executeTransactionAsync(function);
    }

    public static Future<SimpleStorage> deploy(Web3j web3j, Credentials credentials, BigInteger initialValue) {
        return deployAsync(SimpleStorage.class, web3j, credentials, new BigInteger("0"), new BigInteger("300000"), BINARY, "", initialValue);
    }


    public static SimpleStorage load(String contractAddress, Web3j web3j, Credentials credentials) {
        return new SimpleStorage(contractAddress, web3j, credentials, new BigInteger("0"), new BigInteger("4700000"));
    }
}
