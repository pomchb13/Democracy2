package user;

import beans.RightEnum;

import java.util.*;

public class LoggedUsers {

    private Map<String, RightEnum> tokenList = new HashMap<>();
    private static LoggedUsers theInstance;

    private LoggedUsers() {

    }

    public static LoggedUsers getInstance() {
        if (theInstance == null) {
            theInstance = new LoggedUsers();
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

    public void logout(String hash) {
        tokenList.remove(hash);
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
        System.out.println("----------LoggedUsers------------");
        for (Map.Entry<String, RightEnum> e  :tokenList.entrySet()) {
            System.out.println(e.getKey() +"|"+e.getValue());
        }
    }

    public Map<String, RightEnum> getTokenList() {
        return tokenList;
    }

    public void setTokenList(Map<String, RightEnum> tokenList) {
        this.tokenList = tokenList;
    }




}
