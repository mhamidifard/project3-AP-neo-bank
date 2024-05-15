package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {
    private static  List<Account> accounts=new ArrayList<>();
    private static Map<Integer,Transaction> transactions=new HashMap<>();

    public static Account findByAccNum(long accNum){
        for (Account user :accounts) {
            if(user.getAccountNumber()==accNum){
                return user;
            }
        }
        return null;
    }

    public static Account findByPhone(long phone){
        for (Account user :accounts) {
            if(user.getPhoneNumber()==phone){
                return user;
            }
        }
        return null;
    }


    public static List<Account> getAccounts() {
        return accounts;
    }

    public static void setAccounts(List<Account> accounts) {
        DataBase.accounts = accounts;
    }

    public static Map<Integer, Transaction> getTransactions() {
        return transactions;
    }

    public static void setTransactions(Map<Integer, Transaction> transactions) {
        DataBase.transactions = transactions;
    }
}
