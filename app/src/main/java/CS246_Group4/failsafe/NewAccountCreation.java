package CS246_Group4.failsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class NewAccountCreation extends AppCompatActivity {

    private Button Save;

    Account account;
    private EditText usernameText;
    private EditText accountnameText;
    private EditText passwordText;
    private EditText URLText;
    private EncoderHelper enc;
    private String userHashPass;
    private boolean needToCreateFile = true;
    private PasswordSave pwd = new PasswordSave();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_creation2);
        usernameText = findViewById(R.id.username);
        accountnameText = findViewById(R.id.accountname);
        passwordText = findViewById(R.id.password);
        URLText = findViewById(R.id.URL);
        Intent intent = getIntent();
        userHashPass = intent.getStringExtra(ShowAccountsActivity.USERS_HASHED_PASS);
        Log.e("check intent",userHashPass);

        Save = findViewById(R.id.Save);

        Save.setOnClickListener((view)->save());
    }
    private void save() {

        String username = usernameText.getText().toString();
        String accountname = accountnameText.getText().toString();
        String URL = URLText.getText().toString();
        String password = passwordText.getText().toString();
        account = new Account(username, accountname, URL, password);

        Context context = getApplicationContext();
        CharSequence text = username+" "+accountname+" "+URL+" "+password;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();


      try {
            Gson gson = new Gson();
            String json = gson.toJson(account);
            enc = new EncoderHelper();
            json = enc.encodeText(userHashPass, json);
            Log.e("match", json);

            pwd.writeString(json,  1);



            // openFileOutput is the Android function to get a file for writing from the phone.  Wrapped the stream
            // into a BufferedWriter for ease of use.
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("Accounts.txt", Context.MODE_PRIVATE)));
//            BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"));

//            writer.append(json);
//            writer.newLine();
//            writer.close();
//            Log.e("debug", reader.readLine());

        }
        catch (IOException ioe) {
            Log.e("files",ioe.toString());
        }

        //TODO: add functionality to automatically switch to previous activity after saving
    }
}
