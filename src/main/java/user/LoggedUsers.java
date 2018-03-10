package user;

import beans.RightEnum;

import java.util.*;

/**
 * Author:          Ewald Hartmann
 * Created on:
 * Description:     class contains all user which are logged in.
 */

public class LoggedUsers {

    private Map<String, RightEnum> tokenList = new HashMap<>();
    private Map<String, String> addressList = new HashMap<>();

    // implement the class as singleton
    private static LoggedUsers theInstance;

    private LoggedUsers() {

    }

    public static LoggedUsers getInstance() {
        if (theInstance == null) {
            theInstance = new LoggedUsers();
        }
        return theInstance;
    }

    /**
     *
     * @param hash hash of username, password and salt
     * @param right right of user
     * @param address username of user
     * @throws Exception
     */
    public void login(String hash, RightEnum right, String address) throws Exception {
        // check if hash or address is includes in the list
        // if they are not included they will
        // otherwise and Exception will be thrown
        if (!tokenList.containsKey(hash) && !addressList.containsKey(hash) && !addressList.containsValue(address)) {
            tokenList.put(hash, right);
            addressList.put(hash, address);
        } else {
            throw new Exception("Already logged in");
        }
    }

    /**
     *
     * @param hash hash of username, password and salt
     */
    public void logout(String hash) {
        // remove the hash in all lists
        tokenList.remove(hash);
        addressList.remove(hash);
    }

    /**
     *
     * @param hash hash of username, password and salt
     * @param neededRight the needed right for this side
     * @return true if the right is okay , false if the right is to low
     */
    public boolean compareRights(String hash, RightEnum neededRight) {
        if (tokenList.containsKey(hash)) {
            if (tokenList.get(hash).equals(neededRight)) {
                return true;
            }
        }
        return false;
    }

    /**
     * prints all user out
     */
    public void outPutUserList() {
        System.out.println("----------LoggedUsers------------");
        for (Map.Entry<String, RightEnum> e : tokenList.entrySet()) {
            System.out.println(e.getKey() + "|" + e.getValue());
        }
    }

    /**
     *
     * @param hash hash of username, password and salt
     * @return the address(username) of this hash
     */
    public String getAddressOfHash(String hash) {
        for (Map.Entry<String, String> e : addressList.entrySet()) {
            if (e.getKey().equals(hash)) {
                return e.getValue();
            }
        }
        return "";
    }

    public Map<String, RightEnum> getTokenList() {
        return tokenList;
    }

    public void setTokenList(Map<String, RightEnum> tokenList) {
        this.tokenList = tokenList;
    }


}
