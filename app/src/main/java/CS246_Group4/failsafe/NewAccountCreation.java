package CS246_Group4.failsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewAccountCreation extends AppCompatActivity {

    private Button Save;

    Account account;
    private EditText usernameText;
    private EditText accountnameText;
    private EditText passwordText;
    private EditText URLText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_creation2);
        usernameText = findViewById(R.id.username);
        accountnameText = findViewById(R.id.accountname);
        passwordText = findViewById(R.id.password);
        URLText = findViewById(R.id.URL);

        Save = findViewById(R.id.Save);

        Save.setOnClickListener((view)->save());

    }
    private void save() {
        String username = usernameText.getText().toString();
        String accountname = accountnameText.getText().toString();
        String URL = URLText.getText().toString();
        String password = passwordText.getText().toString();
        account = new Account(username, accountname, URL, password);
    }
}
