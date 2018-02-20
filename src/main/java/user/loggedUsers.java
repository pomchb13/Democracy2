package user;

import java.util.LinkedList;
import java.util.List;

public class loggedUsers {

    private List<String> tokenList = new LinkedList<>();
    private static loggedUsers theInstance;

    private loggedUsers() {

    }

    public static loggedUsers getInstance() {
        if (theInstance == null) {
            theInstance = new loggedUsers();
        }
        return theInstance;
    }

    public void login(String hash) throws Exception {
        if (!tokenList.contains(hash)) {
            tokenList.add(hash);
        } else {
            throw new Exception("Already logged in");
        }
    }

    public void logout(String hash)
    {
        tokenList.remove(hash);
    }


}
