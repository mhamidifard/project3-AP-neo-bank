package ir.ac.kntu;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.*;

public class UserInput {
    private static Account account;
    private static Scanner syInput;

    public static void setSyInput(Scanner syInput) {
        UserInput.syInput = syInput;
    }

    public static void menu(Account account1) {
        account = account1;
        int choice = 0;
        while (true) {
            Print.menu("menu\n1.manage account\n2.contacts\n3.trasfer\n4.support\n5.setting");
            choice = simpleMenu();
            if (choice == -1) {
                return;
            }
            switchMenu(choice);
        }
    }

    public static void switchMenu(int choice) {
        switch (choice) {
            case 1:
                manageAccount();
                break;
            case 2:
                goContacts();
                break;
            case 3:
                goTransfer();
                break;
            case 4:
                UserSupport.menu(account);
                break;
            case 5:
                UserSetting.settingMenu(account);
                break;

            default:
                Print.erorr("invalid choice");
                break;
        }
    }

    public static int simpleMenu() {
        String temp;
        while (true) {
            Input.printBottom();
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                return -1;
            } else if (!Input.isNumber(temp)) {
                Print.erorr("invalid choice");
            } else {
                return Integer.parseInt(temp);
            }
        }
    }

    public static long simpleLong() {
        String temp;
        while (true) {
            //Input.printBottom();
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                return -1;
            } else if (!Input.isNumber(temp)) {
                Print.erorr("invalid number");
            } else {
                return Long.parseLong(temp);
            }
        }
    }

    public static void manageAccount() {
        int choice = 0;
        while (true) {
            Print.info("manage account  " + account.getAccountNumber() + "\nbalance: " + account.getBalance());
            Print.menu("1.charge\n2.transactions\n3.filter transactions");
            choice = simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    charge();
                    break;
                case 2:
                    listTransactions(null, null);
                    break;
                case 3:
                    goFilterTra();
                    break;

                default:
                    Print.erorr("invalid choice");
                    break;
            }

        }
    }

    public static void charge() {
        String temp;
        long amount = 0;
        while (amount == 0) {
            Print.input("Enter amount to be charged");
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                //manageAccount();
                return;
            } else if (!Input.isNumber(temp)) {
                Print.erorr("invalid amount");
            } else {
                amount = Long.parseLong(temp);
                if (amount > 0) {
                    account.charge(amount);
                } else {
                    Print.erorr("invalid amount");
                }
            }
        }
        //manageAccount();
    }

    public static void goFilterTra() {
        Instant minDate = inFilterDate("min");
        if (minDate == null) {
            return;
        }
        Instant maxDate = inFilterDate("max");
        if (maxDate == null) {
            return;
        }
        listTransactions(minDate, maxDate);
    }

    public static void listTransactions(Instant minDate, Instant maxDate) {
        int choice;
        List<Long> transactions = account.getTransactions();
        List<Long> validTransaction;
        while (true) {
            validTransaction = new ArrayList<>();
            int counter = 1;
            for (Long navId : transactions) {
                if (filterDate(navId, minDate, maxDate)) {
                    Print.list(counter + ": " + DataBase.findTransaction(navId).summery());
                    validTransaction.add(navId);
                    counter++;
                }
            }
            choice = simpleMenu();
            if (choice == -1) {
                return;
            }
            if (0 < choice && choice <= validTransaction.size()) {
                showTransaction(validTransaction.get(choice - 1));
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static Instant inFilterDate(String text) {
        Instant ans;
        String temp, date;
        while (true) {
            Print.input("Enter " + text + " date: example(2018-02-20)");
            Input.printBottom();
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                return null;
            }
            date = temp;

            Print.input("Enter " + text + " date time: example(18:05:24)");
            Input.printBottom();
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                return null;
            }
            date = date + "T" + temp + "Z";
            try {
                ans = Instant.parse(date);
                return ans;
            } catch (DateTimeParseException e) {
                Print.erorr("invalid format");
            }
        }
    }

    public static boolean filterDate(long navId, Instant minDate, Instant maxDate) {
        if (minDate == null || maxDate == null) {
            return true;
        }
        Instant date = DataBase.findTransaction(navId).getDate();
        return date.compareTo(minDate) > 0 && date.compareTo(maxDate) < 0;
    }


    public static void goContacts() {
        int choice = 0;
        while (true) {
            Print.info("contacts");
            Print.menu("1.add contact\n2.list contacts");
            choice = simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    addcontact();
                    break;
                case 2:
                    listContacts();
                    break;

                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void addcontact() {
        String firstName, lastName;
        Print.info("add contact");
        firstName = Input.defineFistName();
        if ("@".equals(firstName)) {
            return;
        }
        lastName = Input.defineLastName();
        if ("@".equals(lastName)) {
            return;
        }
        long phoneNumber = defContactPhone();
        if (phoneNumber == -1) {
            return;
        }
        account.addContact(firstName, lastName, phoneNumber);
    }

    public static long defContactPhone() {
        long phoneNumber = 0;
        while (true) {
            Print.input("Enter phone number:");
            phoneNumber = simpleLong();
            if (phoneNumber == -1) {
                return -1;
            }
            if (DataBase.findByPhone(phoneNumber) == null) {
                Print.erorr("there is not this phone number ");
            } else if (account.containContact(phoneNumber)) {
                Print.erorr("contact already exists");
            } else if (phoneNumber == account.getPhoneNumber()) {
                Print.erorr("this is your phone");
            } else {
                return phoneNumber;
            }
        }
    }

    public static void listContacts() {
        int choice;
        ArrayList<Long> contactList;
        while (true) {
            contactList = new ArrayList<>();
            int counter = 1;
            Print.info("contacts list");
            for (Map.Entry<Long, Contact> element : account.getContactMap().entrySet()) {
                contactList.add(element.getKey());
                Print.list(counter + ". " + element.getValue().summery());
                counter++;
            }
            choice = simpleMenu();
            if (choice == -1) {
                return;
            }
            if (0 < choice && choice <= contactList.size()) {
                checkContact(contactList.get(choice - 1));
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static void checkContact(long contactPhone) {
        int choice = -1;
        while (choice == -1) {
            Print.input(account.getContactMap().get(contactPhone).toString());
            Print.menu("1.edit\n2.remove");
            choice = simpleMenu();
            if (choice == -1) {
                return;
            }
            if (choice == 1) {
                editContact(contactPhone);
                return;
            } else if (choice == 2) {
                account.removeContact(contactPhone);
                return;
            } else {
                choice = -1;
                Print.erorr("invalid choice");
            }
        }
    }

    public static void editContact(long contactPhone) {
        String firstName, lastName;
        Print.info("edit contact");
        firstName = Input.defineFistName();
        if ("@".equals(firstName)) {
            return;
        }
        lastName = Input.defineLastName();
        if ("@".equals(lastName)) {
            return;
        }
        account.editContact(contactPhone, firstName, lastName);
    }

    public static void goTransfer() {
        int choice = 0;
        while (true) {
            Print.menu("transfer by\n1.account number\n2.contacts\n3.last transfers");
            choice = simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    inTranAccNum();
                    break;
                case 2:
                    inTranContact();
                    break;
                case 3:
                    inTranLast();
                    break;

                default:
                    choice = 0;
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void inTranAccNum() {
        long toAccountNum;
        while (true) {
            Print.input("Enter account number:");
            toAccountNum = simpleLong();
            if (toAccountNum == -1) {
                return;
            }
            if (DataBase.findByAccNum(toAccountNum) == null) {
                Print.erorr("there is not this account number ");
            } else if (toAccountNum == account.getAccountNumber()) {
                Print.erorr("this is your account");
            } else {
                amountTransfer(toAccountNum);
                return;
            }
        }
    }

    public static void amountTransfer(long toAccountNum) {
        long amount;
        if (!DataBase.findByAccNum(toAccountNum).isVerifyStatus()) {
            Print.erorr("this account is not verified");
            return;
        }
        while (true) {
            Print.input("Enter amount to be transferred:");
            amount = simpleLong();
            if (amount == -1) {
                return;
            }
            if (amount < Transfer.fee) {
                Print.erorr("amount is small");
            } else if (amount + Transfer.fee > account.getBalance()) {
                Print.erorr("The balance is not enough");
            } else {
                checkTransfer(toAccountNum, amount);
                return;
            }
        }
    }

    public static void checkTransfer(long toAccountNum, long amount) {
        Account toAccount = DataBase.findByAccNum(toAccountNum);
        String name = toAccount.getFirstName() + " " + toAccount.getLastName();
        int choice = 0;
        while (true) {
            Print.info("to: " + toAccountNum + " name: " + name + "\namount: " + amount);
            Print.menu("1.confirm\n2.cancel");
            choice = simpleMenu();
            if (choice == -1) {
                return;
            } else if (choice == 1) {
                long navId = account.doTransfer(toAccountNum, amount);
                showTransaction(navId);
                return;
            } else if (choice == 2) {
                return;
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static void showTransaction(long navId) {
        DataBase.printTransaction(navId, account);
        String temp;
        while (true) {
            Input.printBottom();
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                return;
            } else {
                Print.erorr("invalid input");
            }
        }
    }

    public static void inTranContact() {
        int choice;
        ArrayList<Long> contactList;
        Account toAccount;
        while (true) {
            contactList = new ArrayList<>();
            int counter = 1;
            Print.info("contacts list");
            for (Map.Entry<Long, Contact> element : account.getContactMap().entrySet()) {
                toAccount = DataBase.findByAccNum(element.getValue().getAccountNumber());
                if (toAccount.isVerifyStatus() && toAccount.isContactFeature() && toAccount.containContact(account.getPhoneNumber())) {
                    contactList.add(element.getValue().getAccountNumber());
                    Print.list(counter + ". " + element.getValue().summery());
                    counter++;
                }
            }
            choice = simpleMenu();
            if (choice == -1) {
                return;
            }
            if (0 < choice && choice <= contactList.size()) {
                amountTransfer(contactList.get(choice - 1));
                return;
            } else {
                Print.erorr("invalid choice");
            }
        }

    }

    public static void inTranLast() {
        int choice;
        List<Long> accounts = account.getLastTransferAccs();
        for (int i = 0; i < accounts.size(); i++) {
            Print.list(i + 1 + ": " + accounts.get(i));
        }
        choice = simpleMenu();
        if (choice == -1) {
            return;
        }
        if (0 < choice && choice <= accounts.size()) {
            amountTransfer(accounts.get(choice - 1));
            return;
        } else {
            Print.erorr("invalid choice");
        }

    }

}