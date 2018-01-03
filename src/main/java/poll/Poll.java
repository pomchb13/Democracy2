package poll;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
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
public final class Poll extends Contract {
    private static final String BINARY = "606060405234156200001057600080fd5b60405162000cd738038062000cd783398101604052808051906020019091908051820191906020018051906020019091908051906020019091908051906020019091905050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555084600681620000a59190620000fd565b508360026000019080519060200190620000c192919062000132565b508260026001018190555081600280018190555080600260030160006101000a81548160ff02191690831515021790555050505050506200027b565b8154818355818115116200012d576003028160030283600052602060002091820191016200012c9190620001b9565b5b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200017557805160ff1916838001178555620001a6565b82800160010185558215620001a6579182015b82811115620001a557825182559160200191906001019062000188565b5b509050620001b5919062000207565b5090565b6200020491905b80821115620002005760008082016000620001dc91906200022f565b600182016000620001ee91906200022f565b600282016000905550600301620001c0565b5090565b90565b6200022c91905b80821115620002285760008160009055506001016200020e565b5090565b90565b50805460018160011615610100020316600290046000825580601f1062000257575062000278565b601f01602090049060005260206000209081019062000277919062000207565b5b50565b610a4c806200028b6000396000f300606060405260043610610078576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680632e044ae91461007d57806337a3c9581461018557806348bb06cc1461022e5780636597e028146102d55780639e7b8d61146102fe578063b81c645314610337575b600080fd5b341561008857600080fd5b61009e600480803590602001909190505061037c565b604051808060200180602001838103835285818151815260200191508051906020019080838360005b838110156100e25780820151818401526020810190506100c7565b50505050905090810190601f16801561010f5780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b8381101561014857808201518184015260208101905061012d565b50505050905090810190601f1680156101755780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b341561019057600080fd5b61022c600480803590602001909190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190505061050d565b005b341561023957600080fd5b6102416105a2565b604051808060200185815260200184815260200183151515158152602001828103825286818151815260200191508051906020019080838360005b8381101561029757808201518184015260208101905061027c565b50505050905090810190601f1680156102c45780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b34156102e057600080fd5b6102e861067c565b6040518082815260200191505060405180910390f35b341561030957600080fd5b610335600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506106fd565b005b341561034257600080fd5b61037a600480803560ff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff1690602001909190505061080e565b005b610384610967565b61038c610967565b60068381548110151561039b57fe5b90600052602060002090600302016000016006848154811015156103bb57fe5b9060005260206000209060030201600101818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104615780601f1061043657610100808354040283529160200191610461565b820191906000526020600020905b81548152906001019060200180831161044457829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104fd5780601f106104d2576101008083540402835291602001916104fd565b820191906000526020600020905b8154815290600101906020018083116104e057829003601f168201915b5050505050905091509150915091565b8160068481548110151561051d57fe5b9060005260206000209060030201600001908051906020019061054192919061097b565b508060068481548110151561055257fe5b9060005260206000209060030201600101908051906020019061057692919061097b565b50600060068481548110151561058857fe5b906000526020600020906003020160020181905550505050565b6105aa610967565b600080600060026000016002600101546002800154600260030160009054906101000a900460ff16838054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106675780601f1061063c57610100808354040283529160200191610667565b820191906000526020600020905b81548152906001019060200180831161064a57829003601f168201915b50505050509350935093509350935090919293565b60008060008060009250600090505b6006805490508110156106f457826006828154811015156106a857fe5b90600052602060002090600302016002015411156106e7576006818154811015156106cf57fe5b90600052602060002090600302016002015492508091505b808060010191505061068b565b81935050505090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415806107a55750600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff165b156107af5761080b565b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160026101000a81548160ff021916908360ff1602179055505b50565b600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff168061087157506006805490508260ff1610155b1561087b57610963565b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160006101000a81548160ff02191690831515021790555081600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160016101000a81548160ff021916908360ff160217905550600160068360ff1681548110151561094457fe5b9060005260206000209060030201600201600082825401925050819055505b5050565b602060405190810160405280600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106109bc57805160ff19168380011785556109ea565b828001600101855582156109ea579182015b828111156109e95782518255916020019190600101906109ce565b5b5090506109f791906109fb565b5090565b610a1d91905b80821115610a19576000816000905550600101610a01565b5090565b905600a165627a7a72305820bb999fda97d5dea4fb7dc334905b4c2630bbd22071c5182fe6ddc63cc1af66990029";

    private Poll(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Poll(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<Tuple2<String, String>> getAnswerData(BigInteger answer) {
        final Function function = new Function("getAnswerData", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(answer)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple2<String, String>>(
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
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

    public RemoteCall<BigInteger> winningAnswer() {
        Function function = new Function("winningAnswer", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> giveRightToVote(String voter) {
        Function function = new Function(
                "giveRightToVote", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(voter)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> vote(BigInteger answer, String a) {
        Function function = new Function(
                "vote", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(answer), 
                new org.web3j.abi.datatypes.Address(a)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<Poll> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger numAnswers, String p_title, BigInteger p_date_from, BigInteger p_date_due, Boolean p_show_diagram) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(numAnswers), 
                new org.web3j.abi.datatypes.Utf8String(p_title), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_from), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_due), 
                new org.web3j.abi.datatypes.Bool(p_show_diagram)));
        return deployRemoteCall(Poll.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Poll> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger numAnswers, String p_title, BigInteger p_date_from, BigInteger p_date_due, Boolean p_show_diagram) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(numAnswers), 
                new org.web3j.abi.datatypes.Utf8String(p_title), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_from), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_due), 
                new org.web3j.abi.datatypes.Bool(p_show_diagram)));
        return deployRemoteCall(Poll.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static Poll load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Poll(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Poll load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Poll(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
