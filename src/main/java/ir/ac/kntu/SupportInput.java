package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SupportInput {
    private static Scanner syInput;
    private static Support support;

    public static void menu(Support support1, Scanner systemIn) {
        support = support1;
        syInput = systemIn;
        String temp;

        int choice = 0;
        while (true) {
            System.out.println("1.verify\n2.requests");
            Input.printBottom();
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                //Input.goSupport();
                choice = -1;
                return;
            } else if (!Input.isNumber(temp)) {
                System.out.println("invalid choice");
            } else {
                choice = Integer.parseInt(temp);
                switch (choice) {
                    case 1:
                        verifylist();
                        break;
                    case 2:
                        inFilterReq();
                        break;
                    default:
                        choice = 0;
                        System.out.println("invalid choice");
                        break;
                }
            }
        }
    }

    public static void verifylist() {
        String temp;
        int choice = 0;
        while (true) {
            System.out.println("requests list");
            for (int i = 0; i < DataBase.getVerifyRequests().size(); i++) {
                System.out.println(i + 1 + " : " + DataBase.getVerifyRequests().get(i).summery());
            }
            Input.printBottom();
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                //menu(support,syInput);
                return;
            } else if (!Input.isNumber(temp)) {
                System.out.println("invalid choice");
            } else {
                choice = Integer.parseInt(temp);
                if (0 < choice && choice <= DataBase.getVerifyRequests().size()) {
                    checkVerify(DataBase.getVerifyRequests().get(choice - 1));
                } else {
                    choice = 0;
                    System.out.println("invalid choice");
                }
            }
        }
    }

    public static void checkVerify(VerificationRequest verifyReq) {

        String temp;
        int choice = 0;
        while (choice == 0) {
            System.out.println(verifyReq.toString());
            System.out.println("1.accept\n2.reject");
            Input.printBottom();
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                //verifylist();
                return;
            } else if (!Input.isNumber(temp)) {
                System.out.println("invalid choice");
            } else {
                choice = Integer.parseInt(temp);
                if (choice == 1) {
                    verifyReq.accept();
                    //verifylist();
                    return;
                } else if (choice == 2) {
                    System.out.println("Enter the message");
                    Input.printBottom();
                    temp = syInput.nextLine();
                    if (Input.checkLine(temp) == Command.BACK) {
                        //verifylist();
                        return;
                    }
                    verifyReq.reject(temp);
                    //verifylist();
                    return;
                } else {
                    choice = 0;
                    System.out.println("invalid choice");
                }
            }
        }
    }

    public static void inFilterReq() {
        long phoneNumber = inPhoneFilter();
        if (phoneNumber == -1) {
            return;
        }
        Subject subject = inSubjectFilter();
        if (subject == null) {
            return;
        }
        Status status = inStatusFilter();
        if (status == null) {
            return;
        }
        requestList(phoneNumber, status, subject);

    }

    public static long inPhoneFilter() {
        long phoneNumber = 0;
        while (true) {
            System.out.println("Enter phone number: (if you dont want filter by phone inter 0)");
            phoneNumber = UserInput.simpleLong();
            if (phoneNumber == -1) {
                return -1;
            }
            if (phoneNumber == 0) {
                return 0;
            } else if (DataBase.findByPhone(phoneNumber) == null) {
                System.out.println("there is not this phone number ");

            } else {
                return phoneNumber;
            }
        }
    }

    public static Subject inSubjectFilter() {
        int choice = 0;
        while (true) {
            System.out.println("select subject");
            System.out.println("1.report\n2.contacts\n3.transfer\n4.setting\n5.without filter");
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
                case 5:
                    return Subject.NOTHING;
                default:
                    System.out.println("invalid choice");
                    break;
            }

        }
    }

    public static Status inStatusFilter() {
        int choice = 0;
        while (true) {
            System.out.println("select status");
            System.out.println("1.created\n2.process\n3.closed\n4.without filter");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return null;
            }
            switch (choice) {
                case 1:
                    return Status.CREATED;
                case 2:
                    return Status.PROCESS;
                case 3:
                    return Status.CLOSED;
                case 4:
                    return Status.NOTHING;
                default:
                    System.out.println("invalid choice");
                    break;
            }

        }
    }

    public static void requestList(long userPhone, Status status, Subject subject) {
        int choice;
        int size = DataBase.getSupportRequests().size();
        SupportRequest request;
        List<SupportRequest> requests;
        while (true) {
            requests = new ArrayList<>();
            System.out.println("list requests");
            long navId = SupportRequest.firstId;
            int counter = 1;
            for (int i = size - 1; i >= 0; i--) {
                request = DataBase.findSuppReq(navId + i);
                if (checkRequest(request, userPhone, status, subject)) {
                    System.out.println(counter + "." + request.summry());
                    requests.add(request);
                    counter++;
                }
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

    public static boolean checkRequest(SupportRequest request, long userPhone, Status status, Subject subject) {
        if (userPhone != 0 && request.getUserPhone() != userPhone) {
            return false;
        }
        if (status != Status.NOTHING && request.getStatus() != status) {
            return false;
        }
        return subject == Subject.NOTHING || request.getSubject() == subject;
    }

    public static void goRequest(SupportRequest request) {
        //System.out.println(request);
        int choice = 0;
        while (true) {
            System.out.println(request);
            System.out.println("1.add message\n2.set status");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    inAddMessage(request);
                    break;
                case 2:
                    setStatusReq(request);
                    break;
                default:
                    System.out.println("invalid choice");
                    break;
            }

        }
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
        request.addMessage(text, Sender.SUPPORT);
        System.out.println("Message sent successfully");
    }

    public static void setStatusReq(SupportRequest request) {
        int choice = 0;
        while (true) {
            System.out.println("select status");
            System.out.println("1.created\n2.process\n3.closed");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    request.setStatus(Status.CREATED);
                    return;
                case 2:
                    request.setStatus(Status.PROCESS);
                    return;
                case 3:
                    request.setStatus(Status.CLOSED);
                    return;
                default:
                    System.out.println("invalid choice");
                    break;
            }

        }
    }
}
