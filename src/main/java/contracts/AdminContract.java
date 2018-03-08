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
    private static final String BINARY = "6060604052341561000f57600080fd5b600160008161001e919061009d565b50600160028190555060c8600181610036919061009d565b5060006003819055503360008081548110151561004f57fe5b906000526020600020900160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506100ee565b8154818355818115116100c4578183600052602060002091820191016100c391906100c9565b5b505050565b6100eb91905b808211156100e75760008160009055506001016100cf565b5090565b90565b6106e1806100fd6000396000f300606060405260043610610099576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063111fd88b1461009e5780637048027514610101578063760dd58d1461013a5780638b7bf3eb146101925780639399869d146101bb578063aefa7d98146101e4578063b11ce2db14610247578063d23887db14610280578063d9fa9c1a146102f9575b600080fd5b34156100a957600080fd5b6100bf600480803590602001909190505061034a565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561010c57600080fd5b610138600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190505061038d565b005b341561014557600080fd5b610190600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610411565b005b341561019d57600080fd5b6101a5610493565b6040518082815260200191505060405180910390f35b34156101c657600080fd5b6101ce61049d565b6040518082815260200191505060405180910390f35b34156101ef57600080fd5b61020560048080359060200190919050506104a7565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561025257600080fd5b61027e600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506104eb565b005b341561028b57600080fd5b6102b7600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190505061055b565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561030457600080fd5b610330600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506105c4565b604051808215151515815260200191505060405180910390f35b6000808281548110151561035a57fe5b906000526020600020900160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b6002600081548092919060010191905055506002546000816103af9190610664565b508060006001600254038154811015156103c557fe5b906000526020600020900160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b81600460008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050565b6000600254905090565b6000600354905090565b60006001828154811015156104b857fe5b906000526020600020900160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b8060016003548154811015156104fd57fe5b906000526020600020900160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060036000815480929190600101919050555050565b6000600460008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b6000805b600080549050811015610659578273ffffffffffffffffffffffffffffffffffffffff166000828154811015156105fb57fe5b906000526020600020900160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141561064c576001915061065e565b80806001019150506105c8565b600091505b50919050565b81548183558181151161068b5781836000526020600020918201910161068a9190610690565b5b505050565b6106b291905b808211156106ae576000816000905550600101610696565b5090565b905600a165627a7a723058209ad7210c1562e5a0005438b922bfda1a94b67a3b11b9b40f727645f7dabce19b0029";

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

    public RemoteCall<TransactionReceipt> addContractAddressToVoter(String contractAddress, String voterAddress) {
        Function function = new Function(
                "addContractAddressToVoter", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(contractAddress), 
                new org.web3j.abi.datatypes.Address(voterAddress)), 
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

    public RemoteCall<String> getContractAddressForVoter(String voterAddress) {
        Function function = new Function("getContractAddressForVoter", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(voterAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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
