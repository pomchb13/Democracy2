package contracttest;

import java.lang.String;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.web3j.tx.Contract;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.</p>
 */
public final class PollContract extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b5b6103318061001f6000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806372910be014610054578063ae819e13146100b1578063ff3c1a8f1461019f575b600080fd5b341561005f57600080fd5b6100af600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190505061022e565b005b34156100bc57600080fd5b61019d600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919080351515906020019091905050610235565b005b34156101aa57600080fd5b6101b2610248565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101f35780820151818401525b6020810190506101d7565b50505050905090810190601f1680156102205780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b8090505b50565b8393508292508191508090505b50505050565b6102506102f1565b60008054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102e65780601f106102bb576101008083540402835291602001916102e6565b820191906000526020600020905b8154815290600101906020018083116102c957829003601f168201915b505050505090505b90565b6020604051908101604052806000815250905600a165627a7a72305820fcbbd5a4274abbc2ba77180e51031b323677a5d86344983c1eda935581b369130029";

    public PollContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public PollContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public Future<TransactionReceipt> setTitle(Utf8String title) {
        Function function = new Function("setTitle", Arrays.asList(title), Collections.emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> savePoll(Utf8String title, Utf8String fromDate, Utf8String toDate, Bool showDiagram) {
        Function function = new Function("savePoll", Arrays.asList(title, fromDate, toDate, showDiagram), Collections.emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Utf8String> getTitle() {
        Function function = new Function("getTitle",
                Arrays.asList(), 
                Arrays.asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<PollContract> deploy(Web3j web3j, Credentials credentials, BigInteger initialValue) {
        return deployAsync(PollContract.class, web3j, credentials, new BigInteger("0"), new BigInteger("300000"), BINARY, "", initialValue);
    }

    public static PollContract load(String contractAddress, Web3j web3j, Credentials credentials) {
        return new PollContract(contractAddress, web3j, credentials, new BigInteger("0"), new BigInteger("4700000"));
    }
}
