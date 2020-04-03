package CS246_Group4.failsafe;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {

    protected String username;
    private String accountname;
    private String URL;
    private String password;
    private int fileLine;

    public Account(Parcel in) {
        username = in.readString();
        accountname = in.readString();
        URL = in.readString();
        password = in.readString();
    }

    public Account(String username, String accountname, String URL, String password) {
        this.username = username;
        this.accountname = accountname;
        this.URL = URL;
        this.password = password;
    }

    public int getFileLine() {
        return fileLine;
    }

    public void setFileLine(int fileLine) {
        this.fileLine = fileLine;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public String toString() {
        return accountname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(accountname);
        dest.writeString(URL);
        dest.writeString(password);
    }
}
