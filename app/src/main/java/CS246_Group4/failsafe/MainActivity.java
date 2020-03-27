package CS246_Group4.failsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        registerButton = findViewById(R.id.registerButton);
//        passwordRegistered = findViewById(R.id.passwordRegistered);
        loginText = findViewById(R.id.loginText);//Login
        loginButton = findViewById(R.id.loginButton);//Login

        //get value in shared preferences
        String tester = pwd.readString(this, 1);
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
        else {//first run, create a password and save it
            setContentView(R.layout.activity_login);
            loginButton.setOnClickListener((view)->login());
            //call different stuff
        }

    }


    //TODO Add LOGIN function,
    /* Will need to use hash function, to hash initial user password and save to
     *  a file with an inconspicuous name, such as "uno"
     *  Then we will need to hash the user input to allow us to compare to saved hashed password,
     *  and if it matches let them into the next activity, the accountlist.
     * */
    // ----------- REGISTER ACTIVITY CODE -----------
    //when registering, get and save password hash in shared preferences
    private void reg() throws NoSuchAlgorithmException, IOException {
        String hashed = hasher.hashPassword(passwordRegistered.getText().toString());//hashes user input
        String test = enc.encodeText(loginTest, hashed);//Encrypt the keystring to verify login with
        pwd.writeString(test, this, 0);//Save the encrypted tester string as 0.txt
        Intent list_view = new Intent(this, ListOfAccounts.class);
        startActivity(list_view);
    }

    private void login() {
        String input = loginText.getText().toString();
        //loop
        while (!enc.decodeText(pwd.readString(this, 0), input).equals(loginTest)) {
            if (enc.decodeText(pwd.readString(this, 0), input).equals(loginTest)) {//attempt to decrypt tester string with user input and compare to actual
                //go to account list activity
                Intent list_view = new Intent(this, ListOfAccounts.class);
                startActivity(list_view);
            } else {
                //toast wrong password
                Toast.makeText (getApplicationContext(),
                        "Wrong password!", Toast.LENGTH_LONG).show();
            }
        }
    }


}
