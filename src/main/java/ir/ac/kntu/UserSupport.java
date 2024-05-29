package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserSupport {
    private static Account account;
    private static Scanner syInput;

    public static void menu(Account account1, Scanner syInput1) {
        account = account1;
        syInput = syInput1;
        int choice = 0;
        while (true) {
            System.out.println("Support section");
            System.out.println("1.add request\n2.list of requests\n");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    inAddRequest();
                    break;
                case 2:
                    listRequest();
                    break;

                default:
                    System.out.println("invalid choice");
                    break;
            }

        }
    }

    public static Subject choiceSubject() {
        int choice = 0;
        while (true) {
            System.out.println("select subject");
            System.out.println("1.report\n2.contacts\n3.transfer\n4.setting");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return null;
            }
            switch (choice) {
                case 1:
                    return Subject.REPORT;
                case 2:
                    return Subject.CONTACTS;
                case 3:
                    return Subject.TRANSFER;
                case 4:
                    return Subject.SETTING;

                default:
                    System.out.println("invalid choice");
                    break;
            }

        }
    }

    public static void inAddRequest() {
        String temp, title = "";
        Command command;
        Subject subject = choiceSubject();
        if (subject == null) {
            return;
        }
        while (title.isEmpty()) {
            System.out.println("Enter title:");
            temp = syInput.nextLine();
            command = Input.checkLine(temp);
            temp = temp.strip().toLowerCase();
            if (command == Command.BACK) {
                return;
            } else {
                title = temp;
            }
        }
        SupportRequest request = DataBase.addSupportReq(title, account.getPhoneNumber(), subject);
        account.addSuppReq(request.getNavId());
        inAddMessage(request);
    }

    public static void inAddMessage(SupportRequest request) {
        String temp, text = "";
        Command command;
        while (text.isEmpty()) {
            System.out.println("Enter message:");
            temp = syInput.nextLine();
            command = Input.checkLine(temp);
            temp = temp.strip().toLowerCase();
            if (command == Command.BACK) {
                return;
            } else {
                text = temp;
            }
        }
        request.addMessage(text, Sender.USER);
        System.out.println("Message sent successfully");
    }

    public static void listRequest() {
        int choice;
        int size = account.getSupportRequests().size();
        SupportRequest request;
        List<SupportRequest> requests;
        while (true) {
            requests = new ArrayList<>();
            System.out.println("list requests");

            for (int i = size - 1; i >= 0; i--) {
                request = DataBase.findSuppReq(account.getSupportRequests().get(i));
                System.out.println(size - i + "." + request.getTitle());
                requests.add(request);
            }
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            if (0 < choice && choice <= size) {
                goRequest(requests.get(choice - 1));
            } else {
                System.out.println("invalid choice");
            }
        }
    }

    public static void goRequest(SupportRequest request) {
        System.out.println(request);
        int choice = 0;
        while (true) {
            System.out.println("1.add message");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            } else if (choice == 1) {
                inAddMessage(request);
                return;
            } else {
                System.out.println("invalid choice");
            }
        }
    }
}
