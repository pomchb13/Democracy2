package handler;

import contracts.AdminContract;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:          Patrick Windegger
 * Created on:
 * Description:     Class responsible for handling the admins of elections and polls.
 * All methods of the contract are implemented in this handler.
 * The communication between java and the Blockchain is also implemented here.
 */

public class AdminHandler {

    private Web3j web3;
    private Credentials credentials;
    private AdminContract admin;

    /**
     * Constructor to initiliaze the web3j web-service
     *
     * @param credentials: credentials of the user (wallet file)
     */
    public AdminHandler(Credentials credentials) {
        web3 = Web3j.build(new HttpService());
        this.credentials = credentials;
    }

    /**
     * Method responsible for creating a new admin contract
     *
     * @return address of the contract
     * @throws Exception if an error occurs
     */
    public String createSmartContract() throws Exception {
        admin = AdminContract.deploy(web3, credentials, new BigInteger("0"), new BigInteger("4700000")).send();
        return admin.getContractAddress();
    }

    /**
     * Method responsible for loading an existing contrat with a specific address
     *
     * @param contractAddress: address of the contract
     * @return address of the contract
     */
    public String loadSmartContract(Address contractAddress) {
        admin = AdminContract.load(contractAddress.toString(), web3, credentials, new BigInteger("0"), new BigInteger("4700000"));
        return admin.getContractAddress();
    }

    /**
     * Method responsible for checking if a user is admin
     *
     * @param userAddress: address of a user
     * @return true if the user is admin, false if the user is not admin
     * @throws Exception if the admin contract is not loaded
     */
    public boolean checkIfAdmin(Address userAddress) throws Exception {
        if (admin != null) {
            return admin.checkIfAdmin(userAddress.toString()).send();
        } else {
            throw new Exception("admin object is null!");
        }
    }

    /**
     * Method responsible for getting the current amount of admins
     *
     * @param senderAddress: address of the sender to check if he is allowed to perform this operation
     * @return amount of admins
     * @throws Exception if the admin contract is not loaded
     */
    public int getAdminCount(Address senderAddress) throws Exception {
        if (checkIfAdmin(senderAddress)) {
            if (admin != null) {
                return admin.getAdminCount().send().intValue();
            } else {
                throw new Exception("admin object is null!");
            }
        } else {
            throw new Exception("sender address is not allowed to perform this operation!");
        }
    }

    /**
     * Method responsible for getting all admins
     *
     * @param senderAddress: address of the sender to check if he is allowed to perform this operation
     * @return list of all admins
     * @throws Exception if the admin contract is not loaded
     */
    public List<Address> getAllAdmins(Address senderAddress) throws Exception {
        if (checkIfAdmin(senderAddress)) {
            if (admin != null) {
                List<Address> admins = new ArrayList<>();
                int numAdmins = admin.getAdminCount().send().intValue();
                for (int i = 0; i < numAdmins; i++) {
                    admins.add(new Address(admin.getAdmin(new BigInteger(i + "")).send()));
                }
                return admins;
            } else {
                throw new Exception("admin object is null!");
            }
        } else {
            throw new Exception("sender address is not allowed to perform this operation!");
        }


    }

    /**
     * Method responsible for getting all stored contract addresses
     *
     * @param senderAddress: address of the sender to check if he is allowed to perform this operation
     * @return list of contract addresses
     * @throws Exception if the admin contract is not loaded
     */
    public List<Address> getAllContractAddresses(Address senderAddress) throws Exception {
        if (checkIfAdmin(senderAddress)) {
            if (admin != null) {
                List<Address> contracts = new ArrayList<>();
                int numContracts = admin.getContractCount().send().intValue();
                for (int i = 0; i < numContracts; i++) {
                    contracts.add(new Address(admin.getContractAddress(new BigInteger(i + "")).send()));
                }
                return contracts;
            } else {
                throw new Exception("admin object is null!");
            }
        } else {
            throw new Exception("sender address is not allowed to perform this operation!");
        }
    }

    /**
     * Method responsible for getting the current amount of contracts
     *
     * @param senderAddress: address of the sender to check if he is allowed to perform this operation
     * @return amount of contracts
     * @throws Exception if the admin contract is not loaded
     */
    public int getContractCount(Address senderAddress) throws Exception {
        if (checkIfAdmin(senderAddress)) {
            if (admin != null) {
                return admin.getContractCount().send().intValue();
            } else {
                throw new Exception("admin object is null!");
            }
        } else {
            throw new Exception("sender address is not allowed to perform this operation!");
        }
    }

    /**
     * Method responsible for adding a new admin to the contract
     *
     * @param newAdminAddress: address of the new admin
     * @param senderAddress:   address of the sender to check if he is allowed to perform this operation
     * @throws Exception if the admin contract is not loaded
     */
    public void addAdminAddress(Address newAdminAddress, Address senderAddress) throws Exception {
        if (checkIfAdmin(senderAddress)) {
            if (admin != null) {
                admin.addAdmin(newAdminAddress.toString()).send();
            } else {
                throw new Exception("admin object is null!");
            }

        } else {
            throw new Exception("sender address is not allowed to perform this operation!");
        }
    }

    /**
     * Method responsible for adding a contract address
     *
     * @param contractAddress: address of the contract
     * @param senderAddress:   address of the sender to check if he is allowed to perform this operation
     * @throws Exception if the admin contract is not loaded
     */
    public void addContractAddress(Address contractAddress, Address senderAddress) throws Exception {
        if (checkIfAdmin(senderAddress)) {
            if (admin != null) {
                admin.addContractAddress(contractAddress.toString()).send();
            } else {
                throw new Exception("admin object is null!");
            }
        } else {
            throw new Exception("sender address is not allowed to perform this operation!");
        }
    }

    /**
     * Method responsible for mapping the contract address to the voter address
     *
     * @param contractAddress: address of the contract
     * @param voterAddress:    address of the voter
     * @param senderAddress:   address of the sender to check if he is allowed to perform this operation
     * @throws Exception if the admin contract is not loaded
     */
    public void addContractAddressToVoter(Address contractAddress, Address voterAddress, Address senderAddress) throws Exception {
        if (checkIfAdmin(senderAddress)) {
            if (admin != null) {
                admin.addContractAddressToVoter(contractAddress.toString(), voterAddress.toString()).send();
            } else {
                throw new Exception("admin object is null!");
            }
        }
    }

    /**
     * Method responsible for getting the address of the contract where the voter is allowed to vote
     *
     * @param voterAddress: address of the voter
     * @return address of the contract
     * @throws Exception if the admin contract is not loaded
     */
    public Address getContractAddressForVoter(Address voterAddress) throws Exception {
        if (admin != null) {
            return new Address(admin.getContractAddressForVoter(voterAddress.toString()).send());
        } else {
            throw new Exception("admin object is null!");
        }
    }
}
