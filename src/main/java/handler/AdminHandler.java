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
        admin = AdminContract.deploy(web3, credentials, new BigInteger("300000"), new BigInteger("4700000")).send();
        return admin.getContractAddress();
    }

    public void loadSmartContract(Address address) {
        admin = AdminContract.load(address.toString(), web3, credentials, new BigInteger("300000"), new BigInteger("4700000"));
    }

    public boolean checkIfAdmin(Address address) throws Exception {
        if (admin != null) {
            return admin.checkIfAdmin(address.toString()).send();
        }
        throw new Exception("admin object is null!");
    }

    public List<Address> getAllAdmins() throws Exception {

        if (admin != null) {
            List<Address> admins = new ArrayList<>();
            int numAdmins = admin.getAdminCount().send().intValue();
            for (int i = 0; i < numAdmins; i++) {
                admins.add(new Address(admin.getAdmin(new BigInteger(i + "")).send()));
            }
            return admins;
        }
        throw new Exception("admin object is null!");
    }

    public List<Address> getAllContractAddresses() throws Exception {
        if (admin != null) {
            List<Address> contracts = new ArrayList<>();
            int numContracts = admin.getContractCount().send().intValue();
            for (int i = 0; i < numContracts; i++) {
                contracts.add(new Address(admin.getContractAddress(new BigInteger(i + "")).send()));
            }
            return contracts;
        }
        throw new Exception("admin object is null!");
    }

    public void addAdminAddress(Address address) throws Exception {
        if (admin != null) {
            admin.addAdmin(address.toString()).send();
        }
        throw new Exception("admin object is null!");
    }

    public void addContractAddress(Address address) throws Exception {
        if (admin != null) {
            admin.addContractAddress(address.toString()).send();
        }
        throw new Exception("admin object is null!");
    }


    public static void main(String[] args) {

    }
}
