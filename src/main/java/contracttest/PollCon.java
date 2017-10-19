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
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.</p>
 */
public final class PollCon extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b5b6104168061001f6000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680634a79d50c1461005457806372910be0146100e3578063ff3c1a8f14610140575b600080fd5b341561005f57600080fd5b6100676101cf565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100a85780820151818401525b60208101905061008c565b50505050905090810190601f1680156100d55780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156100ee57600080fd5b61013e600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190505061026d565b005b341561014b57600080fd5b610153610288565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101945780820151818401525b602081019050610178565b50505050905090810190601f1680156101c15780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b60008054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102655780601f1061023a57610100808354040283529160200191610265565b820191906000526020600020905b81548152906001019060200180831161024857829003601f168201915b505050505081565b8060009080519060200190610283929190610331565b505b50565b6102906103b1565b60008054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103265780601f106102fb57610100808354040283529160200191610326565b820191906000526020600020905b81548152906001019060200180831161030957829003601f168201915b505050505090505b90565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061037257805160ff19168380011785556103a0565b828001600101855582156103a0579182015b8281111561039f578251825591602001919060010190610384565b5b5090506103ad91906103c5565b5090565b602060405190810160405280600081525090565b6103e791905b808211156103e35760008160009055506001016103cb565b5090565b905600a165627a7a72305820aeb9d0807f11dc893814d46042bcdf9204cf3525fa5cbb4e55649142441ca28c0029";



    public PollCon(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public PollCon(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public Future<Utf8String> title() {
        Function function = new Function("title",
                Arrays.asList(), 
                Arrays.asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setTitle(Utf8String title1) {
        Function function = new Function("setTitle", Arrays.asList(title1), Collections.emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Utf8String> getTitle() {
        Function function = new Function("getTitle",
                Arrays.asList(), 
                Arrays.asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<PollCon> deploy(Web3j web3j, Credentials credentials, BigInteger initialValue) {
        return deployAsync(PollCon.class, web3j, credentials, new BigInteger("0"), new BigInteger("300000"), BINARY, "", initialValue);
    }

    public static PollCon load(String contractAddress, Web3j web3j, Credentials credentials) {
        return new PollCon(contractAddress, web3j, credentials, new BigInteger("0"), new BigInteger("4700000"));
    }
}
