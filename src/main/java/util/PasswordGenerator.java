package util;


import java.security.SecureRandom;

/**
 * Created by Christoph Pommer on 18.02.2018
 * Class to generate a Secure Password
 */
public class PasswordGenerator {




    private static final SecureRandom secureRand = new SecureRandom();
    /**
     * Letters to generate the password
     */
    private static final String AVAILABLELETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!#$%^*()-_=+[{]}\\|;:,./?";


    /**
     *
     * Generates a password out of the Available Letters and the length with a securerandom object
     * @param length of password
     * @return the new password
     */
    public static String createPassword(int length)
    {
        String generatedPasswd="";
        for(int i=0;i<length;i++)
        {
            int randInt = secureRand.nextInt(AVAILABLELETTERS.length());
            generatedPasswd+= AVAILABLELETTERS.charAt(randInt);
        }
        return generatedPasswd;
    }


}
