package CS246_Group4.failsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import java.io.FileReader;
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
    private ImageButton addNewAccountButton;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_accounts);
        Intent intent = getIntent();
        usersHashedPass = intent.getStringExtra(MainActivity.USERS_HASHED_PASS);
        listOfAccounts = findViewById(R.id.listOfAccounts);
        Log.e("check", "about to loadfile");
        loadFile();
//        addNewAccountButton = findViewById(R.id.addNewAccountButton);
//        addNewAccountButton.setOnClickListener((view)->addNewAccount(view));
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener((view)->addNewAccount(view));

    }

    public void loadFile(){
        if (true){
            Log.e("getting into loadfile", "we are in loadfile function");
        }
//        try {
            Log.e("check", "checking that we are entering try statement");
            // openFileInput is the Android function to get a file for reading from the phone.  Wrapped the stream
            // into a BufferedRead for ease of use.
//            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("Accounts.txt")));

            String file = pwd.readString(1);
            Gson gson = new Gson();
            PasswordSave pwd = new PasswordSave();
            ArrayList<Account> acctsList = new ArrayList<>();
            String decrypted;
            // TODO: add scanner to go line by line and add to array list so that the while loop works
            while (){
                Log.e("check", file);
                decrypted = enc.decodeText(file, usersHashedPass);

                acctsList.add(gson.fromJson(decrypted, Account.class));
                Log.e("check", "checking for infinite loop");
            }
            if (acctsList.isEmpty()){
                Log.e("debugging", "list is empty");
            }
//            else{
//                Log.e("debugging", acctsList.toString());
//            }
//            reader.close();
//            AccountList theMainList = new AccountList();
//            theMainList.setAccountList(acctsList);

            final ArrayAdapter<Account> accountAdapter = new ArrayAdapter<Account>(this, android.R.layout.simple_list_item_1, acctsList);
            listOfAccounts.setAdapter(accountAdapter);
//        }
//        catch (IOException ioe) {
//            Log.d("files", ioe.toString());
//        }
    }
    //function to add new account to file
    public void addNewAccount(View view){
        final Intent addAccount = new Intent(this, NewAccountCreation.class);
        addAccount.putExtra(USERS_HASHED_PASS, usersHashedPass);
        startActivity(addAccount);
    }
}
