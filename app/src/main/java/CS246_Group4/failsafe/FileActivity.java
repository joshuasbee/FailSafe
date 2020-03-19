package CS246_Group4.failsafe;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileActivity extends AppCompatActivity {

    private EditText username;
    private EditText accountname;
    private EditText URL;
    private EditText password;
    private TextView fileContents;

    public void saveFile(View view) {
        try {
            Account account = new Account(username.getText().toString(),
                                          accountname.getText().toString(),
                                          URL.getText().toString(), password.getText().toString());

            Gson gson = new Gson();
            String json = gson.toJson(account);

            // openFileOutput is the Android function to get a file for writing from the phone.  Wrapped the stream
            // into a BufferedWriter for ease of use.
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("ParamFile.txt", Context.MODE_PRIVATE)));
            writer.write(json);
            writer.close();
            fileContents.setText("Wrote data to AccountFile.txt");
        }
        catch (IOException ioe) {
            fileContents.setText("Unable to save to AccountFile.txt");
            Log.d("files",ioe.toString());
        }
    }

    public void loadFile(View view){
        try {
            // openFileInput is the Android function to get a file for reading from the phone.  Wrapped the stream
            // into a BufferedRead for ease of use.
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("ParamFile.txt")));
            String json = reader.readLine();
            reader.close();

            Gson gson = new Gson();
            Account account = gson.fromJson(json, Account.class);
            fileContents.setText(account.toString());
        }
        catch (IOException ioe) {
            fileContents.setText("Unable to load from AccountFile.txt");
            Log.d("files", ioe.toString());
        }
    }
}
