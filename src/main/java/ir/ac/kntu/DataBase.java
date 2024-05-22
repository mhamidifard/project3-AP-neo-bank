package ir.ac.kntu;

import java.util.*;

public class DataBase {
    private static  List<Account> accounts=new ArrayList<>();
    private static  List<Support> supports=new ArrayList<>();
    private static Map<Integer,Transaction> transactions=new HashMap<>();
    private static List<VerificationRequest> verifyRequests=new LinkedList<>();


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

    public static Account findById(String natinalId){
        for (Account user :accounts) {
            if(user.getNationalId().equals(natinalId)){
                return user;
            }
        }
        return null;
    }

    public static void addUser(String firstName, String lastName, long phoneNumber, String id, String password){
        accounts.add(new  Account(firstName, lastName, phoneNumber, id, password));
        accounts.get(accounts.size()-1).setDataBaseNum(accounts.size()-1);
    }

    public static void addVerifyReq(long phoneNumber){
        verifyRequests.add(new VerificationRequest(phoneNumber));
    }
    public static void removeVerifyReq(VerificationRequest vr){
        verifyRequests.remove(vr);
    }

    public static void removeVerifyReq(long phoneNummber){
        for (int i = 0; i < verifyRequests.size(); i++) {
            if(verifyRequests.get(i).getPhoneNumber()==phoneNummber){
                verifyRequests.remove(i);
                i--;
            }
        }
    }

    public static void renewVerifyReq(long lastPhone,long newPhone){
        for (int i = 0; i < verifyRequests.size(); i++) {
            if(verifyRequests.get(i).getPhoneNumber()==lastPhone){
                verifyRequests.set(i,new VerificationRequest(newPhone));
                return;
            }
        }
    }

    public static VerificationRequest findVerifyReq(long phoneNumber){
        for (VerificationRequest x:verifyRequests){
            if(phoneNumber==x.getPhoneNumber()){
                return x;
            }
        }
        return null;
    }

    public static Support suppFind(String username){
        for (Support x: supports) {
            if(x.getUserName().equals(username)){
                return x;
            }
        }
        return null;

    }

    public static void addSupport(String name,String username,String password){
        supports.add(new Support (name,username,password));
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

    public static List<Support> getSupports() {
        return supports;
    }

    public static void setSupports(List<Support> supports) {
        DataBase.supports = supports;
    }

    public static List<VerificationRequest> getVerifyRequests() {
        return verifyRequests;
    }

    public static void setVerifyRequests(List<VerificationRequest> verifyRequests) {
        DataBase.verifyRequests = verifyRequests;
    }
}
