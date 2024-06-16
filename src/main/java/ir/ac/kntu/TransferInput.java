package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransferInput {

    private static Account account;

    public static void goTransfer(Account account1) {
        account = account1;
        int choice = 0;
        while (true) {
            Print.menu("transfer by\n1.account number\n2.card number\n3.contacts\n4.last transfers");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    inTranAccNum();
                    break;
                case 2:
                    inTranCardNum();
                    break;
                case 3:
                    inTranContact();
                    break;
                case 4:
                    inTranLast();
                    break;

                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void inTranAccNum() {
        long toAccountNum;
        while (true) {
            Print.input("Enter account number:");
            toAccountNum = UserInput.simpleLong();
            if (toAccountNum == -1) {
                return;
            }
            if (DataBase.findByAccNum(toAccountNum) == null) {
                if (OtherDataBase.findByAccNum(toAccountNum) == null) {
                    Print.erorr("there is not this account number ");
                } else {
                    selectTransferOther(toAccountNum, 0L);
                    return;
                }
            } else if (toAccountNum == account.getAccountNumber()) {
                Print.erorr("this is your account");
            } else {
                selectTransfer(toAccountNum);
                return;
            }
        }
    }

    public static void inTranCardNum() {
        long toCardNum;
        long toAccountNum;
        while (true) {
            Print.input("Enter card number:");
            toCardNum = UserInput.simpleLong();
            if (toCardNum == -1) {
                return;
            }
            if (DataBase.findByCardNum(toCardNum) == null) {
                if (OtherDataBase.findByCardNum(toCardNum) == null) {
                    Print.erorr("there is not this account number ");
                } else {
                    toAccountNum = OtherDataBase.findByCardNum(toCardNum).getAccountNumber();
                    selectTransferOther(toAccountNum, toCardNum);
                    return;
                }
            } else if (toCardNum == account.getCardNumber()) {
                Print.erorr("this is your account");
            } else {
                toAccountNum = DataBase.findByCardNum(toCardNum).getAccountNumber();
                selectTransfer(toAccountNum);
                return;
            }
        }
    }

//    public static long amountTransfer(long toAccountNum) {
//        long amount;
//        if (!DataBase.findByAccNum(toAccountNum).isVerifyStatus()) {
//            Print.erorr("this account is not verified");
//            return;
//        }
//        while (true) {
//            Print.input("Enter amount to be transferred:");
//            amount = UserInput.simpleLong();
//            if (amount == -1) {
//                return;
//            }
//            if (amount < Transfer.fee) {
//                Print.erorr("amount is small");
//            } else if (amount + Transfer.fee > account.getBalance()) {
//                Print.erorr("The balance is not enough");
//            } else {
//                SelectTransfer(toAccountNum, amount);
//                return;
//            }
//        }
//    }

    public static long amountTransfer() {
        long amount;
        while (true) {
            Print.input("Enter amount to be transferred:");
            amount = UserInput.simpleLong();
            if (amount == -1) {
                return -1;
            }
            if (amount < 1000) {
                Print.erorr("amount is small");
            } else {
                return amount;
            }
        }
    }

    public static void selectTransfer(long toAccountNum) {
        int choice = 0;
        long amount = amountTransfer();
        if (amount == -1) {
            return;
        }
        while (true) {
            List<TransferType> choices = new ArrayList<>();
            if (amount <= Parametr.getFariToMax()) {
                choices.add(TransferType.FARITOFARI);
            }
            for (int i = 1; i <= choices.size(); i++) {
                Print.list(i + "." + choices.get(i - 1));
            }
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            if (choices.size() == 1 && choice == 1) {
                account.updateListTransfer(toAccountNum, 0L);
                fariTo(toAccountNum, amount);
                return;
            } else {
                Print.erorr("invalid choice");
            }
        }

    }

    public static void selectTransferOther(long toAccountNum, Long toCardNum) {
        int choice = 0;
        long amount = amountTransfer();
        if (amount == -1) {
            return;
        }
        while (true) {
            List<TransferType> choices = new ArrayList<>();
            if (toCardNum != 0 && amount <= Parametr.getCardToMax()) {
                choices.add(TransferType.CardToCard);
            }
            if (amount <= Parametr.getPolMax()) {
                choices.add(TransferType.POL);
            }
            if (amount <= Parametr.getPayaMax()) {
                choices.add(TransferType.PAYA);
            }
            for (int i = 1; i <= choices.size(); i++) {
                Print.list(i + "." + choices.get(i - 1));
            }
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            if (0 < choice && choice <= choices.size()) {
                account.updateListTransfer(toAccountNum, toCardNum);
                switchTransfer(toAccountNum, toCardNum, amount, choices.get(choice - 1));
                return;
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static void switchTransfer(long toAccountNum, long toCardNum, long amount, TransferType type) {
        switch (type) {
            case CardToCard:
                cardTo(toCardNum, amount);
                break;
            case POL:
                pol(toAccountNum, amount);
                break;
            case PAYA:
                paya(toAccountNum, amount);
                break;
            default:
                break;
        }
    }

    public static void cardTo(long toCardNum, long amount) {
        OtherBanks toAccount = OtherDataBase.findByCardNum(toCardNum);
        if (amount + Parametr.getCardToFee() > account.getBalance()) {
            Print.erorr("The balance is not enough");
            return;
        }
        if (!confirmOther(toAccount, amount, toCardNum)) {
            return;
        }
        long navId = account.doCardTo(toCardNum, amount);
        UserInput.showTransaction(navId);
    }

    public static boolean confirmOther(OtherBanks toAccount, long amount, Long toNum) {
        String name = toAccount.getFirstName() + " " + toAccount.getLastName();
        int choice = 0;
        while (true) {
            Print.info("to: " + toNum + " name: " + name + "\namount: " + amount);
            Print.menu("1.confirm\n2.cancel");
            choice = UserInput.simpleMenu();
            if (choice == -1 || choice == 2) {
                return false;
            } else if (choice == 1) {
                return true;
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static boolean confirmFari(Account toAccount, long amount, Long toNum) {
        String name = toAccount.getFirstName() + " " + toAccount.getLastName();
        int choice = 0;
        while (true) {
            Print.info("to: " + toNum + " name: " + name + "\namount: " + amount);
            Print.menu("1.confirm\n2.cancel");
            choice = UserInput.simpleMenu();
            if (choice == -1 || choice == 2) {
                return false;
            } else if (choice == 1) {
                return true;
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static void pol(long toAccountNum, long amount) {
        OtherBanks toAccount = OtherDataBase.findByAccNum(toAccountNum);
        if (amount + (Parametr.getPolFee() * amount) / 100 > account.getBalance()) {
            Print.erorr("The balance is not enough");
            return;
        }
        if (!confirmOther(toAccount, amount, toAccountNum)) {
            return;
        }
        long navId = account.doPol(toAccountNum, amount);
        UserInput.showTransaction(navId);
    }

    public static void paya(long toAccountNum, long amount) {
        OtherBanks toAccount = OtherDataBase.findByAccNum(toAccountNum);
        if (amount + Parametr.getPayaFee() > account.getBalance()) {
            Print.erorr("The balance is not enough");
            return;
        }
        if (!confirmOther(toAccount, amount, toAccountNum)) {
            return;
        }
        long navId = account.doPaya(toAccountNum, amount);
        UserInput.showTransaction(navId);
    }

    public static void fariTo(long toAccountNum, long amount) {
        Account toAccount = DataBase.findByAccNum(toAccountNum);
        if (amount + Parametr.getFariToFee() > account.getBalance()) {
            Print.erorr("The balance is not enough");
            return;
        } else if (!confirmFari(toAccount, amount, toAccountNum)) {
            return;
        }
        long navId = account.doFariTo(toAccountNum, amount);
        UserInput.showTransaction(navId);
    }


    //    public static void checkTransfer(long toAccountNum, long amount) {
//        Account toAccount = DataBase.findByAccNum(toAccountNum);
//        String name = toAccount.getFirstName() + " " + toAccount.getLastName();
//        int choice = 0;
//        while (true) {
//            Print.info("to: " + toAccountNum + " name: " + name + "\namount: " + amount);
//            Print.menu("1.confirm\n2.cancel");
//            choice = UserInput.simpleMenu();
//            if (choice == -1) {
//                return;
//            } else if (choice == 1) {
//                long navId = account.doTransfer(toAccountNum, amount);
//                UserInput.showTransaction(navId);
//                return;
//            } else if (choice == 2) {
//                return;
//            } else {
//                Print.erorr("invalid choice");
//            }
//        }
//    }
//
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
                if (toAccount != null && toAccount.isVerifyStatus() && toAccount.isContactFeature() && toAccount.containContact(account.getPhoneNumber())) {
                    contactList.add(element.getValue().getAccountNumber());
                    Print.list(counter + ". " + element.getValue().summery());
                    counter++;
                }
            }
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            if (0 < choice && choice <= contactList.size()) {
                selectTransfer(contactList.get(choice - 1));
                return;
            } else {
                Print.erorr("invalid choice");
            }
        }

    }

    public static void inTranLast() {
        int choice;
        List<LastTransfer> accounts = account.getLastTransferAccs();
        for (int i = 0; i < accounts.size(); i++) {
            Print.list(i + 1 + ": " + accounts.get(i));
        }
        while (true) {
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            if (0 < choice && choice <= accounts.size()) {
                tranListSwitch(accounts.get(choice - 1));
                return;
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static void tranListSwitch(LastTransfer lastTransfer) {
        if (lastTransfer.getCardNum() != 0) {
            selectTransferOther(lastTransfer.getAccountNum(), lastTransfer.getCardNum());
        } else if (OtherDataBase.findByAccNum(lastTransfer.getAccountNum()) != null) {
            selectTransferOther(lastTransfer.getAccountNum(), lastTransfer.getCardNum());
        } else if (DataBase.findByAccNum(lastTransfer.getAccountNum()) != null) {
            selectTransfer(lastTransfer.getAccountNum());
        } else {
            Print.erorr("there is not this account");
        }
    }
}
