package CS246_Group4.failsafe;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;

public class PasswordSave {
    private Hash hasher = new Hash();

    public void writeHashed (String data, Context context) {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput("uno.txt", context.MODE_PRIVATE));
            String hash = hasher.hashPassword(data);
            writer.write(hash);
            writer.close();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        };
    }

    public String readHashed (Context context) {
        String ret = "";
        try {
            InputStream istream = context.openFileInput("uno.txt");
            if (istream != null) {
                InputStreamReader iread = new InputStreamReader(istream);
                BufferedReader bufferedReader = new BufferedReader(iread);
                String receiveString = "";
                StringBuilder builder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null) {
                    builder.append("").append(receiveString);
                }
                istream.close();
                ret = builder.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
