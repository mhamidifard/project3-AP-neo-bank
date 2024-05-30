package ir.ac.kntu;

import java.util.*;

public class Account {
    private boolean verifyStatus = false;
    private boolean contactFeature = true;
    private int dataBaseNum;
    private long balance = 0;
    private long phoneNumber;
    private String firstName;
    private String lastName;
    private int passwordHash;
    private String nationalId;
    private long accountNumber = 0;
    private int numberInList;
    private Card card;
    private List<Long> transactions = new ArrayList<>();
    private Map<Long, Contact> contactMap = new HashMap<>();
    private List<Long> lastTransferAccs = new LinkedList<>();
    private List<Long> supportRequests = new ArrayList<>();


    public Account(String firstName, String lastName, long phoneNumber, String nationalId, String password, int numberInList) {
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setNationalId(nationalId);
        setPasswordHash(password);
        setNumberInList(numberInList);
    }

    public void verify() {
        setVerifyStatus(true);
        setAccountNumber();
        card=new Card(accountNumber);
    }

    public void addContact(String firstName, String lastName, long phoneNumber) {
        contactMap.put(phoneNumber, new Contact(firstName, lastName, phoneNumber));
    }

    public void removeContact(long phoneNumber) {
        contactMap.remove(phoneNumber);
    }

    public void editContact(long phoneNumber, String firstName, String lastName) {
        contactMap.put(phoneNumber, new Contact(firstName, lastName, phoneNumber));
    }

    public boolean passwordEqual(String password) {
        return password.hashCode() == getPasswordHash();

    }


    public void sendVerifyReq() {
        DataBase.addVerifyReq(phoneNumber);
    }

    public boolean containContact(long phoneNumber) {
        return contactMap.containsKey(phoneNumber);
    }

    public String findContactName(long phone) {
        return contactMap.get(phone).getFirstName() + " " + contactMap.get(phone).getLastName();
    }

    public void charge(long value) {
        setBalance(balance + value);
        transactions.add(DataBase.addCharge(value, getAccountNumber()));
    }

    public long doTransfer(long toAccountNum, long amount) {
        Account toAccountObj = DataBase.findByAccNum(toAccountNum);
        toAccountObj.setBalance(toAccountObj.getBalance() + amount);
        setBalance(balance - amount-Transfer.fee);
        long navId = DataBase.addTransfer(amount, accountNumber, toAccountNum);
        addTransferToList(navId);
        toAccountObj.addTransferToList(navId);
        lastTransferAccs.remove(toAccountNum);
        lastTransferAccs.addFirst(toAccountNum);
        return navId;
    }

    public void addTransferToList(long navId) {
        transactions.add(navId);
    }

    public void addSuppReq(long navId){
        supportRequests.add(navId);
    }

    public void changeCardPass(String pass){
        card.setHashCardPass(pass);
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

    public void setAccountNumber() {
        this.accountNumber = 861900000000L + numberInList;
    }


    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public List<Long> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Long> transactions) {
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

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
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

    public int getNumberInList() {
        return numberInList;
    }

    public void setNumberInList(int numberInList) {
        this.numberInList = numberInList;
    }

    public List<Long> getLastTransferAccs() {
        return lastTransferAccs;
    }

    public void setLastTransferAccs(List<Long> lastTransferAccs) {
        this.lastTransferAccs = lastTransferAccs;
    }

    public List<Long> getSupportRequests() {
        return supportRequests;
    }

    public void setSupportRequests(List<Long> supportRequests) {
        this.supportRequests = supportRequests;
    }

    @Override
    public String toString(){
        return "first name: "+firstName+" last name: "+lastName+
                "\nphone: "+phoneNumber+" account number: "+accountNumber;
    }

    public String summery(){
        return firstName+" "+lastName+" phone: "+phoneNumber;
    }
}
