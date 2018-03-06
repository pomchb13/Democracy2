package handler;

import contracts.AdminContract;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import util.BlockchainUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AdminHandler {

    private Web3j web3;
    private Credentials credentials;
    private AdminContract admin;

    // for testing purpose only
    public AdminHandler() throws IOException, CipherException {
        web3 = Web3j.build(new HttpService());
        credentials = BlockchainUtil.loginToBlockhain("0xdcc97f1bd80b47137480d2a3d9a54a0af6aa92be", "1234");
    }

    public AdminHandler(Credentials credentials) {
        web3 = Web3j.build(new HttpService());
        this.credentials = credentials;
    }

    public String createSmartContract() throws Exception {
        admin = AdminContract.deploy(web3, credentials, new BigInteger("0"), new BigInteger("4700000")).send();
        return admin.getContractAddress();
    }

    public String loadSmartContract(Address address) {
        admin = AdminContract.load(address.toString(), web3, credentials, new BigInteger("0"), new BigInteger("4700000"));
        return  admin.getContractAddress();
    }

    public boolean checkIfAdmin(Address address) throws Exception {
        if (admin != null) {
            return admin.checkIfAdmin(address.toString()).send();
        }
        else
        {
            throw new Exception("admin object is null!");
        }
    }

    public List<Address> getAllAdmins(Address senderAddress) throws Exception {
        if (checkIfAdmin(senderAddress)) {
            if (admin != null) {
                List<Address> admins = new ArrayList<>();
                int numAdmins = admin.getAdminCount().send().intValue();
                for (int i = 0; i < numAdmins; i++) {
                    admins.add(new Address(admin.getAdmin(new BigInteger(i + "")).send()));
                }
                return admins;
            }
            else
            {
                throw new Exception("admin object is null!");
            }
        }
        else
        {
            throw new Exception("sender address is not allowed to perform this operation!");
        }


    }

    public List<Address> getAllContractAddresses(Address senderAddress) throws Exception {
        if(checkIfAdmin(senderAddress))
        {
            if (admin != null) {
                List<Address> contracts = new ArrayList<>();
                int numContracts = admin.getContractCount().send().intValue();
                for (int i = 0; i < numContracts; i++) {
                    contracts.add(new Address(admin.getContractAddress(new BigInteger(i + "")).send()));
                }
                return contracts;
            }
            else
            {
                throw new Exception("admin object is null!");
            }
        }
        else
        {
            throw new Exception("sender address is not allowed to perform this operation!");
        }
    }

    public void addAdminAddress(Address address, Address senderAddress) throws Exception {
        if(checkIfAdmin(senderAddress))
        {
            if (admin != null) {
                admin.addAdmin(address.toString()).send();
            }
            else
            {
                throw new Exception("admin object is null!");
            }

        }
        else
        {
            throw new Exception("sender address is not allowed to perform this operation!");
        }
    }

    public void addContractAddress(Address address, Address senderAddress) throws Exception {
        if(checkIfAdmin(senderAddress))
        {
            if (admin != null) {
                admin.addContractAddress(address.toString()).send();
            }
            else
            {
                throw new Exception("admin object is null!");
            }
        }
        else
        {
            throw new Exception("sender address is not allowed to perform this operation!");
        }
    }
}
