package util;

import org.web3j.abi.datatypes.Address;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Author:          Patrick Windegger
 * Created on:
 * Description:     Class responsible for getting the address of the admin contract
 */
public class AdminReader {

    /**
     * Method responsible for reading the contract.txt file where the address to the admin contract is saved.
     *
     * @param path: path to the contract.txt file
     * @return address of the contract
     * @throws Exception if the file does not exist
     */
    public static Address getAdminContractAddress(String path) throws Exception {
        File f = new File(path + "contract.txt");
        if (f.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String address = br.readLine();
            br.close();
            return new Address(address);
        } else {
            throw new Exception("admin contract does not exist!");
        }
    }
}
