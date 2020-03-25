package CS246_Group4.failsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class NewAccountCreation extends AppCompatActivity {

    private Button Save;

    Account account;
    private EditText usernameText;
    private EditText accountnameText;
    private EditText passwordText;
    private EditText URLText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_creation2);
        usernameText = findViewById(R.id.username);
        accountnameText = findViewById(R.id.accountname);
        passwordText = findViewById(R.id.password);
        URLText = findViewById(R.id.URL);

        Save = findViewById(R.id.Save);

        Save.setOnClickListener((view)->save());

    }
    private void save() {
        String username = usernameText.getText().toString();
        String accountname = accountnameText.getText().toString();
        String URL = URLText.getText().toString();
        String password = passwordText.getText().toString();
        account = new Account(username, accountname, URL, password);

        try {
            Gson gson = new Gson();
            String json = gson.toJson(account);

            // openFileOutput is the Android function to get a file for writing from the phone.  Wrapped the stream
            // into a BufferedWriter for ease of use.
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("ParamFile.txt", Context.MODE_PRIVATE)));
            writer.write(json);
            writer.close();

        }
        catch (IOException ioe) {
            Log.d("files",ioe.toString());
        }
    }
}
