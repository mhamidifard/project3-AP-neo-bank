package ir.ac.kntu;

import java.util.Scanner;

public class UserSetting {
    private static Account account;
    private static Scanner syInput;

    public static void setSyInput(Scanner syInput) {
        UserSetting.syInput = syInput;
    }

    public static void settingMenu(Account account1) {
        account = account1;
        int choice;
        while (true) {
            Print.info("setting\ncontacts feature=" + account.isContactFeature());
            Print.menu("1.change contacts feature status\n2.change password\n3.change card pass");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    account.setContactFeature(!account.isContactFeature());
                    break;
                case 2:
                    changePassword();
                    break;
                case 3:
                    changeCardPass();
                    break;

                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void changePassword() {
        String password = Input.definePassword();
        if ("@".equals(password)) {
            return;
        }
        account.setPasswordHash(password);
    }

    public static void changeCardPass() {
        String temp, cardPass = "";
        Command command;
        while (cardPass.isEmpty()) {
            Print.input("Enter password:");
            temp = syInput.nextLine();
            command = Input.checkLine(temp);
            if (command == Command.BACK) {
                return;

            } else if (Input.isNumber(temp) && temp.length() == 4) {
                cardPass = temp;
            } else {
                Print.erorr("invalid pass");
            }
        }
        account.changeCardPass(cardPass);
    }
}
