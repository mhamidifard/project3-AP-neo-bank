package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserSupport {
    private static Account account;
    private static Scanner syInput;

    public static void setSyInput(Scanner syInput) {
        UserSupport.syInput = syInput;
    }

    public static void menu(Account account1) {
        account = account1;
        int choice = 0;
        while (true) {
            Print.info("Support section");
            Print.menu("1.add request\n2.list of requests\n");
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
                    Print.erorr("invalid choice");
                    break;
            }

        }
    }

    public static Subject choiceSubject() {
        int choice = 0;
        while (true) {
            Print.info("select subject");
            Print.menu("1.report\n2.contacts\n3.transfer\n4.setting");
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
                    Print.erorr("invalid choice");
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
            Print.input("Enter title:");
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
            Print.input("Enter message:");
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
        Print.info("Message sent successfully");
    }

    public static void listRequest() {
        int choice;
        int size = account.getSupportRequests().size();
        SupportRequest request;
        List<SupportRequest> requests;
        while (true) {
            requests = new ArrayList<>();
            Print.info("list requests");

            for (int i = size - 1; i >= 0; i--) {
                request = DataBase.findSuppReq(account.getSupportRequests().get(i));
                Print.list(size - i + "." + request.getTitle());
                requests.add(request);
            }
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            if (0 < choice && choice <= size) {
                goRequest(requests.get(choice - 1));
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static void goRequest(SupportRequest request) {
        Print.info(request.toString());
        int choice = 0;
        while (true) {
            Print.menu("1.add message");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            } else if (choice == 1) {
                inAddMessage(request);
                return;
            } else {
                Print.erorr("invalid choice");
            }
        }
    }
}
