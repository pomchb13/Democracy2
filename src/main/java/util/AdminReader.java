package util;

import org.web3j.abi.datatypes.Address;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class AdminReader {

    public static Address getAdminContractAddress(String path) throws Exception {
        File f = new File(path + "contract.txt");
        if(f.exists())
        {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String address = br.readLine();
            br.close();
            return new Address(address);
        }
        else
        {
            throw new Exception("admin contract does not exist!");
        }
    }
}
