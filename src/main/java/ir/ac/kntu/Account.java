package ir.ac.kntu;

import java.util.List;
import java.util.Map;

public class Account {
    private boolean verificationStatus=false;
    private boolean contactFeature =true;
    private double balance=0;
    private long phoneNumber;
    private String firstName;
    private String lastName;
    private int passwordHash;
    private String id;
    private long accountNumber=0;
    private Card card;
    private List<Transaction> transactions;
    private Map<Long,Contact> contactMap;

    public Account(String firstName, String lastName, long phoneNumber, String id, String password) {
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setId(id);
        setPasswordHash(password);
    }

    public void verify(){
        setverificationStatus(true);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isverificationStatus() {
        return verificationStatus;
    }

    public void setverificationStatus(boolean verificationStatus) {
        this.verificationStatus = verificationStatus;
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
}
