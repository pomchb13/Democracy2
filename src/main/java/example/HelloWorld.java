package example;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * Created by chris on 12.07.2017.
 */
public class HelloWorld {

       Web3j web = Web3j.build(new HttpService());
        public static String getMessage() {
        return "Hello, world";
    }

    public static void main(String[] args) {
        System.out.println(getMessage());
    }



}
