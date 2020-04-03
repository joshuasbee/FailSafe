package CS246_Group4.failsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ViewSingleAccountActivity extends AppCompatActivity {
    private Account accountToView;
    private EditText accountName;
    private EditText username;
    private EditText password;
    private EditText url;
    private String hashedPass;
    private EncoderHelper enc = new EncoderHelper();


    //copies of original data
    private String copyOfAccountName;
    private String copyOfUsername;
    private String copyOfPassword;
    private String copyOfURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_account);
        accountName = findViewById(R.id.editAccountName);
        username = findViewById(R.id.editUsername);
        password = findViewById(R.id.editPassword);
        url = findViewById(R.id.editURL);
        hashedPass = getIntent().getStringExtra(ShowAccountsActivity.USERS_HASHED_PASS);

        Account unparceledAccount = (Account) getIntent().getParcelableExtra(ShowAccountsActivity.PARCEL_DATA);
        accountName.setText(unparceledAccount.getAccountname());
        username.setText(unparceledAccount.getUsername());
        password.setText(unparceledAccount.getPassword());
        url.setText(unparceledAccount.getURL());

        //initialize the copies for future use
        copyOfAccountName = accountName.getText().toString();
        copyOfUsername = username.getText().toString();
        copyOfPassword = password.getText().toString();
        copyOfURL = url.getText().toString();
    }

    public void generatePwd(View view){
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String randomStrongPwd = passwordGenerator.generateNewPassword();
        password.setText(randomStrongPwd);
    }

    public void editAccount(View view) {
        //get new account data and check for changes
        Log.e("look at me!", copyOfAccountName);
        String acctName = accountName.getText().toString();
        String uName = username.getText().toString();
        String pass = password.getText().toString();
        String website = url.getText().toString();

        //if no changes then return to previous screen
        if (acctName == copyOfAccountName && uName == copyOfUsername && pass == copyOfPassword && website == copyOfURL) {
            final Intent goBack = new Intent(this, ShowAccountsActivity.class);
            startActivity(goBack);
        }

        //read in current file contents to an array list for easy manipulation
        List<String> listAccounts = new ArrayList<>();
        String fileContents;
        String decrypted;
        int counter = 0;
        try {
            // openFileInput is the Android function to get a file for reading from the phone.  Wrapped the stream
            // into a BufferedRead for ease of use.
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("Accounts.txt")));


            while ((fileContents = reader.readLine()) != null) {
                if (!fileContents.equals("")) {
                    decrypted = enc.decodeText(fileContents, hashedPass);
                    listAccounts.add(decrypted);
                    counter++;
                }
            }
            reader.close();
        } catch (IOException ioe) {
            Log.e("files", ioe.toString());
        }

        //for loop to find correct account and save changes made by user
        for (int i = 0; i < counter; i++){
            if (listAccounts.get(i).contains(copyOfAccountName) && listAccounts.get(i).contains(copyOfPassword) && listAccounts.get(i).contains(copyOfURL) && listAccounts.get(i).contains(copyOfUsername)) {
                String jsonTemplate = "{\"URL\":\"" + website + "\",\"accountname\":\"" + acctName + "\",\"fileLine\":0,\"password\":\"" + pass + "\",\"username\":\"" + uName + "\"}";
                listAccounts.set(i, jsonTemplate);
            }
        }

        //write the contents of the array list back to the file with the new changes
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("Accounts.txt", Context.MODE_PRIVATE)));
            for (int i = 0; i < counter; i++) {
                writer.write(enc.encodeText(listAccounts.get(i), hashedPass));
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException ioe) {
            Log.d("files",ioe.toString());
        }
        final Intent goBack = new Intent(this, ShowAccountsActivity.class);
        startActivity(goBack);
    }
}
