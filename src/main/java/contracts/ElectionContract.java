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
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple7;
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
public final class ElectionContract extends Contract {
    private static final String BINARY = "606060405234156200001057600080fd5b604051620014ca380380620014ca83398101604052808051906020019091908051820191906020018051906020019091908051906020019091908051906020019091905050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555084600281620000a59190620000fd565b508360036000019080519060200190620000c192919062000132565b508260036001018190555081600360020181905550806003800160006101000a81548160ff0219169083151502179055505050505050620002c1565b8154818355818115116200012d576008028160080283600052602060002091820191016200012c9190620001b9565b5b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200017557805160ff1916838001178555620001a6565b82800160010185558215620001a6579182015b82811115620001a557825182559160200191906001019062000188565b5b509050620001b591906200024d565b5090565b6200024a91905b808211156200024657600080820160009055600182016000620001e4919062000275565b600282016000620001f6919062000275565b60038201600062000208919062000275565b600482016000905560058201600062000222919062000275565b60068201600062000234919062000275565b600782016000905550600801620001c0565b5090565b90565b6200027291905b808211156200026e57600081600090555060010162000254565b5090565b90565b50805460018160011615610100020316600290046000825580601f106200029d5750620002be565b601f016020900490600052602060002090810190620002bd91906200024d565b5b50565b6111f980620002d16000396000f30060606040526004361061008e576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806335b8e820146100935780633628ea48146102ed5780636652e526146104685780639e7b8d611461050f578063b2e6b91214610548578063b81c64531461059d578063e3183a1b146105e2578063e328297e14610633575b600080fd5b341561009e57600080fd5b6100b460048080359060200190919050506106ac565b60405180806020018060200180602001888152602001806020018060200187815260200186810386528d818151815260200191508051906020019080838360005b838110156101105780820151818401526020810190506100f5565b50505050905090810190601f16801561013d5780820380516001836020036101000a031916815260200191505b5086810385528c818151815260200191508051906020019080838360005b8381101561017657808201518184015260208101905061015b565b50505050905090810190601f1680156101a35780820380516001836020036101000a031916815260200191505b5086810384528b818151815260200191508051906020019080838360005b838110156101dc5780820151818401526020810190506101c1565b50505050905090810190601f1680156102095780820380516001836020036101000a031916815260200191505b50868103835289818151815260200191508051906020019080838360005b83811015610242578082015181840152602081019050610227565b50505050905090810190601f16801561026f5780820380516001836020036101000a031916815260200191505b50868103825288818151815260200191508051906020019080838360005b838110156102a857808201518184015260208101905061028d565b50505050905090810190601f1680156102d55780820380516001836020036101000a031916815260200191505b509c5050505050505050505050505060405180910390f35b34156102f857600080fd5b610466600480803590602001909190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001909190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610adf565b005b341561047357600080fd5b61047b610c61565b604051808060200185815260200184815260200183151515158152602001828103825286818151815260200191508051906020019080838360005b838110156104d15780820151818401526020810190506104b6565b50505050905090810190601f1680156104fe5780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b341561051a57600080fd5b610546600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610d3b565b005b341561055357600080fd5b61055b610ecd565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156105a857600080fd5b6105e0600480803560ff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610ef6565b005b34156105ed57600080fd5b610619600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190505061104f565b604051808215151515815260200191505060405180910390f35b341561063e57600080fd5b61066a600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506110a8565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6106b4611114565b6106bc611114565b6106c4611114565b60006106ce611114565b6106d6611114565b60006002888154811015156106e757fe5b906000526020600020906008020160010160028981548110151561070757fe5b906000526020600020906008020160020160028a81548110151561072757fe5b906000526020600020906008020160030160028b81548110151561074757fe5b90600052602060002090600802016004015460028c81548110151561076857fe5b906000526020600020906008020160050160028d81548110151561078857fe5b906000526020600020906008020160060160028e8154811015156107a857fe5b906000526020600020906008020160070154868054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561084f5780601f106108245761010080835404028352916020019161084f565b820191906000526020600020905b81548152906001019060200180831161083257829003601f168201915b50505050509650858054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108eb5780601f106108c0576101008083540402835291602001916108eb565b820191906000526020600020905b8154815290600101906020018083116108ce57829003601f168201915b50505050509550848054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156109875780601f1061095c57610100808354040283529160200191610987565b820191906000526020600020905b81548152906001019060200180831161096a57829003601f168201915b50505050509450828054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610a235780601f106109f857610100808354040283529160200191610a23565b820191906000526020600020905b815481529060010190602001808311610a0657829003601f168201915b50505050509250818054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610abf5780601f10610a9457610100808354040283529160200191610abf565b820191906000526020600020905b815481529060010190602001808311610aa257829003601f168201915b505050505091509650965096509650965096509650919395979092949650565b86600288815481101515610aef57fe5b90600052602060002090600802016000018190555085600288815481101515610b1457fe5b90600052602060002090600802016001019080519060200190610b38929190611128565b5084600288815481101515610b4957fe5b90600052602060002090600802016002019080519060200190610b6d929190611128565b5083600288815481101515610b7e57fe5b90600052602060002090600802016003019080519060200190610ba2929190611128565b5082600288815481101515610bb357fe5b90600052602060002090600802016004018190555081600288815481101515610bd857fe5b90600052602060002090600802016005019080519060200190610bfc929190611128565b5080600288815481101515610c0d57fe5b90600052602060002090600802016006019080519060200190610c31929190611128565b506000600288815481101515610c4357fe5b90600052602060002090600802016007018190555050505050505050565b610c69611114565b600080600060036000016003600101546003600201546003800160009054906101000a900460ff16838054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610d265780601f10610cfb57610100808354040283529160200191610d26565b820191906000526020600020905b815481529060010190602001808311610d0957829003601f168201915b50505050509350935093509350935090919293565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141580610de35750600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff165b15610ded57610eca565b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160026101000a81548160ff021916908360ff16021790555030600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160036101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b50565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff1680610f5957506002805490508260ff1610155b15610f635761104b565b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160006101000a81548160ff02191690831515021790555081600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160016101000a81548160ff021916908360ff160217905550600160028360ff1681548110151561102c57fe5b9060005260206000209060080201600701600082825401925050819055505b5050565b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff169050919050565b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160039054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b602060405190810160405280600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061116957805160ff1916838001178555611197565b82800160010185558215611197579182015b8281111561119657825182559160200191906001019061117b565b5b5090506111a491906111a8565b5090565b6111ca91905b808211156111c65760008160009055506001016111ae565b5090565b905600a165627a7a72305820d4e425362002301bb0e287c13f4678364151f92575d7da6ddae6af5aff0d06820029";

    private ElectionContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private ElectionContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<Tuple7<String, String, String, BigInteger, String, String, BigInteger>> getCandidate(BigInteger candidate) {
        final Function function = new Function("getCandidate", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(candidate)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple7<String, String, String, BigInteger, String, String, BigInteger>>(
                new Callable<Tuple7<String, String, String, BigInteger, String, String, BigInteger>>() {
                    @Override
                    public Tuple7<String, String, String, BigInteger, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple7<String, String, String, BigInteger, String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> storeCandidateData(BigInteger candidate, String p_title, String p_firstname, String p_lastname, BigInteger p_birthday, String p_party, String p_slogan) {
        Function function = new Function(
                "storeCandidateData", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(candidate), 
                new org.web3j.abi.datatypes.Utf8String(p_title), 
                new org.web3j.abi.datatypes.Utf8String(p_firstname), 
                new org.web3j.abi.datatypes.Utf8String(p_lastname), 
                new org.web3j.abi.datatypes.generated.Uint256(p_birthday), 
                new org.web3j.abi.datatypes.Utf8String(p_party), 
                new org.web3j.abi.datatypes.Utf8String(p_slogan)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple4<String, BigInteger, BigInteger, Boolean>> getElectionData() {
        final Function function = new Function("getElectionData", 
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

    public RemoteCall<TransactionReceipt> vote(BigInteger candidate, String a) {
        Function function = new Function(
                "vote", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(candidate), 
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

    public RemoteCall<String> getVoteAddressForVoter(String a) {
        Function function = new Function("getContractAddressForVoter",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(a)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<ElectionContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger numCandidates, String p_title, BigInteger p_date_from, BigInteger p_date_due, Boolean p_show_diagram) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(numCandidates), 
                new org.web3j.abi.datatypes.Utf8String(p_title), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_from), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_due), 
                new org.web3j.abi.datatypes.Bool(p_show_diagram)));
        return deployRemoteCall(ElectionContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<ElectionContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger numCandidates, String p_title, BigInteger p_date_from, BigInteger p_date_due, Boolean p_show_diagram) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(numCandidates), 
                new org.web3j.abi.datatypes.Utf8String(p_title), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_from), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_due), 
                new org.web3j.abi.datatypes.Bool(p_show_diagram)));
        return deployRemoteCall(ElectionContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static ElectionContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ElectionContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static ElectionContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ElectionContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
