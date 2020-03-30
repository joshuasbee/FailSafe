package CS246_Group4.failsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ShowAccountsActivity extends AppCompatActivity {
    private PasswordSave pwd = new PasswordSave();
    private EncoderHelper enc = new EncoderHelper();
    private AccountList theMainAccountList;
    private String usersHashedPass;
    private ListView listOfAccounts;
    private final String USERS_HASHED_PASS = "key to find pass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_accounts);
        Intent intent = getIntent();
        usersHashedPass = intent.getStringExtra(MainActivity.USERS_HASHED_PASS);
        loadFile();
    }

    public void loadFile(){
        try {
            // openFileInput is the Android function to get a file for reading from the phone.  Wrapped the stream
            // into a BufferedRead for ease of use.
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("ParamFile.txt")));
            String json = reader.readLine();
            reader.close();

            json = enc.decodeText(json, usersHashedPass);
            Gson gson = new Gson();
            AccountList accounts = gson.fromJson(json, AccountList.class);
            List<Account> listAccounts = accounts.getAccountList();
            final ArrayAdapter<Account> accountAdapter = new ArrayAdapter<Account>(this, android.R.layout.simple_list_item_1, listAccounts);
            listOfAccounts.setAdapter(accountAdapter);
        }
        catch (IOException ioe) {
            Log.d("files", ioe.toString());
        }
    }
    //function to add new account to file
    public void addNewAccount(View view){
        final Intent addAccount = new Intent(this, NewAccountCreation.class);
        addAccount.putExtra(USERS_HASHED_PASS, usersHashedPass);
        startActivity(addAccount);

    }
}
