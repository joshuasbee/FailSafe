package CS246_Group4.failsafe;

import com.tozny.crypto.android.AesCbcWithIntegrity;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public class EncoderHelper {
//    private static final String PASSWORD = "Password";
    private static final byte[] SALT = "klgasjkhads".getBytes();//TODO generate a salt and save it

    /*
     *   TO USE:
     *   In activity of use, paste the following:
     *     private EncoderHelper enc = new EncoderHelper();
     *   and call the encodeText() on each password, and the parameter
     *   called "password" will be the User Inputted password
     */

//    public EncoderHelper(){}

    private AesCbcWithIntegrity.SecretKeys getKey(String password, byte[] salt) throws GeneralSecurityException {
        String saltString = AesCbcWithIntegrity.saltString(salt);
        AesCbcWithIntegrity.SecretKeys key = AesCbcWithIntegrity.generateKeyFromPassword(password, saltString);
        return key;
    }

    protected String encodeText(String input, String password) {
        try {
            AesCbcWithIntegrity.SecretKeys key = getKey(password, SALT);//generate key from password, salt will be saved somewhere
            AesCbcWithIntegrity.CipherTextIvMac cipher = AesCbcWithIntegrity.encrypt(input, key);//encrypt using created key
            return cipher.toString();//returns encrypted text
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return "";
    }

    protected String decodeText(String cipherText, String password) {
        try {
            AesCbcWithIntegrity.SecretKeys key = getKey(password, SALT);
            AesCbcWithIntegrity.CipherTextIvMac cipher = new AesCbcWithIntegrity.CipherTextIvMac(cipherText);
            String decodedText = AesCbcWithIntegrity.decryptString(cipher, key);
            return decodedText;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return "";
    }
}
