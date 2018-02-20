package poll;

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
public final class Poll extends Contract {
    private static final String BINARY = "606060405234156200001057600080fd5b60405162000e4338038062000e4383398101604052808051906020019091908051820191906020018051906020019091908051906020019091908051906020019091905050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555084600681620000a59190620000fd565b508360026000019080519060200190620000c192919062000132565b508260026001018190555081600280018190555080600260030160006101000a81548160ff02191690831515021790555050505050506200027b565b8154818355818115116200012d576003028160030283600052602060002091820191016200012c9190620001b9565b5b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200017557805160ff1916838001178555620001a6565b82800160010185558215620001a6579182015b82811115620001a557825182559160200191906001019062000188565b5b509050620001b5919062000207565b5090565b6200020491905b80821115620002005760008082016000620001dc91906200022f565b600182016000620001ee91906200022f565b600282016000905550600301620001c0565b5090565b90565b6200022c91905b80821115620002285760008160009055506001016200020e565b5090565b90565b50805460018160011615610100020316600290046000825580601f1062000257575062000278565b601f01602090049060005260206000209081019062000277919062000207565b5b50565b610bb8806200028b6000396000f30060606040526004361061008e576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680632e044ae91461009357806337a3c958146101a257806348bb06cc1461024b5780636597e028146102f25780639e7b8d611461031b578063b2e6b91214610354578063b81c6453146103a9578063e3183a1b146103ee575b600080fd5b341561009e57600080fd5b6100b4600480803590602001909190505061043f565b604051808060200180602001848152602001838103835286818151815260200191508051906020019080838360005b838110156100fe5780820151818401526020810190506100e3565b50505050905090810190601f16801561012b5780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b83811015610164578082015181840152602081019050610149565b50505050905090810190601f1680156101915780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b34156101ad57600080fd5b610249600480803590602001909190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506105f7565b005b341561025657600080fd5b61025e61068c565b604051808060200185815260200184815260200183151515158152602001828103825286818151815260200191508051906020019080838360005b838110156102b4578082015181840152602081019050610299565b50505050905090810190601f1680156102e15780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b34156102fd57600080fd5b610305610766565b6040518082815260200191505060405180910390f35b341561032657600080fd5b610352600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506107e7565b005b341561035f57600080fd5b6103676108f8565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156103b457600080fd5b6103ec600480803560ff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610921565b005b34156103f957600080fd5b610425600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610a7a565b604051808215151515815260200191505060405180910390f35b610447610ad3565b61044f610ad3565b600060068481548110151561046057fe5b906000526020600020906003020160000160068581548110151561048057fe5b90600052602060002090600302016001016006868154811015156104a057fe5b906000526020600020906003020160020154828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105475780601f1061051c57610100808354040283529160200191610547565b820191906000526020600020905b81548152906001019060200180831161052a57829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105e35780601f106105b8576101008083540402835291602001916105e3565b820191906000526020600020905b8154815290600101906020018083116105c657829003601f168201915b505050505091509250925092509193909250565b8160068481548110151561060757fe5b9060005260206000209060030201600001908051906020019061062b929190610ae7565b508060068481548110151561063c57fe5b90600052602060002090600302016001019080519060200190610660929190610ae7565b50600060068481548110151561067257fe5b906000526020600020906003020160020181905550505050565b610694610ad3565b600080600060026000016002600101546002800154600260030160009054906101000a900460ff16838054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107515780601f1061072657610100808354040283529160200191610751565b820191906000526020600020905b81548152906001019060200180831161073457829003601f168201915b50505050509350935093509350935090919293565b60008060008060009250600090505b6006805490508110156107de578260068281548110151561079257fe5b90600052602060002090600302016002015411156107d1576006818154811015156107b957fe5b90600052602060002090600302016002015492508091505b8080600101915050610775565b81935050505090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614158061088f5750600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff165b15610899576108f5565b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160026101000a81548160ff021916908360ff1602179055505b50565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff168061098457506006805490508260ff1610155b1561098e57610a76565b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160006101000a81548160ff02191690831515021790555081600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160016101000a81548160ff021916908360ff160217905550600160068360ff16815481101515610a5757fe5b9060005260206000209060030201600201600082825401925050819055505b5050565b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff169050919050565b602060405190810160405280600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610b2857805160ff1916838001178555610b56565b82800160010185558215610b56579182015b82811115610b55578251825591602001919060010190610b3a565b5b509050610b639190610b67565b5090565b610b8991905b80821115610b85576000816000905550600101610b6d565b5090565b905600a165627a7a72305820a3c485f1ad583fa4ea4e755edf97133de942f8855a7d4970544515c6084105f90029";

    private Poll(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Poll(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<Tuple3<String, String, BigInteger>> getAnswerData(BigInteger answer) {
        final Function function = new Function("getAnswerData", 
                Arrays.<Type>asList(new Uint256(answer)),
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
                Arrays.<Type>asList(new Uint256(answer),
                new Utf8String(p_title),
                new Utf8String(p_description)),
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
                Arrays.<Type>asList(new Address(voter)),
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
                new Address(a)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> getAlreadyVotedForVoter(String a) {
        Function function = new Function("getAlreadyVotedForVoter", 
                Arrays.<Type>asList(new Address(a)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public static RemoteCall<Poll> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger numAnswers, String p_title, BigInteger p_date_from, BigInteger p_date_due, Boolean p_show_diagram) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(numAnswers),
                new Utf8String(p_title),
                new Uint256(p_date_from),
                new Uint256(p_date_due),
                new Bool(p_show_diagram)));
        return deployRemoteCall(Poll.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Poll> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger numAnswers, String p_title, BigInteger p_date_from, BigInteger p_date_due, Boolean p_show_diagram) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(numAnswers),
                new Utf8String(p_title),
                new Uint256(p_date_from),
                new Uint256(p_date_due),
                new Bool(p_show_diagram)));
        return deployRemoteCall(Poll.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static Poll load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Poll(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Poll load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Poll(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
