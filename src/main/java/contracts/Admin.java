package contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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
public final class Admin extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b600160008161001e919061009d565b5060016002819055506000600181610036919061009d565b5060006003819055503360008081548110151561004f57fe5b906000526020600020900160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506100ee565b8154818355818115116100c4578183600052602060002091820191016100c391906100c9565b5b505050565b6100eb91905b808211156100e75760008160009055506001016100cf565b5090565b90565b610552806100fd6000396000f30060606040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806331ae450b146100725780633b33b61a146100dc5780637048027514610146578063b11ce2db1461017f578063d9fa9c1a146101b8575b600080fd5b341561007d57600080fd5b610085610209565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156100c85780820151818401526020810190506100ad565b505050509050019250505060405180910390f35b34156100e757600080fd5b6100ef61029d565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b83811015610132578082015181840152602081019050610117565b505050509050019250505060405180910390f35b341561015157600080fd5b61017d600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610331565b005b341561018a57600080fd5b6101b6600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506103b5565b005b34156101c357600080fd5b6101ef600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610421565b604051808215151515815260200191505060405180910390f35b6102116104c1565b600080548060200260200160405190810160405280929190818152602001828054801561029357602002820191906000526020600020905b8160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019060010190808311610249575b5050505050905090565b6102a56104c1565b600180548060200260200160405190810160405280929190818152602001828054801561032757602002820191906000526020600020905b8160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190600101908083116102dd575b5050505050905090565b60026000815480929190600101919050555060025460008161035391906104d5565b5080600060016002540381548110151561036957fe5b906000526020600020900160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b80600160036000815480929190600101919050558154811015156103d557fe5b906000526020600020900160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b6000805b6000805490508110156104b6578273ffffffffffffffffffffffffffffffffffffffff1660008281548110151561045857fe5b906000526020600020900160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156104a957600191506104bb565b8080600101915050610425565b600091505b50919050565b602060405190810160405280600081525090565b8154818355818115116104fc578183600052602060002091820191016104fb9190610501565b5b505050565b61052391905b8082111561051f576000816000905550600101610507565b5090565b905600a165627a7a72305820e59333f783fbbac577ff2978273c73a247d190473af16c541b2de4de025506a80029";

    private Admin(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Admin(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<List<String>> getAdmins() {
        Function function = new Function("getAdmins", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return executeRemoteCallSingleValueReturn(function, List<String>.class);
    }

    public RemoteCall<List<String>> getAllContractAddresses() {
        Function function = new Function("getAllContractAddresses", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return executeRemoteCallSingleValueReturn(function, List<String>.class);
    }

    public RemoteCall<TransactionReceipt> addAdmin(String a) {
        Function function = new Function(
                "addAdmin", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(a)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public static RemoteCall<Admin> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Admin.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Admin> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Admin.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Admin load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Admin(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Admin load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Admin(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
