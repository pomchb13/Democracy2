package contracttest;

import java.lang.String;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.web3j.tx.Contract;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.</p>
 */
public final class Poll extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b6104038061001e6000396000f300606060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063609ff1bd14610067578063940f851c146100965780639e7b8d61146100bc578063b3f98adc146100f5575b600080fd5b341561007257600080fd5b61007a61011b565b604051808260ff1660ff16815260200191505060405180910390f35b34156100a157600080fd5b6100ba600480803560ff16906020019091905050610199565b005b34156100c757600080fd5b6100f3600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506101ee565b005b341561010057600080fd5b610119600480803560ff169060200190919050506102a4565b005b6000806000809150600090505b6002805490508160ff161015610194578160028260ff1681548110151561014b57fe5b90600052602060002090016000015411156101875760028160ff1681548110151561017257fe5b90600052602060002090016000015491508092505b8080600101915050610128565b505090565b336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508060ff166002816101ea9190610384565b5050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415806102965750600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff165b156102a0576102a1565b5b50565b6000600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002090508060000160009054906101000a900460ff168061030c57506002805490508260ff1610155b1561031657610380565b60018160000160006101000a81548160ff021916908315150217905550818160000160016101000a81548160ff021916908360ff160217905550600160028360ff1681548110151561036457fe5b9060005260206000209001600001600082825401925050819055505b5050565b8154818355818115116103ab578183600052602060002091820191016103aa91906103b0565b5b505050565b6103d491905b808211156103d057600080820160009055506001016103b6565b5090565b905600a165627a7a723058206680eae1bce207efab1f7efe9e249587d2af126520bac55743bbafd32080ab650029";

    public Poll(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public Future<Uint8> winningProposal() {
        Function function = new Function("winningProposal",
                Arrays.asList(), 
                Arrays.asList(new TypeReference<Uint8>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> Ballot(Uint8 _numProposals) {
        Function function = new Function("Ballot", Arrays.asList(_numProposals), Collections.emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> giveRightToVote(Address voter) {
        Function function = new Function("giveRightToVote", Arrays.asList(voter), Collections.emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> vote(Uint8 proposal) {
        Function function = new Function("vote", Arrays.asList(proposal), Collections.emptyList());
        return executeTransactionAsync(function);
    }

    public static Future<Poll> deploy(Web3j web3j, Credentials credentials, BigInteger initialValue) {
        return deployAsync(Poll.class, web3j, credentials, new BigInteger("0"), new BigInteger("300000"), BINARY, "", initialValue);
    }

    public static Poll load(String contractAddress, Web3j web3j, Credentials credentials) {
        return new Poll(contractAddress, web3j, credentials, new BigInteger("0"), new BigInteger("4700000"));
    }
}
