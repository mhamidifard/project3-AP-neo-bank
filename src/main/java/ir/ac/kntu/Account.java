package ir.ac.kntu;

import java.util.*;

public class Account {
    private boolean verifyStatus = false;
    private boolean contactFeature = true;
    private Long verifyReq;
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
    private List<LastTransfer> lastTransferAccs = new LinkedList<>();
    private List<Long> supportRequests = new ArrayList<>();
    private Box smallMoneyBox;
    private List<Box> boxes = new ArrayList<>();


    public Account(String firstName, String lastName, long phoneNumber, String nationalId, String password) {
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setNationalId(nationalId);
        setPasswordHash(password);
    }

    public void verify() {
        setVerifyStatus(true);
        setAccountNumber();
        card = new Card(accountNumber);
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

    public void doTransfer(long toAccountNum, long amount, long navId) {
        Account toAccountObj = DataBase.findByAccNum(toAccountNum);
        addTransferToList(navId);
        if (toAccountObj != null) {
            toAccountObj.setBalance(toAccountObj.getBalance() + amount);
            toAccountObj.addTransferToList(navId);
        }
//        lastTransferAccs.remove(toAccountNum);
//        //lastTransferAccs.addFirst(toAccountNum);
//        lastTransferAccs.add(0,toAccountNum);
    }

    public void updateListTransfer(long accNum, long cardNum) {
        LastTransfer transfer = new LastTransfer(accNum, cardNum);
        lastTransferAccs.remove(transfer);
        lastTransferAccs.add(0, transfer);
    }

    public long doCardTo(long toCardNum, long amount) {
        long navId = DataBase.addTransfer(amount, accountNumber, toCardNum, TransferType.CardToCard);
        setBalance(balance - amount - Admin.getCardToFee());
        doTransfer(toCardNum, amount, navId);
        checkSmallBox(amount + Admin.getCardToFee());
        return navId;
    }

    public long doPol(long toAccNum, long amount) {
        long navId = DataBase.addTransfer(amount, accountNumber, toAccNum, TransferType.POL);
        setBalance(balance - amount - (Admin.getPolFee() * amount) / 100);
        doTransfer(toAccNum, amount, navId);
        checkSmallBox(amount + (Admin.getPolFee() * amount) / 100);
        return navId;
    }

    public long doPaya(long toAccNum, long amount) {
        long navId = DataBase.addTransfer(amount, accountNumber, toAccNum, TransferType.PAYA);
        setBalance(balance - amount - Admin.getPayaFee());
        doTransfer(toAccNum, amount, navId);
        checkSmallBox(amount + Admin.getPayaFee());
        return navId;
    }

    public long doFariTo(long toAccNum, long amount) {
        long navId = DataBase.addTransfer(amount, accountNumber, toAccNum, TransferType.FARITOFARI);
        setBalance(balance - amount - Admin.getFariToFee());
        doTransfer(toAccNum, amount, navId);
        checkSmallBox(amount + Admin.getFariToFee());
        return navId;
    }


    public void addTransferToList(long navId) {
        transactions.add(navId);
    }

    public void addSuppReq(long navId) {
        supportRequests.add(navId);
    }

    public void changeCardPass(String pass) {
        card.setHashCardPass(pass);
    }

    public void addBox(Box box) {
        boxes.add(box);
        if (box.getType() == TypeBox.REWARD) {
            RewardBox rewardBox = (RewardBox) box;
            rewardBox.setAccount(this);
            DataBase.addRewardBox(rewardBox);
        }
        if (box.getType() == TypeBox.SMALLMONEY) {
            smallMoneyBox = box;
        }
        Print.info("box added successfully");
    }

    public void removeBox(Box box) {
        boxes.remove(box);
        if (box.getType() == TypeBox.REWARD) {
            DataBase.removeRewardBox((RewardBox) box);
        }
        if (box.getType() == TypeBox.SMALLMONEY) {
            smallMoneyBox = null;
        }
    }

    public void withdraw(Box box, long amount) {
        balance += amount;
        box.withdraw(amount);
        Long navId = DataBase.addTransaction(new TraBox(amount, accountNumber, BoxAction.WITHDRAW));
        transactions.add(navId);
    }

    public void deposit(Box box, long amount) {
        if (box == null) {
            return;
        }
        balance -= amount;
        box.deposit(amount);
        Long navId = DataBase.addTransaction(new TraBox(amount, accountNumber, BoxAction.DEPOSIT));
        transactions.add(navId);
    }

    public void checkSmallBox(long money) {
        if (smallMoneyBox == null) {
            return;
        }
        int digitSize = Long.toString(money).length() * 3 / 4;
        long base = Helper.pow(10, digitSize);
        money = money % base;
        long amount = base - money;
        if (amount > balance) {
            return;
        }
        deposit(smallMoneyBox, amount);
    }

    public void chargeSim(long phoneNumber,long amount){
        balance-=(amount+amount*Admin.getSimChargeFee()/100);
        DataBase.addChargeSim(phoneNumber,amount);
        Long navId = DataBase.addTransaction(new TraSimCharge(amount,phoneNumber,accountNumber));
        transactions.add(navId);
        checkSmallBox(amount+amount*Admin.getSimChargeFee()/100);
    }

    public long getSimCharge(){
        return DataBase.getChargeSim(phoneNumber);
    }

    public long getCardNumber() {
        return card.getCardNumber();
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

    public List<LastTransfer> getLastTransferAccs() {
        return lastTransferAccs;
    }

    public void setLastTransferAccs(List<LastTransfer> lastTransferAccs) {
        this.lastTransferAccs = lastTransferAccs;
    }

    public List<Long> getSupportRequests() {
        return supportRequests;
    }

    public void setSupportRequests(List<Long> supportRequests) {
        this.supportRequests = supportRequests;
    }

    public Box getSmallMoneyBox() {
        return smallMoneyBox;
    }

    public void setSmallMoneyBox(Box smallMoneyBox) {
        this.smallMoneyBox = smallMoneyBox;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    @Override
    public String toString() {
        return "first name: " + firstName + " last name: " + lastName +
                "\nphone: " + phoneNumber + " account number: " + accountNumber;
    }

    public String summery() {
        return firstName + " " + lastName + " phone: " + phoneNumber;
    }

    public Long getVerifyReq() {
        return verifyReq;
    }

    public void setVerifyReq(Long verifyReq) {
        this.verifyReq = verifyReq;
    }
}
