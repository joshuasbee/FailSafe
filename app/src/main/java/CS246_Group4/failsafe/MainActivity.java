package CS246_Group4.failsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    private PasswordSave pwd = new PasswordSave();//object for calling hashing and file save stuff
    private Hash hasher = new Hash();
    private EditText passwordRegistered;
    private Button registerButton;
    private EditText loginText;
    private Button loginButton;
    private String loginTest = "Success!";
    private EncoderHelper enc = new EncoderHelper();
    public static final String USERS_HASHED_PASS = "key to find hash of user password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get value in files
        String tester = pwd.readString(this, 0);
        if (tester == null || tester.length() == 0) { //check files for value present
            setContentView(R.layout.activity_register);
            registerButton = findViewById(R.id.registerButton);
            passwordRegistered = findViewById(R.id.passwordRegistered);
            registerButton.setOnClickListener((view)-> {
                try {
                    reg();
                } catch (NoSuchAlgorithmException | IOException e) {
                    e.printStackTrace();
                }
            });
        }
        else {//Login activity
            setContentView(R.layout.activity_login);
            loginButton = findViewById(R.id.loginButton);
            loginText = findViewById(R.id.loginText);//Login
            loginButton.setOnClickListener((view)-> {
                try {
                    login();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            });
            //call different stuff
        }

    }

    // ----------- REGISTER ACTIVITY CODE -----------
    //when registering, get and save password hash in shared preferences
    private void reg() throws NoSuchAlgorithmException, IOException {
        String hashed = hasher.hashPassword(passwordRegistered.getText().toString());//hashes user input
        String test = enc.encodeText(loginTest, hashed);//Encrypt the keystring to verify login with
        pwd.writeString(test, this, 0);//Save the encrypted tester string as 0.txt
        final Intent list_view = new Intent(this, ShowAccountsActivity.class);
        list_view.putExtra(USERS_HASHED_PASS, hashed);
        list_view.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(list_view);
        finish();
    }

    private void login() throws NoSuchAlgorithmException {
        String input = loginText.getText().toString();
        String hashInput = hasher.hashPassword(input);
        //loop

            Log.e("Decode: ", enc.decodeText(pwd.readString(this, 0), input));
            if (enc.decodeText(pwd.readString(this, 0), hashInput).equals(loginTest)) {//attempt to decrypt tester string with user input and compare to actual
                //go to account list activity
                final Intent list_view = new Intent(this, ShowAccountsActivity.class);
                list_view.putExtra(USERS_HASHED_PASS, hashInput);
                list_view.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(list_view);
                finish();
            } else {
                //toast wrong password
                Toast.makeText (getApplicationContext(),
                        "Wrong password!", Toast.LENGTH_LONG).show();
                //TODO add login timer thing "android java countdown timer"
            }
    }
}
