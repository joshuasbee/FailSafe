package CS246_Group4.failsafe;

import com.google.common.collect.Table;

import java.util.ArrayList;

public class AccountList {
    private ArrayList<Account> accountList; // Create an ArrayList object

    public AccountList() {
        accountList = new ArrayList<Account>();
    }


    public void setAccountList(ArrayList<Account> accountList) {
        this.accountList = accountList;
    }

    public ArrayList<Account> getAccountList() {
        return accountList;
    }

    public void populateTable() {
        for (Account element : accountList) {
            System.out.println(element);
            //Table.add(element);                          //Temporary placeholder until real exists
        }
    }
}
