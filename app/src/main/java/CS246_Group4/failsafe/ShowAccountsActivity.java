package CS246_Group4.failsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ShowAccountsActivity extends AppCompatActivity {
    private PasswordSave pwd = new PasswordSave();
    private EncoderHelper enc = new EncoderHelper();
    private AccountList theMainAccountList;
    private String usersHashedPass;
    private ListView listOfAccounts;
    public static final String USERS_HASHED_PASS = "hash";
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_accounts);
        listOfAccounts = findViewById(R.id.listOfAccounts);
        Intent intent = getIntent();
        usersHashedPass = intent.getStringExtra(MainActivity.USERS_HASHED_PASS);
        loadFile();
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener((view)->addNewAccount(view));
    }

    public void loadFile(){
        List<Account> listAccounts = new ArrayList<>();
        Gson gson = new Gson();
        String fileContents;
        String decrypted;
        Account tempAccount;
        try {
            // openFileInput is the Android function to get a file for reading from the phone.  Wrapped the stream
            // into a BufferedRead for ease of use.
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("Accounts.txt")));
//            String anotherTest = reader.readLine();

            while ((fileContents = reader.readLine()) != null) {
                if (!fileContents.equals("")) {
                    decrypted = enc.decodeText(fileContents, usersHashedPass);
                    tempAccount = gson.fromJson(decrypted, Account.class);
                    listAccounts.add(tempAccount);
                }
            }

            reader.close();
            final ArrayAdapter<Account> accountAdapter = new ArrayAdapter<Account>(this, android.R.layout.simple_list_item_1, listAccounts);
            listOfAccounts.setAdapter(accountAdapter);
        }
        catch (IOException ioe) {
            Log.e("files", ioe.toString());
        }
    }
    //function to add new account to file
    public void addNewAccount(View view){
        final Intent addAccount = new Intent(this, NewAccountCreation.class);
        addAccount.putExtra(USERS_HASHED_PASS, usersHashedPass);
        startActivity(addAccount);
    }
}
