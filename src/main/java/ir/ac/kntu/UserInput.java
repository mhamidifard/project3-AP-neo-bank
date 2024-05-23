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
        while (true) {
            System.out.println("menu\n1.manage account\n2.contacts");
            Input.printBottom();
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                //Input.goUser();
                choice = -1;
                return;
            } else if (!Input.isNumber(temp)) {
                System.out.println("invalid choice");
            } else {
                choice = Integer.parseInt(temp);
                switch (choice) {
                    case 1:
                        manageAccount();
                        break;
                    case 2:
                        goContacts();
                        break;

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
        while (true) {
            System.out.println("manage account\nbalance: " + account.getBalance());
            System.out.println("1.charge\n2.transactions");
            Input.printBottom();
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                //menu(account, syInput);
                choice = -1;
                return;
            } else if (!Input.isNumber(temp)) {
                System.out.println("invalid choice");
            } else {
                choice = Integer.parseInt(temp);
                switch (choice) {
                    case 1:
                        charge();
                        break;
                    case 2:

                        break;

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
                //manageAccount();
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
        //manageAccount();
    }

    public static void goContacts() {
        String temp;
        int choice = 0;
        while (true) {
            System.out.println("contacts");
            System.out.println("1.add contact\n2.list contacts");
            Input.printBottom();
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                choice = -1;
                return;
            } else if (!Input.isNumber(temp)) {
                System.out.println("invalid choice");
            } else {
                choice = Integer.parseInt(temp);
                switch (choice) {
                    case 1:
                        addcontact();
                        break;
                    case 2:

                        break;

                    default:
                        choice = 0;
                        System.out.println("invalid choice");
                        break;
                }
            }
        }
    }

    public static void addcontact() {
        String firstName, lastName, temp;
        Command command;
        System.out.println("add contact");
        firstName = defContactFistName();
        if (firstName.equals("@")) {
            return;
        }
        lastName = defContactLastName();
        if (lastName.equals("@")) {
            return;
        }
        long phoneNumber = 0;
        while (phoneNumber == 0) {
            System.out.println("Enter phone number:");
            temp = syInput.nextLine();
            command = Input.checkLine(temp);
            if (command == Command.NOTHING && Input.isNumber(temp)) {
                phoneNumber = Long.parseLong(temp);
                if (DataBase.findByPhone(phoneNumber) == null) {
                    System.out.println("there is not this phone number ");
                    phoneNumber = 0;
                } else if (account.containContact(phoneNumber)) {
                    System.out.println("contact already exists");
                    phoneNumber = 0;
                } else {
                    account.addContact(firstName, lastName, phoneNumber);
                }

            } else if (command == Command.BACK) {
                //goContacts();
                return;

            } else {
                System.out.println("invalid input");
            }
        }
        //goContacts();


    }

    public static String defContactFistName() {
        String temp, firstName = "";
        Command command;
        while (firstName.isEmpty()) {
            System.out.println("Enter first name:");
            temp = syInput.nextLine();
            command = Input.checkLine(temp);
            temp = temp.strip().toLowerCase();
            if (command == Command.BACK) {
                return "@";
            } else if (!Input.isLetter(temp)) {
                System.out.println("invalid first name");
            } else {
                firstName = temp;
            }
        }
        return firstName;
    }

    public static String defContactLastName() {
        String temp, lastName = "";
        Command command;
        while (lastName.isEmpty()) {
            System.out.println("Enter first name:");
            temp = syInput.nextLine();
            command = Input.checkLine(temp);
            temp = temp.strip().toLowerCase();
            if (command == Command.BACK) {
                return "@";
            } else if (!Input.isLetter(temp)) {
                System.out.println("invalid first name");
            } else {
                lastName = temp;
            }
        }
        return lastName;
    }
}
