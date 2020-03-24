package CS246_Group4.failsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Hash hasher = new Hash();//object for calling hashing stuff
    private EditText passwordRegistered;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerButton = findViewById(R.id.registerButton);
        passwordRegistered = findViewById(R.id.passwordRegistered);

        //get value in shared preferences
        if (0 == 0) { //check shared preferences value present
            setContentView(R.layout.activity_login);
            //call stuff
        }
        else {//first run, create a password and save it
            setContentView(R.layout.activity_register);
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
    public void reg() {

        //hasher.getHash(password);
        SharedPreferences sharedPref = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPref.edit();
        myEdit.putString("hashword", hashed.getText.toString());
        myEdit.commit();
    }



}
