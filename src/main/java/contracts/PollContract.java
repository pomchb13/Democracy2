package contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
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
public final class PollContract extends Contract {
    private static final String BINARY = "606060405234156200001057600080fd5b60405162000dbb38038062000dbb83398101604052808051906020019091908051820191906020018051906020019091908051906020019091908051906020019091905050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555084600681620000a59190620000fd565b508360026000019080519060200190620000c192919062000132565b508260026001018190555081600280018190555080600260030160006101000a81548160ff021916908315150217905550505050505062000283565b8154818355818115116200012d576004028160040283600052602060002091820191016200012c9190620001b9565b5b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200017557805160ff1916838001178555620001a6565b82800160010185558215620001a6579182015b82811115620001a557825182559160200191906001019062000188565b5b509050620001b591906200020f565b5090565b6200020c91905b808211156200020857600080820160009055600182016000620001e4919062000237565b600282016000620001f6919062000237565b600382016000905550600401620001c0565b5090565b90565b6200023491905b808211156200023057600081600090555060010162000216565b5090565b90565b50805460018160011615610100020316600290046000825580601f106200025f575062000280565b601f0160209004906000526020600020908101906200027f91906200020f565b5b50565b610b2880620002936000396000f300606060405260043610610083576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680632e044ae91461008857806337a3c9581461019757806348bb06cc146102405780639e7b8d61146102e7578063b2e6b91214610320578063b81c645314610375578063e3183a1b146103ba575b600080fd5b341561009357600080fd5b6100a9600480803590602001909190505061040b565b604051808060200180602001848152602001838103835286818151815260200191508051906020019080838360005b838110156100f35780820151818401526020810190506100d8565b50505050905090810190601f1680156101205780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b8381101561015957808201518184015260208101905061013e565b50505050905090810190601f1680156101865780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b34156101a257600080fd5b61023e600480803590602001909190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506105c3565b005b341561024b57600080fd5b61025361067d565b604051808060200185815260200184815260200183151515158152602001828103825286818151815260200191508051906020019080838360005b838110156102a957808201518184015260208101905061028e565b50505050905090810190601f1680156102d65780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b34156102f257600080fd5b61031e600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610757565b005b341561032b57600080fd5b610333610868565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561038057600080fd5b6103b8600480803560ff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610891565b005b34156103c557600080fd5b6103f1600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506109ea565b604051808215151515815260200191505060405180910390f35b610413610a43565b61041b610a43565b600060068481548110151561042c57fe5b906000526020600020906004020160010160068581548110151561044c57fe5b906000526020600020906004020160020160068681548110151561046c57fe5b906000526020600020906004020160030154828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105135780601f106104e857610100808354040283529160200191610513565b820191906000526020600020905b8154815290600101906020018083116104f657829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105af5780601f10610584576101008083540402835291602001916105af565b820191906000526020600020905b81548152906001019060200180831161059257829003601f168201915b505050505091509250925092509193909250565b826006848154811015156105d357fe5b906000526020600020906004020160000181905550816006848154811015156105f857fe5b9060005260206000209060040201600101908051906020019061061c929190610a57565b508060068481548110151561062d57fe5b90600052602060002090600402016002019080519060200190610651929190610a57565b50600060068481548110151561066357fe5b906000526020600020906004020160030181905550505050565b610685610a43565b600080600060026000016002600101546002800154600260030160009054906101000a900460ff16838054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107425780601f1061071757610100808354040283529160200191610742565b820191906000526020600020905b81548152906001019060200180831161072557829003601f168201915b50505050509350935093509350935090919293565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415806107ff5750600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff165b1561080957610865565b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160026101000a81548160ff021916908360ff1602179055505b50565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff16806108f457506006805490508260ff1610155b156108fe576109e6565b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160006101000a81548160ff02191690831515021790555081600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160016101000a81548160ff021916908360ff160217905550600160068360ff168154811015156109c757fe5b9060005260206000209060040201600301600082825401925050819055505b5050565b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff169050919050565b602060405190810160405280600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610a9857805160ff1916838001178555610ac6565b82800160010185558215610ac6579182015b82811115610ac5578251825591602001919060010190610aaa565b5b509050610ad39190610ad7565b5090565b610af991905b80821115610af5576000816000905550600101610add565b5090565b905600a165627a7a7230582099e0c4c2ebddb5a135b7033881b90edb74d1851acdf26954ab7b4765dff290a10029";

    private PollContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private PollContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<Tuple3<String, String, BigInteger>> getAnswerData(BigInteger answer) {
        final Function function = new Function("getAnswerData", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(answer)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple3<String, String, BigInteger>>(
                new Callable<Tuple3<String, String, BigInteger>>() {
                    @Override
                    public Tuple3<String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple3<String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> storeAnswerData(BigInteger answer, String p_title, String p_description) {
        Function function = new Function(
                "storeAnswerData", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(answer), 
                new org.web3j.abi.datatypes.Utf8String(p_title), 
                new org.web3j.abi.datatypes.Utf8String(p_description)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple4<String, BigInteger, BigInteger, Boolean>> getPollData() {
        final Function function = new Function("getPollData", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple4<String, BigInteger, BigInteger, Boolean>>(
                new Callable<Tuple4<String, BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple4<String, BigInteger, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple4<String, BigInteger, BigInteger, Boolean>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> giveRightToVote(String voter) {
        Function function = new Function(
                "giveRightToVote", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(voter)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getAdminAddress() {
        Function function = new Function("getAdminAddress", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> vote(BigInteger answer, String a) {
        Function function = new Function(
                "vote", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(answer), 
                new org.web3j.abi.datatypes.Address(a)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> getAlreadyVotedForVoter(String a) {
        Function function = new Function("getAlreadyVotedForVoter", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(a)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public static RemoteCall<PollContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger numAnswers, String p_title, BigInteger p_date_from, BigInteger p_date_due, Boolean p_show_diagram) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(numAnswers), 
                new org.web3j.abi.datatypes.Utf8String(p_title), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_from), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_due), 
                new org.web3j.abi.datatypes.Bool(p_show_diagram)));
        return deployRemoteCall(PollContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<PollContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger numAnswers, String p_title, BigInteger p_date_from, BigInteger p_date_due, Boolean p_show_diagram) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(numAnswers), 
                new org.web3j.abi.datatypes.Utf8String(p_title), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_from), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_due), 
                new org.web3j.abi.datatypes.Bool(p_show_diagram)));
        return deployRemoteCall(PollContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static PollContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PollContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static PollContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PollContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
