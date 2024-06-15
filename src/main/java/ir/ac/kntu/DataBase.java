package ir.ac.kntu;

import java.util.*;

public class DataBase {
    private static List<Account> accounts = new ArrayList<>();
    private static List<Support> supports = new ArrayList<>();
    private static List<Admin> admins=new ArrayList<>();
    private static Map<Long, Transaction> transactions = new HashMap<>();
    private static List<VerificationRequest> verifyRequests = new LinkedList<>();
    private static Map<Long, SupportRequest> supportRequests = new HashMap<>();
    private static List<RewardBox> rewardBoxes = new ArrayList<>();
    private static Map<Long,Long> simS=new HashMap<>();


    public static Account findByAccNum(long accNum) {
        if(accNum==0){
            return null;
        }
        for (Account user : accounts) {
            if (user.getAccountNumber() == accNum) {
                return user;
            }
        }
        return null;
    }

    public static Account findByCardNum(long cardNum) {
        if(cardNum==0){
            return null;
        }
        for (Account user : accounts) {
            if (user.getCardNumber() == cardNum) {
                return user;
            }
        }
        return null;
    }

    public static Account findByPhone(long phone) {
        if(phone==0){
            return null;
        }
        for (Account user : accounts) {
            if (user.getPhoneNumber() == phone) {
                return user;
            }
        }
        return null;
    }

    public static Account findById(String natinalId) {
        for (Account user : accounts) {
            if (user.getNationalId().equals(natinalId)) {
                return user;
            }
        }
        return null;
    }

    public static void addUser(Account account) {
        account.setNumberInList(accounts.size());
        accounts.add(account);
        accounts.get(accounts.size() - 1).setDataBaseNum(accounts.size() - 1);
    }

    public static void addVerifyReq(long phoneNumber) {
        verifyRequests.add(new VerificationRequest(phoneNumber));
    }

    public static long addSuppVerify(long phoneNumber){
        SupportRequest request=addSupportReq("#verify",phoneNumber,Subject.VERIFY);
        Account account=findByPhone(phoneNumber);
        String message="first name: " + account.getFirstName() + "  " + "last name: " + account.getLastName() + "\n"
                + "phone: " + account.getPhoneNumber() + " id: " + account.getNationalId();
        request.addMessage(message,Sender.USER);
        return request.getNavId();
    }

    public static void removeVerifyReq(VerificationRequest verifyReq) {
        verifyRequests.remove(verifyReq);
    }

    public static void removeVerifyReq(long phoneNummber) {
        for (int i = 0; i < verifyRequests.size(); i++) {
            if (verifyRequests.get(i).getPhoneNumber() == phoneNummber) {
                verifyRequests.remove(i);
                i--;
            }
        }
    }

    public static void renewVerifyReq(long lastPhone, long newPhone) {
        for (int i = 0; i < verifyRequests.size(); i++) {
            if (verifyRequests.get(i).getPhoneNumber() == lastPhone) {
                verifyRequests.set(i, new VerificationRequest(newPhone));
                return;
            }
        }
    }

    public static VerificationRequest findVerifyReq(long phoneNumber) {
        for (VerificationRequest x : verifyRequests) {
            if (phoneNumber == x.getPhoneNumber()) {
                return x;
            }
        }
        return null;
    }

    public static Support suppFind(String username) {
        for (Support x : supports) {
            if (x.getUserName().equals(username)) {
                return x;
            }
        }
        return null;

    }

    public static Admin adminFind(String username){
        for (Admin admin:admins){
            if(admin.getUserName().equals(username)){
                return admin;
            }
        }
        return null;
    }

    public static void addSupport(String name, String username, String password) {
        supports.add(new Support(name, username, password));
    }


    public static void addAdmin(String name, String username, String password) {
        admins.add(new Admin(name, username, password));
    }

    public static List<Account> getAccounts() {
        return accounts;
    }

    public static long addCharge(long value, long account) {
        Charge charge = new Charge(value, account);
        transactions.put(charge.getNavId(), charge);
        return charge.getNavId();
    }

    public static long addTransfer(long amount, long fromAccount, long toAccount,TransferType transferType) {
        Transfer transfer = new Transfer(amount, fromAccount, toAccount,transferType);
        transactions.put(transfer.getNavId(), transfer);
        return transfer.getNavId();
    }

    public static long addTransaction(Transaction transaction){
        transactions.put(transaction.getNavId(),transaction);
        return transaction.getNavId();
    }

    public static void printTransaction(long navId, Account account) {
        Print.info(transactions.get(navId).toStringComplete(account));
    }

    public static Transaction findTransaction(long navId) {
        return transactions.getOrDefault(navId, null);
    }

    public static SupportRequest findSuppReq(Long navId) {
        return supportRequests.getOrDefault(navId, null);
    }

    public static SupportRequest addSupportReq(String title, long userPhone, Subject subject) {
        SupportRequest request = new SupportRequest(userPhone, title, subject);
        supportRequests.put(request.getNavId(), request);
        return request;
    }

    public static void addRewardBox(RewardBox box){
        rewardBoxes.add(box);
    }

    public static void removeRewardBox(RewardBox box){
        rewardBoxes.remove(box);
    }

    public static void addChargeSim(long phone,long amount){
        if(simS.containsKey(phone)){
            simS.put(phone,simS.get(phone)+amount);
        }else {
            simS.put(phone,amount);
        }
    }

    public static long getChargeSim(long phone){
        return simS.getOrDefault(phone,0L);
    }

    public static void setAccounts(List<Account> accounts) {
        DataBase.accounts = accounts;
    }

    public static Map<Long, Transaction> getTransactions() {
        return transactions;
    }

    public static void setTransactions(Map<Long, Transaction> transactions) {
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

    public static Map<Long, SupportRequest> getSupportRequests() {
        return supportRequests;
    }

    public static void setSupportRequests(Map<Long, SupportRequest> supportRequests) {
        DataBase.supportRequests = supportRequests;
    }

    public static Map<Long, Long> getSimS() {
        return simS;
    }

    public static void setSimS(Map<Long, Long> simS) {
        DataBase.simS = simS;
    }

    public static List<RewardBox> getRewardBoxes() {
        return rewardBoxes;
    }

    public static void setRewardBoxes(List<RewardBox> rewardBoxes) {
        DataBase.rewardBoxes = rewardBoxes;
    }

    public static List<Admin> getAdmins() {
        return admins;
    }

    public static void setAdmins(List<Admin> admins) {
        DataBase.admins = admins;
    }
}
