package poll;

import java.lang.String;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.web3j.tx.Contract;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.</p>
 */
public final class Poll extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b6040516020806105888339810160405280805190602001909190505060006100356100e1565b336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600091505b8260ff168210156100d95760206040519081016040528060008152509050600280548060010182816100ac91906100f5565b9160005260206000209001600083909190915060008201518160000155505050818060010192505061007a565b505050610148565b602060405190810160405280600081525090565b81548183558181151161011c5781836000526020600020918201910161011b9190610121565b5b505050565b61014591905b808211156101415760008082016000905550600101610127565b5090565b90565b610431806101576000396000f300606060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630645b8db146100675780639e7b8d611461009e578063b81c6453146100d7578063d826f88f1461011c575b600080fd5b341561007257600080fd5b6100886004808035906020019091905050610131565b6040518082815260200191505060405180910390f35b34156100a957600080fd5b6100d5600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610158565b005b34156100e257600080fd5b61011a600480803560ff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610269565b005b341561012757600080fd5b61012f6103bf565b005b600060028281548110151561014257fe5b9060005260206000209001600001549050919050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415806102005750600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff165b1561020a57610266565b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160026101000a81548160ff021916908360ff1602179055505b50565b600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff16806102cc57506002805490508260ff1610155b156102d6576103bb565b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160006101000a81548160ff02191690831515021790555081600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160016101000a81548160ff021916908360ff160217905550600160028360ff1681548110151561039f57fe5b9060005260206000209001600001600082825401925050819055505b5050565b60008090505b6002805490508110156104025760006002828154811015156103e357fe5b90600052602060002090016000018190555080806001019150506103c5565b505600a165627a7a723058200d8b26c8f56d3ddec702f98bff45bd32ae6ffc4df660e439f9be0acd6f1c5e330029";

    public Poll(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public Poll(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public Future<Uint256> getProposalVoteCount(Uint256 num) {
        Function function = new Function("getProposalVoteCount",
                Arrays.asList(num), 
                Arrays.asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> giveRightToVote(Address voter) {
        Function function = new Function("giveRightToVote", Arrays.asList(voter), Collections.emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> vote(Uint8 proposal, Address a) {
        Function function = new Function("vote", Arrays.asList(proposal, a), Collections.emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> reset() {
        Function function = new Function("reset", Arrays.asList(), Collections.emptyList());
        return executeTransactionAsync(function);
    }

    public static Future<Poll> deploy(Web3j web3j, Credentials credentials, BigInteger initialValue, Uint8 _numProposals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(_numProposals));
        return deployAsync(Poll.class,web3j, credentials, new BigInteger("0"), new BigInteger("300000"), BINARY, encodedConstructor, initialValue);
    }

    public static Poll load(String contractAddress, Web3j web3j, Credentials credentials) {
        return new Poll(contractAddress, web3j, credentials, new BigInteger("0"), new BigInteger("4700000"));
    }
}
