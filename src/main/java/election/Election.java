package election;

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
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple6;
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
public final class Election extends Contract {
    private static final String BINARY = "606060405234156200001057600080fd5b604051620012773803806200127783398101604052808051906020019091908051820191906020018051906020019091908051906020019091908051906020019091905050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555084600281620000a59190620000fe565b508360046000019080519060200190620000c192919062000133565b50826004600101819055508160046002018190555080600460030160006101000a81548160ff0219169083151502179055505050505050620002ba565b8154818355818115116200012e576007028160070283600052602060002091820191016200012d9190620001ba565b5b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200017657805160ff1916838001178555620001a7565b82800160010185558215620001a7579182015b82811115620001a657825182559160200191906001019062000189565b5b509050620001b6919062000246565b5090565b6200024391905b808211156200023f5760008082016000620001dd91906200026e565b600182016000620001ef91906200026e565b6002820160006200020191906200026e565b60038201600090556004820160006200021b91906200026e565b6005820160006200022d91906200026e565b600682016000905550600701620001c1565b5090565b90565b6200026b91905b80821115620002675760008160009055506001016200024d565b5090565b90565b50805460018160011615610100020316600290046000825580601f10620002965750620002b7565b601f016020900490600052602060002090810190620002b6919062000246565b5b50565b610fad80620002ca6000396000f300606060405260043610610078576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063609ff1bd1461007d5780636652e526146100a65780639e7b8d611461014d578063b81c645314610186578063c7f758a8146101cb578063ce989dfe1461041e575b600080fd5b341561008857600080fd5b610090610599565b6040518082815260200191505060405180910390f35b34156100b157600080fd5b6100b961061a565b604051808060200185815260200184815260200183151515158152602001828103825286818151815260200191508051906020019080838360005b8381101561010f5780820151818401526020810190506100f4565b50505050905090810190601f16801561013c5780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b341561015857600080fd5b610184600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506106f5565b005b341561019157600080fd5b6101c9600480803560ff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610806565b005b34156101d657600080fd5b6101ec600480803590602001909190505061095f565b60405180806020018060200180602001878152602001806020018060200186810386528c818151815260200191508051906020019080838360005b83811015610242578082015181840152602081019050610227565b50505050905090810190601f16801561026f5780820380516001836020036101000a031916815260200191505b5086810385528b818151815260200191508051906020019080838360005b838110156102a857808201518184015260208101905061028d565b50505050905090810190601f1680156102d55780820380516001836020036101000a031916815260200191505b5086810384528a818151815260200191508051906020019080838360005b8381101561030e5780820151818401526020810190506102f3565b50505050905090810190601f16801561033b5780820380516001836020036101000a031916815260200191505b50868103835288818151815260200191508051906020019080838360005b83811015610374578082015181840152602081019050610359565b50505050905090810190601f1680156103a15780820380516001836020036101000a031916815260200191505b50868103825287818151815260200191508051906020019080838360005b838110156103da5780820151818401526020810190506103bf565b50505050905090810190601f1680156104075780820380516001836020036101000a031916815260200191505b509b50505050505050505050505060405180910390f35b341561042957600080fd5b610597600480803590602001909190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001909190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610d6b565b005b60008060008060009250600090505b60028054905081101561061157826002828154811015156105c557fe5b9060005260206000209060070201600601541115610604576002818154811015156105ec57fe5b90600052602060002090600702016006015492508091505b80806001019150506105a8565b81935050505090565b610622610ec8565b60008060006004600001600460010154600460020154600460030160009054906101000a900460ff16838054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106e05780601f106106b5576101008083540402835291602001916106e0565b820191906000526020600020905b8154815290600101906020018083116106c357829003601f168201915b50505050509350935093509350935090919293565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614158061079d5750600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff165b156107a757610803565b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160026101000a81548160ff021916908360ff1602179055505b50565b600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff168061086957506002805490508260ff1610155b156108735761095b565b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160006101000a81548160ff02191690831515021790555081600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160016101000a81548160ff021916908360ff160217905550600160028360ff1681548110151561093c57fe5b9060005260206000209060070201600601600082825401925050819055505b5050565b610967610ec8565b61096f610ec8565b610977610ec8565b6000610981610ec8565b610989610ec8565b60028781548110151561099857fe5b90600052602060002090600702016000016002888154811015156109b857fe5b90600052602060002090600702016001016002898154811015156109d857fe5b906000526020600020906007020160020160028a8154811015156109f857fe5b90600052602060002090600702016003015460028b815481101515610a1957fe5b906000526020600020906007020160040160028c815481101515610a3957fe5b9060005260206000209060070201600501858054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610adf5780601f10610ab457610100808354040283529160200191610adf565b820191906000526020600020905b815481529060010190602001808311610ac257829003601f168201915b50505050509550848054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610b7b5780601f10610b5057610100808354040283529160200191610b7b565b820191906000526020600020905b815481529060010190602001808311610b5e57829003601f168201915b50505050509450838054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c175780601f10610bec57610100808354040283529160200191610c17565b820191906000526020600020905b815481529060010190602001808311610bfa57829003601f168201915b50505050509350818054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610cb35780601f10610c8857610100808354040283529160200191610cb3565b820191906000526020600020905b815481529060010190602001808311610c9657829003601f168201915b50505050509150808054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610d4f5780601f10610d2457610100808354040283529160200191610d4f565b820191906000526020600020905b815481529060010190602001808311610d3257829003601f168201915b5050505050905095509550955095509550955091939550919395565b85600288815481101515610d7b57fe5b90600052602060002090600702016000019080519060200190610d9f929190610edc565b5084600288815481101515610db057fe5b90600052602060002090600702016001019080519060200190610dd4929190610edc565b5083600288815481101515610de557fe5b90600052602060002090600702016002019080519060200190610e09929190610edc565b5082600288815481101515610e1a57fe5b90600052602060002090600702016003018190555081600288815481101515610e3f57fe5b90600052602060002090600702016004019080519060200190610e63929190610edc565b5080600288815481101515610e7457fe5b90600052602060002090600702016005019080519060200190610e98929190610edc565b506000600288815481101515610eaa57fe5b90600052602060002090600702016006018190555050505050505050565b602060405190810160405280600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610f1d57805160ff1916838001178555610f4b565b82800160010185558215610f4b579182015b82811115610f4a578251825591602001919060010190610f2f565b5b509050610f589190610f5c565b5090565b610f7e91905b80821115610f7a576000816000905550600101610f62565b5090565b905600a165627a7a72305820704928044106ae7eb7a692cf4b662b0b1a7c4cc3bd4d7b956c937d19c49114a20029";

    private Election(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Election(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<BigInteger> winningProposal() {
        Function function = new Function("winningProposal", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteCall<TransactionReceipt> vote(BigInteger proposal, String a) {
        Function function = new Function(
                "vote", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(proposal), 
                new org.web3j.abi.datatypes.Address(a)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple6<String, String, String, BigInteger, String, String>> getProposal(BigInteger proposal) {
        final Function function = new Function("getProposal", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(proposal)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple6<String, String, String, BigInteger, String, String>>(
                new Callable<Tuple6<String, String, String, BigInteger, String, String>>() {
                    @Override
                    public Tuple6<String, String, String, BigInteger, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple6<String, String, String, BigInteger, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (String) results.get(5).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> storeProposalData(BigInteger proposal, String p_title, String p_firstname, String p_lastname, BigInteger p_birthday, String p_party, String p_slogan) {
        Function function = new Function(
                "storeProposalData", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(proposal), 
                new org.web3j.abi.datatypes.Utf8String(p_title), 
                new org.web3j.abi.datatypes.Utf8String(p_firstname), 
                new org.web3j.abi.datatypes.Utf8String(p_lastname), 
                new org.web3j.abi.datatypes.generated.Uint256(p_birthday), 
                new org.web3j.abi.datatypes.Utf8String(p_party), 
                new org.web3j.abi.datatypes.Utf8String(p_slogan)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<Election> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger numProposals, String p_title, BigInteger p_date_from, BigInteger p_date_due, Boolean p_show_diagram) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(numProposals), 
                new org.web3j.abi.datatypes.Utf8String(p_title), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_from), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_due), 
                new org.web3j.abi.datatypes.Bool(p_show_diagram)));
        return deployRemoteCall(Election.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Election> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger numProposals, String p_title, BigInteger p_date_from, BigInteger p_date_due, Boolean p_show_diagram) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(numProposals), 
                new org.web3j.abi.datatypes.Utf8String(p_title), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_from), 
                new org.web3j.abi.datatypes.generated.Uint256(p_date_due), 
                new org.web3j.abi.datatypes.Bool(p_show_diagram)));
        return deployRemoteCall(Election.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static Election load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Election(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Election load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Election(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
