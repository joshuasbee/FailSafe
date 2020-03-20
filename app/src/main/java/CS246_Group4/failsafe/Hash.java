package CS246_Group4.failsafe;

//import com.google.common.base.Charsets;
//import com.google.common.hash.HashCode;
//import com.google.common.hash.Hasher;
//import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    /*
     *   TO USE:
     *   In activity of use, paste the following:
     *       private Hash hasher = new Hash();
     *   and call hashPassword on each password to be hashed, and the parameter
     *   called "password" will be the User's password
     */

    // Translates the bytes to Hexadecimal
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Hashes the password to hash form for save storage on web server
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest; {
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw e;
            }
        }
        byte[] encodedhash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        password = bytesToHex(encodedhash);
        return password;
    }


}
