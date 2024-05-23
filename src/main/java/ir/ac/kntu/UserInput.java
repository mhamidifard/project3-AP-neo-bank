package ir.ac.kntu;

import java.util.Scanner;

public class UserInput {
    private static Account account;
    private static Scanner syInput;

    public static void menu(Account account1, Scanner systemIn) {
        account = account1;
        syInput = systemIn;
        String temp;
        int choice = 0;
        while (choice != -1) {
            System.out.println("menu\n1.manage account");
            Input.printBottom();
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                Input.goUser();
                choice = -1;
                return;
            } else if (!Input.isNumber(temp)) {
                System.out.println("invalid choice");
            } else {
                choice = Integer.parseInt(temp);
                switch (choice) {
                    case 1:
                        manageAccount();
                        return;

                    default:
                        choice = 0;
                        System.out.println("invalid choice");
                        break;
                }
            }
        }
    }
    public static void manageAccount() {
        String temp;
        int choice = 0;
        while (choice != -1) {
            System.out.println("manage account\nbalance: "+account.getBalance());
            System.out.println("1.charge\n2.transactions");
            Input.printBottom();
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                menu(account, syInput);
                choice = -1;
                return;
            } else if (!Input.isNumber(temp)) {
                System.out.println("invalid choice");
            } else {
                choice = Integer.parseInt(temp);
                switch (choice) {
                    case 1:
                        charge();
                        return;
                    case 2:

                        return;

                    default:
                        choice = 0;
                        System.out.println("invalid choice");
                        break;
                }
            }
        }
    }
    public static void charge() {
        String temp;
        long amount = 0;
        while (amount == 0) {
            System.out.println("Enter amount to be charged");
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                manageAccount();

                return;
            } else if (!Input.isNumber(temp)) {
                System.out.println("invalid amount");
            } else {
                amount = Long.parseLong(temp);
                if (amount > 0) {
                    account.setBalanc(account.getBalance() + amount);
                } else {
                    System.out.println("invalid amount");
                }
            }
        }
        manageAccount();
    }
}
