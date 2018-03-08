package user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashGenerator {

    // implement the HashGenerator as a singleton class
    private static HashGenerator theInstance;

    private HashGenerator() {
    }

    public static HashGenerator getTheInstance() {
        if (theInstance == null) {
            theInstance = new HashGenerator();
        }
        return theInstance;
    }


    /**
     *
     * @param passwordAndUsername given password and username fom the user
     * @return an hash of username, password and salt
     */
    public static String get_SHA_256_SecurePassword(String passwordAndUsername) {
        String generatedPassword = null;
        try {
            // set hash algorithms
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // get salt
            byte[] salt = getSalt();

            // set salt to the algorithms
            md.update(salt);

            // encrypt username, password and salt
            byte[] bytes = md.digest(passwordAndUsername.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     *
     * @return salt --> random string
     * @throws NoSuchAlgorithmException if an error occurs
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
}
