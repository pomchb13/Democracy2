package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class AdminReader {

    public static String getAdminContractAddress(String path) throws Exception {
        File f = new File(path + "contract.txt");
        if(f.exists())
        {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String address = br.readLine();
            br.close();
            return address;
        }
        else
        {
            throw new Exception("admin contract does not exist!");
        }
    }
}
