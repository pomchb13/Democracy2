package user;

import beans.RightEnum;

import java.util.*;

public class loggedUsers {

    private Map<String, RightEnum> tokenList = new HashMap<>();
    private static loggedUsers theInstance;

    private loggedUsers() {

    }

    public static loggedUsers getInstance() {
        if (theInstance == null) {
            theInstance = new loggedUsers();
        }
        return theInstance;
    }

    public void login(String hash, RightEnum right) throws Exception {
        if (!tokenList.containsKey(hash)) {
            tokenList.put(hash, right);
        } else {
            throw new Exception("Already logged in");
        }
    }

    public boolean compareRights(String hash, RightEnum neededRight) {
        if (tokenList.containsKey(hash)) {
            if (tokenList.get(hash).equals(neededRight)) {
                return true;
            }
        }
        return false;

    }

    public void outPutUserList()
    {
        System.out.println("----------loggedUsers------------");
        for (Map.Entry<String, RightEnum> e  :tokenList.entrySet()) {
            System.out.println(e.getKey() +"|"+e.getValue());
        }
    }

    public Map<String, rightEnum> getTokenList() {
        return tokenList;
    }

    public void setTokenList(Map<String, rightEnum> tokenList) {
        this.tokenList = tokenList;
    }

    public void logout(String hash) {
        tokenList.remove(hash);
    }


}
