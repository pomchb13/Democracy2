package contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.1.1.
 */
public final class AdminContract extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b600160008161001e919061009d565b5060016002819055506000600181610036919061009d565b5060006003819055503360008081548110151561004f57fe5b906000526020600020900160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506100ee565b8154818355818115116100c4578183600052602060002091820191016100c391906100c9565b5b505050565b6100eb91905b808211156100e75760008160009055506001016100cf565b5090565b90565b61050b806100fd6000396000f300606060405260043610610083576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063111fd88b1461008857806370480275146100eb5780638b7bf3eb146101245780639399869d1461014d578063aefa7d9814610176578063b11ce2db146101d9578063d9fa9c1a14610212575b600080fd5b341561009357600080fd5b6100a96004808035906020019091905050610263565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156100f657600080fd5b610122600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506102a6565b005b341561012f57600080fd5b61013761032a565b6040518082815260200191505060405180910390f35b341561015857600080fd5b610160610334565b6040518082815260200191505060405180910390f35b341561018157600080fd5b610197600480803590602001909190505061033e565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156101e457600080fd5b610210600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610382565b005b341561021d57600080fd5b610249600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506103ee565b604051808215151515815260200191505060405180910390f35b6000808281548110151561027357fe5b906000526020600020900160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b6002600081548092919060010191905055506002546000816102c8919061048e565b508060006001600254038154811015156102de57fe5b906000526020600020900160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b6000600254905090565b6000600354905090565b600060018281548110151561034f57fe5b906000526020600020900160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b80600160036000815480929190600101919050558154811015156103a257fe5b906000526020600020900160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b6000805b600080549050811015610483578273ffffffffffffffffffffffffffffffffffffffff1660008281548110151561042557fe5b906000526020600020900160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156104765760019150610488565b80806001019150506103f2565b600091505b50919050565b8154818355818115116104b5578183600052602060002091820191016104b491906104ba565b5b505050565b6104dc91905b808211156104d85760008160009055506001016104c0565b5090565b905600a165627a7a723058205e19128564a84acb1bcf798fb3218ee6ac4a2ca372ae02e6e71a90db89129ed40029";

    private AdminContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private AdminContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<String> getAdmin(BigInteger i) {
        Function function = new Function("getAdmin", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(i)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> addAdmin(String a) {
        Function function = new Function(
                "addAdmin", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(a)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getAdminCount() {
        Function function = new Function("getAdminCount", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getContractCount() {
        Function function = new Function("getContractCount", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> getContractAddress(BigInteger i) {
        Function function = new Function("getContractAddress", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(i)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> addContractAddress(String a) {
        Function function = new Function(
                "addContractAddress", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(a)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> checkIfAdmin(String a) {
        Function function = new Function("checkIfAdmin", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(a)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public static RemoteCall<AdminContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AdminContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<AdminContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AdminContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static AdminContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AdminContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static AdminContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AdminContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
