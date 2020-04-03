package CS246_Group4.failsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    public  static final String PARCEL_DATA = "parcelable data";
    private Button buttonAdd;
    private Context context = this;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_accounts);
        listOfAccounts = findViewById(R.id.listOfAccounts);
        Intent intent = getIntent();
        usersHashedPass = intent.getStringExtra(MainActivity.USERS_HASHED_PASS);
        if (usersHashedPass == null){
            SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
            usersHashedPass = sharedPreferences.getString(getString(R.string.pref_file_key), "no password");
        }
        loadFile();
        //the following is a listener for the items within the listview
        listOfAccounts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  Account accountToBeAccessed = (Account) listOfAccounts.getItemAtPosition(position);
                  viewAccountInfo(view, accountToBeAccessed);
            }
        });
        listOfAccounts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Account accountToBeAccessed = (Account) listOfAccounts.getItemAtPosition(position);
                Uri webpage = Uri.parse("http://" + accountToBeAccessed.getURL());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
                return true;
            }
        });
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener((view)->addNewAccount(view));
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.pref_file_key), usersHashedPass);
        editor.commit();
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

    public void viewAccountInfo(View view, Account accessedAccount){
        final Intent viewAccount = new Intent(this, ViewSingleAccountActivity.class);
        viewAccount.putExtra(PARCEL_DATA, accessedAccount);
        viewAccount.putExtra(USERS_HASHED_PASS, usersHashedPass);
        startActivity(viewAccount);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.pref_file_key), "0");
    }
}
