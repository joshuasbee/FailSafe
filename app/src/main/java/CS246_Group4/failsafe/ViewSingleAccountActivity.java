package CS246_Group4.failsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ViewSingleAccountActivity extends AppCompatActivity {
    private Account accountToView;
    private EditText accountName;
    private EditText username;
    private EditText password;
    private EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_account);
        accountName = findViewById(R.id.editAccountName);
        username = findViewById(R.id.editUsername);
        password = findViewById(R.id.editPassword);
        url = findViewById(R.id.editURL);


        Account unparceledAccount = (Account) getIntent().getParcelableExtra(ShowAccountsActivity.PARCEL_DATA);
        accountName.setText(unparceledAccount.getAccountname());
        username.setText(unparceledAccount.getUsername());
        password.setText(unparceledAccount.getPassword());
        url.setText(unparceledAccount.getURL());
        String test = new String();
    }

    public void generatePwd(View view){
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String randomStrongPwd = passwordGenerator.generateNewPassword();
        password.setText(randomStrongPwd);
    }
}
