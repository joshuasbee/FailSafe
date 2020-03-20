package CS246_Group4.failsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Hash hasher = new Hash();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hasher.getHash(password);
        //TODO Add LOGIN function,
        /* Will need to use hash function, to hash initial user password and save to
        *  a file with an inconspicuous name, maybe "hashtag"
        *  Then we will need to hash the user input to allow us to compare to saved hashed password,
        *  and if it matches let them into the next activity, the accountlist.
        * */
    }
}
