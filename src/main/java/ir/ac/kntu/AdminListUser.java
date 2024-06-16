package ir.ac.kntu;

import ir.ac.kntu.util.ComprableUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminListUser {
    public static void inFilterUser() {
        Map<Filter, String> filters = new HashMap<>();
        int choice = 0;
        while (true) {
            Print.info("filter user by");
            Print.menu("1.first name\n2.last name\n3.phone number\n4.apply");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            } else if (choice == 1) {
                SupportInput.inFirstNameFilter(filters);
            } else if (choice == 2) {
                SupportInput.inLastNameFilter(filters);
            } else if (choice == 3) {
                SupportInput.inpPhoneFilter(filters);
            } else if (choice == 4) {
                filterUser(filters);
                filters = new HashMap<>();
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static void filterUser(Map<Filter, String> filters) {
        ArrayList<ComprableUser> listUser = new ArrayList<>();
        for (Account account : DataBase.getAccounts()) {
            ComprableUser user = new ComprableUser(account);
            for (HashMap.Entry<Filter, String> filter : filters.entrySet()) {
                SupportInput.checkUser(filter, account, user);
            }
            if (user.isCondition()) {
                listUser.add(user);
            }
        }
        listUser.sort(ComprableUser::compareTo);
        showUserList(listUser);
    }

    public static void showUserList(List<ComprableUser> listUser) {
        Print.info("list of users:");
        int choice;
        while (true) {
            for (int i = 0; i < listUser.size(); i++) {
                Print.list(i + 1 + ". " + listUser.get(i).getAccount().summery());
            }
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            if (0 < choice && choice <= listUser.size()) {
                showUser(listUser.get(choice - 1).getAccount());
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static void showUser(Account account) {
        int choice;
        while (true) {
            Print.info(account.toString());
            Print.menu("\n1.list transactions\n2.filter transactions\n3.change verify status\n4.change basic information");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switchShowUser(account,choice);
        }
    }

    public static void switchShowUser(Account account,int choice) {
        switch (choice) {
            case 1:
                SupportInput.listTransactions(account, null, null);
                break;
            case 2:
                SupportInput.goFilterTra(account);
                break;
            case 3:
                account.setVerifyStatus(!account.isVerifyStatus());
                if(account.isVerifyStatus()) {
                    account.verify();
                }
                break;
            case 4:
                changeUserInfo(account);
                break;
            default:
                Print.erorr("invalid choice");
                break;
        }
    }

    public static void changeUserInfo(Account account){
        int choice;
        while (true) {
            Print.info(account.toString());
            Print.menu("\n1.change first name\n2.change last name\n3.change id\n4.change password");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switchChangeUserInfo(account,choice);
        }
    }

    public static void switchChangeUserInfo(Account account,int choice){
        switch (choice) {
            case 1:
                changeFirstName(account);
                break;
            case 2:
                changeLastName(account);
                break;
            case 3:
                changeId(account);
                break;
            case 4:
                changePassWord(account);
                break;
            default:
                Print.erorr("invalid choice");
                break;
        }
    }

    public static void changeFirstName(Account account){
        String name=Input.defineFistName();
        if("@".equals(name)){
            return;
        }
        account.setFirstName(name);
    }

    public static void changeLastName(Account account){
        String name=Input.defineLastName();
        if("@".equals(name)){
            return;
        }
        account.setLastName(name);
    }

    public static void changePhone(Account account){
        long phone=Input.definePhone(account);
        if(phone==0){
            return;
        }
        account.setPhoneNumber(phone);
    }

    public static void changeId(Account account){
        String nationalId=Input.defineId(account);
        if("@".equals(nationalId)){
            return;
        }
        account.setNationalId(nationalId);
    }

    public static void changePassWord(Account account){
        String password=Input.definePassword();
        if("@".equals(password)){
            return;
        }
        account.setPasswordHash(password);
    }

}
