package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private static  List<Account> accounts=new ArrayList<>();

    public static List<Account> getAccounts() {
        return accounts;
    }

    public static void setAccounts(List<Account> accounts) {
        DataBase.accounts = accounts;
    }

}
