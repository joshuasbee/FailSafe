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

    public void writeString (String data, Context context, int id) throws IOException {
        String out = "";
        try {
            OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput(id + ".txt", context.MODE_PRIVATE));//mode.txt will be 0.txt for password, other numbers for other stuff
            out = data;
            writer.write(out);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        };
    }

    public String readString (Context context, int id) {
        String ret = "";
        try {
            InputStream istream = context.openFileInput(id + ".txt");
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
