package contracttest;

import java.lang.String;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.web3j.tx.Contract;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.</p>
 */
public final class PollTest extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b5b6102de8061001f6000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806372910be014610049578063ff3c1a8f146100a6575b600080fd5b341561005457600080fd5b6100a4600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610135565b005b34156100b157600080fd5b6100b9610150565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100fa5780820151818401525b6020810190506100de565b50505050905090810190601f1680156101275780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b806000908051906020019061014b9291906101f9565b505b50565b610158610279565b60008054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156101ee5780601f106101c3576101008083540402835291602001916101ee565b820191906000526020600020905b8154815290600101906020018083116101d157829003601f168201915b505050505090505b90565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061023a57805160ff1916838001178555610268565b82800160010185558215610268579182015b8281111561026757825182559160200191906001019061024c565b5b509050610275919061028d565b5090565b602060405190810160405280600081525090565b6102af91905b808211156102ab576000816000905550600101610293565b5090565b905600a165627a7a723058202d8518efaad2a730ba65a7029dd057c04d4fe43062267585ee78ef40c12789ca0029";

    public PollTest(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public Future<TransactionReceipt> setTitle(Utf8String title) {
        Function function = new Function("setTitle", Arrays.asList(title), Collections.emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Utf8String> getTitle() {
        Function function = new Function("getTitle",
                Arrays.asList(), 
                Arrays.asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<PollTest> deploy(Web3j web3j, Credentials credentials, BigInteger initialValue) {
        return deployAsync(PollTest.class, web3j, credentials, new BigInteger("0"), new BigInteger("300000"), BINARY, "", initialValue);
    }

    public static PollTest load(String contractAddress, Web3j web3j, Credentials credentials) {
        return new PollTest(contractAddress, web3j, credentials, new BigInteger("0"), new BigInteger("4700000"));
    }
}
