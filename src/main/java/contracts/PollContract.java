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
    private static final String BINARY = "606060405234156200001057600080fd5b60405162000d8a38038062000d8a83398101604052808051906020019091908051820191906020018051906020019091908051906020019091908051906020019091905050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555084600681620000a59190620000fd565b508360026000019080519060200190620000c192919062000132565b508260026001018190555081600280018190555080600260030160006101000a81548160ff021916908315150217905550505050505062000283565b8154818355818115116200012d576004028160040283600052602060002091820191016200012c9190620001b9565b5b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200017557805160ff1916838001178555620001a6565b82800160010185558215620001a6579182015b82811115620001a557825182559160200191906001019062000188565b5b509050620001b591906200020f565b5090565b6200020c91905b808211156200020857600080820160009055600182016000620001e4919062000237565b600282016000620001f6919062000237565b600382016000905550600401620001c0565b5090565b90565b6200023491905b808211156200023057600081600090555060010162000216565b5090565b90565b50805460018160011615610100020316600290046000825580601f106200025f575062000280565b601f0160209004906000526020600020908101906200027f91906200020f565b5b50565b610af780620002936000396000f300606060405260043610610083576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806302d947ef146100885780632e044ae9146100ca57806337a3c958146101d957806348bb06cc146102825780639e7b8d6114610329578063b2e6b91214610362578063e3183a1b146103b7575b600080fd5b341561009357600080fd5b6100c8600480803590602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610408565b005b34156100d557600080fd5b6100eb6004808035906020019091905050610547565b604051808060200180602001848152602001838103835286818151815260200191508051906020019080838360005b8381101561013557808201518184015260208101905061011a565b50505050905090810190601f1680156101625780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b8381101561019b578082015181840152602081019050610180565b50505050905090810190601f1680156101c85780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b34156101e457600080fd5b610280600480803590602001909190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506106ff565b005b341561028d57600080fd5b6102956107b9565b604051808060200185815260200184815260200183151515158152602001828103825286818151815260200191508051906020019080838360005b838110156102eb5780820151818401526020810190506102d0565b50505050905090810190601f1680156103185780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b341561033457600080fd5b610360600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610893565b005b341561036d57600080fd5b610375610990565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156103c257600080fd5b6103ee600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506109b9565b604051808215151515815260200191505060405180910390f35b600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff168061046857506006805490508210155b1561047257610543565b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160006101000a81548160ff02191690831515021790555081600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010181905550600160068381548110151561052457fe5b9060005260206000209060040201600301600082825401925050819055505b5050565b61054f610a12565b610557610a12565b600060068481548110151561056857fe5b906000526020600020906004020160010160068581548110151561058857fe5b90600052602060002090600402016002016006868154811015156105a857fe5b906000526020600020906004020160030154828054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561064f5780601f106106245761010080835404028352916020019161064f565b820191906000526020600020905b81548152906001019060200180831161063257829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106eb5780601f106106c0576101008083540402835291602001916106eb565b820191906000526020600020905b8154815290600101906020018083116106ce57829003601f168201915b505050505091509250925092509193909250565b8260068481548110151561070f57fe5b9060005260206000209060040201600001819055508160068481548110151561073457fe5b90600052602060002090600402016001019080519060200190610758929190610a26565b508060068481548110151561076957fe5b9060005260206000209060040201600201908051906020019061078d929190610a26565b50600060068481548110151561079f57fe5b906000526020600020906004020160030181905550505050565b6107c1610a12565b600080600060026000016002600101546002800154600260030160009054906101000a900460ff16838054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561087e5780601f106108535761010080835404028352916020019161087e565b820191906000526020600020905b81548152906001019060200180831161086157829003601f168201915b50505050509350935093509350935090919293565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614158061093b5750600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff165b156109455761098d565b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600201819055505b50565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff169050919050565b602060405190810160405280600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610a6757805160ff1916838001178555610a95565b82800160010185558215610a95579182015b82811115610a94578251825591602001919060010190610a79565b5b509050610aa29190610aa6565b5090565b610ac891905b80821115610ac4576000816000905550600101610aac565b5090565b905600a165627a7a72305820e2a7f40efcb3aa495f0159093ee14f46c015fc22579380035f56c2154ce1292b0029";

    private PollContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private PollContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> vote(BigInteger answer, String a) {
        Function function = new Function(
                "vote", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(answer), 
                new org.web3j.abi.datatypes.Address(a)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
