package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {
    private boolean verifyStatus =false;
    private boolean contactFeature =true;
    private int dataBaseNum;
    private double balance=0;
    private long phoneNumber;
    private String firstName;
    private String lastName;
    private int passwordHash;
    private String nationalId;
    private long accountNumber=0;
    private Card card;
    private List<Transaction> transactions=new ArrayList<>();
    private Map<Long,Contact> contactMap=new HashMap<>();


    public Account(String firstName, String lastName, long phoneNumber, String id, String password) {
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setNationalId(id);
        setPasswordHash(password);
    }

    public void verify(){
        setVerifyStatus(true);
    }

    public boolean addContact(String firstName, String lastName, long phoneNumber){
        if(contactMap.containsKey(phoneNumber)){
            return false;
        }else {
            contactMap.put(phoneNumber,new Contact(firstName, lastName, phoneNumber));
            return true;
        }
    }
    public boolean passwordEqual(String password){
        if(password.hashCode()==getPasswordHash()) {
            return true;
        }
        return false;

    }

    public  void sendVerifyReq(){
        DataBase.addVerifyReq(phoneNumber);
    }












    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = password.hashCode();
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }


    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Map<Long, Contact> getContactMap() {
        return contactMap;
    }

    public void setContactMap(Map<Long, Contact> contactMap) {
        this.contactMap = contactMap;
    }

    public boolean isContactFeature() {
        return contactFeature;
    }

    public void setContactFeature(boolean contactFeature) {
        this.contactFeature = contactFeature;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(boolean verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public int getDataBaseNum() {
        return dataBaseNum;
    }

    public void setDataBaseNum(int dataBaseNum) {
        this.dataBaseNum = dataBaseNum;
    }
}
