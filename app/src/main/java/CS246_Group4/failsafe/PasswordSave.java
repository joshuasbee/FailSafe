package CS246_Group4.failsafe;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;


public class PasswordSave {
    private Hash hasher = new Hash();

    public void writeString (String data, int id) throws IOException {
        String out = "";
        try {
            OutputStreamWriter writer = new OutputStreamWriter(MainActivity.getContext().openFileOutput(id + ".txt", MainActivity.getContext().MODE_PRIVATE));//mode.txt will be 0.txt for password, other numbers for other stuff
            BufferedWriter bw = new BufferedWriter(writer);
            out = data;

            bw.write(out);
            bw.close();
            Log.e("Data written", out);
        } catch (IOException e) {
            Log.e("Failed", e.getMessage());
        };
    }

    public String readString (int id) {
        String ret = "";
        try {
            InputStream istream = MainActivity.getContext().openFileInput(id + ".txt");
            if (istream != null) {
                InputStreamReader iread = new InputStreamReader(istream);
                BufferedReader bufferedReader = new BufferedReader(iread);
                String receiveString = "";
                StringBuilder builder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    builder.append("\n").append(receiveString);
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
