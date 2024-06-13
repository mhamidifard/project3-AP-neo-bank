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
            Print.menu("menu\n1.manage account\n2.contacts\n3.trasfer\n4.sim charge\n5.support\n6.setting");
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
                TransferInput.goTransfer(account);
                break;
            case 4:
                goSimCharge();
                break;
            case 5:
                UserSupport.menu(account);
                break;
            case 6:
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
            } else if (!Input.isNumber(temp) || Long.parseLong(temp)==0) {
                Print.erorr("invalid number");
            } else {
                return Long.parseLong(temp);
            }
        }
    }

    public static void manageAccount() {
        int choice = 0;
        while (true) {
            Print.info("manage account  " + account.getAccountNumber() + "\nbalance: " + account.getBalance()+"\nsim charge: "+account.getSimCharge());
            Print.menu("1.charge\n2.boxes\n3.transactions\n4.filter transactions");
            choice = simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    charge();
                    break;
                case 2:
                    BoxInput.goBoxes(account);
                    break;
                case 3:
                    listTransactions(null, null);
                    break;
                case 4:
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

    public static void goSimCharge(){
        int choice = 0;
        while (true) {
            Print.menu("buy sim charge\n1.my phone number\n2.contacts\n3.enter phone number");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    chargePhone(account.getPhoneNumber());
                    break;
                case 2:
                    chargeContact();
                    break;
                case 3:
                    inPhoneCharge();
                    break;


                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void inPhoneCharge(){
        long phoneNumber = 0;
        while (true) {
            Print.input("Enter phone number:");
            phoneNumber = simpleLong();
            if (phoneNumber == -1) {
                return;
            }
            if (Long.toString(phoneNumber).length()==10 && Long.toString(phoneNumber).charAt(0)=='9') {
                chargePhone(phoneNumber);
                return;
            } else {
                Print.erorr("invalid phone number");
            }
        }
    }

    public static void chargePhone(long phone){
        long amount;
        while (true) {
            Print.input("Enter amount to be charged:");
            amount = UserInput.simpleLong();
            if (amount == -1) {
                return;
            }
            if (amount < 1000) {
                Print.erorr("amount is small");
            }else if(amount+amount*Admin.getSimChargeFee()/100>account.getBalance()){
                Print.erorr("balance does not enough");
            }else {
                confirmPhoneCharge(phone,amount);
                return;
            }
        }
    }

    public static void chargeContact(){
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
                chargePhone(account.getContactMap().get(contactList.get(choice - 1)).getPhoneNumber());
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static void confirmPhoneCharge(long phone,long amount){
        int choice = 0;
        while (true) {
            Print.info("phone: " + phone + "    amount: " + amount);
            Print.menu("1.confirm\n2.cancel");
            choice = UserInput.simpleMenu();
            if (choice == -1 || choice == 2) {
                return;
            } else if (choice == 1) {
                account.chargeSim(phone,amount);
                return;
            } else {
                Print.erorr("invalid choice");
            }
        }
    }





}