package CS246_Group4.failsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.io.IOException;
import java.io.InputStream;
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
    private Button pwdGenerator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_creation2);
        usernameText = findViewById(R.id.username);
        accountnameText = findViewById(R.id.accountname);
        passwordText = findViewById(R.id.password);
        URLText = findViewById(R.id.URL);
        pwdGenerator = findViewById(R.id.GeneratePassword);
        Intent intent = getIntent();
        userHashPass = intent.getStringExtra(ShowAccountsActivity.USERS_HASHED_PASS);



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
            json = enc.encodeText(json, userHashPass);

            // openFileOutput is the Android function to get a file for writing from the phone.  Wrapped the stream
            // into a BufferedWriter for ease of use.

            InputStream istream = this.openFileInput("Accounts.txt");
            String fileContents;

            if (istream != null) {
                InputStreamReader iread = new InputStreamReader(istream);
                BufferedReader bufferedReader = new BufferedReader(iread);
                String receiveString = "";
                StringBuilder builder = new StringBuilder();
                int fileLineCounter = 0;

                while ((receiveString = bufferedReader.readLine()) != null) {
                    builder.append(System.lineSeparator()).append(receiveString);

                }
                istream.close();
                fileContents = builder.toString();
            }
            else{
                fileContents = json;
            }

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("Accounts.txt", Context.MODE_PRIVATE)));
            writer.write(fileContents);
            writer.newLine();
            if (fileContents != json) {
                writer.write(json);
            }
            writer.close();

        }
        catch (IOException ioe) {
            Log.d("files",ioe.toString());
        }
        final Intent returnToAccountsPage = new Intent(this, ShowAccountsActivity.class);
        returnToAccountsPage.putExtra(MainActivity.USERS_HASHED_PASS, userHashPass);
        startActivity(returnToAccountsPage);
    }

    public void generatePwd(View view){
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String randomStrongPwd = passwordGenerator.generateNewPassword();
        passwordText.setText(randomStrongPwd);
    }
}
