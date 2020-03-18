package CS246_Group4.failsafe;

import android.util.Log;

public class PasswordGenerator {
    private final String ERR_MSG = "test";

    public String generateNewPassword(){
        String alphaSymNumString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvwxyz"
                                    + "!@#$%^&*()-=";

        int passLength = (int)(Math.random() * ((30 - 20) + 1) + 20);

        StringBuilder sb = new StringBuilder(passLength);

        for (int i = 0; i < passLength; i++){
            int index = (int)(alphaSymNumString.length() * Math.random());

            sb.append(alphaSymNumString.charAt(index));
        }

        Log.e(ERR_MSG, sb.toString());

        return sb.toString();
    }


}
