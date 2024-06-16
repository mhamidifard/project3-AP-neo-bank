package ir.ac.kntu;

import ir.ac.kntu.util.ComprableUser;
import java.time.Instant;
import java.util.*;
enum Filter {
    PHONE, FIRSTNAME, LASTNAME;
}
public class SupportInput {
    private static Scanner syInput;
    private static Support support;

    public static void setSyInput(Scanner syInput) {
        SupportInput.syInput = syInput;
    }

    public static void menu(Support support1) {
        support=support1;
        int choice = 0;
        while (true) {
            Print.info(support.summery()+"\n"+support.subToString());
            Print.menu("1.verify\n2.requests\n3.list of users");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }

            switch (choice) {
                case 1:
                    //verifylist();
                    break;
                case 2:
                    inFilterReq();
                    break;
                case 3:
                    inFilterUser();
                    break;
                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }


    public static void checkVerify(SupportRequest suppVerify) {
        int choice = 0;
        Print.info(suppVerify.toString());
        if (suppVerify.getStatus() == Status.CREATED) {
            desideVerify(suppVerify);
        } else {
            while (choice == 0) {
                choice = UserInput.simpleMenu();
                if (choice == -1) {
                    return;
                } else {
                    Print.erorr("invalid choice");
                }
            }
        }
    }

    public static void desideVerify(SupportRequest suppVerify) {
        String temp;
        while (true) {
            Print.menu("1.accept\n2.reject");
            int choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            if (choice == 1) {
                suppVerify.accept();
                return;
            } else if (choice == 2) {
                Print.input("Enter the message");
                temp = Input.simpleString();
                if ("@".equals(temp)) {
                    return;
                }
                suppVerify.reject(temp);
                return;
            } else {
                Print.erorr("invalid choice");
            }
        }

    }

    public static void inFilterReq() {
        long phoneNumber = inPhoneFilter();
        if (phoneNumber == -1) {
            return;
        }
        Subject subject = inSubjectFilter2();
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
            Print.input("Enter phone number: (if you dont want filter by phone inter 1)");
            phoneNumber = UserInput.simpleLong();
            if (phoneNumber == -1) {
                return -1;
            }
            if (phoneNumber == 1) {
                return 0;
            } else if (DataBase.findByPhone(phoneNumber) == null) {
                Print.erorr("there is not this phone number ");

            } else {
                return phoneNumber;
            }
        }
    }

    public static Status inStatusFilter() {
        int choice = 0;
        while (true) {
            Print.info("select status");
            Print.menu("0.without filter\n1.created\n2.process\n3.closed\n4.verified");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return null;
            }
            switch (choice) {
                case 0:
                    return Status.NOTHING;
                case 1:
                    return Status.CREATED;
                case 2:
                    return Status.PROCESS;
                case 3:
                    return Status.CLOSED;
                case 4:
                    return Status.VERIFIED;
                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static Subject inSubjectFilter2(){
        int choice;
        Print.info("select status");
        List<Subject> choices=new ArrayList<>();
        for (Map.Entry<Subject,Boolean> subject:support.getSubjects().entrySet()){
            if(subject.getValue()){
                choices.add(subject.getKey());
            }
        }
        while (true) {
            Print.list("0.without filter");
            for (int i = 1; i <= choices.size(); i++) {
                Print.list(i + "." + choices.get(i - 1));
            }
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return null;
            }else if(choice==0){
                return Subject.NOTHING;
            }else if (0 < choice && choice <= choices.size()) {
                return choices.get(choice-1);
            } else {
                Print.erorr("invalid choice");
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
            Print.info("list requests");
            long navId = SupportRequest.firstId;
            int counter = 1;
            for (int i = size - 1; i >= 0; i--) {
                request = DataBase.findSuppReq(navId + i);
                if (checkRequest(request, userPhone, status, subject)) {
                    Print.list(counter + "." + request.summry());
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
                Print.erorr("invalid choice");
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
        if(!support.getSubjects().get(request.getSubject())){
            return false;
        }
        return subject == Subject.NOTHING || request.getSubject() == subject;
    }

    public static void goRequest(SupportRequest request) {
        //System.out.println(request);
        int choice = 0;
        while (true) {
            if (request.getSubject() == Subject.VERIFY) {
                checkVerify(request);
                return;
            } else {
                Print.info(request.toString());
                Print.menu("1.add message\n2.set status");
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
                        Print.erorr("invalid choice");
                        break;
                }
            }

        }
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
        request.addMessage(text, Sender.SUPPORT);
        Print.info("Message sent successfully");
    }

    public static void setStatusReq(SupportRequest request) {
        int choice = 0;
        while (true) {
            Print.info("select status");
            Print.menu("1.created\n2.process\n3.closed");
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
                    Print.erorr("invalid choice");
                    break;
            }

        }
    }

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
                inFirstNameFilter(filters);
            } else if (choice == 2) {
                inLastNameFilter(filters);
            } else if (choice == 3) {
                inpPhoneFilter(filters);
            } else if (choice == 4) {
                filterUser(filters);
                filters = new HashMap<>();
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static void inFirstNameFilter(Map<Filter, String> filters) {
        String firstName = Input.defineFistName();
        if ("@".equals(firstName)) {
            return;
        }
        filters.put(Filter.FIRSTNAME, firstName);
    }

    public static void inLastNameFilter(Map<Filter, String> filters) {
        String lastName = Input.defineLastName();
        if ("@".equals(lastName)) {
            return;
        }
        filters.put(Filter.LASTNAME, lastName);
    }

    public static void inpPhoneFilter(Map<Filter, String> filters) {
        String temp, phone = "";
        Command command;
        while (phone.isEmpty()) {
            Print.input("Enter phone number:");
            temp = syInput.nextLine();
            command = Input.checkLine(temp);
            temp = temp.strip();
            if (command == Command.BACK) {
                return;
            } else if (!Input.isNumber(temp)) {
                Print.erorr("invalid phone number");
            } else {
                phone = temp;
            }
        }
        filters.put(Filter.PHONE, phone);
    }

    public static void filterUser(Map<Filter, String> filters) {
        ArrayList<ComprableUser> listUser = new ArrayList<>();
        for (Account account : DataBase.getAccounts()) {
            ComprableUser user = new ComprableUser(account);
            for (HashMap.Entry<Filter, String> filter : filters.entrySet()) {
                checkUser(filter, account, user);
            }
            if (user.isCondition()) {
                listUser.add(user);
            }
        }
        listUser.sort(ComprableUser::compareTo);
        showUserList(listUser);
    }

    public static void checkUser(HashMap.Entry<Filter, String> filter, Account account, ComprableUser user) {
        switch (filter.getKey()) {
            case FIRSTNAME:
                user.addLength(account.getFirstName().length());
                if (!Input.similarity(account.getFirstName(), filter.getValue())) {
                    user.setCondition(false);
                }
                break;
            case LASTNAME:
                user.addLength(account.getLastName().length());
                if (!Input.similarity(account.getLastName(), filter.getValue())) {
                    user.setCondition(false);
                }
                break;
            case PHONE:
                if (Long.parseLong(filter.getValue()) != account.getPhoneNumber()) {
                    user.setCondition(false);
                }
                break;
            default:
                break;
        }
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
            Print.menu("\n1.list transactions\n2.filter tranactions");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    listTransactions(account, null, null);
                    break;
                case 2:
                    goFilterTra(account);
                    break;
                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void goFilterTra(Account account) {
        Instant minDate = UserInput.inFilterDate("min");
        if (minDate == null) {
            return;
        }
        Instant maxDate = UserInput.inFilterDate("max");
        if (maxDate == null) {
            return;
        }
        listTransactions(account, minDate, maxDate);
    }

    public static void listTransactions(Account account, Instant minDate, Instant maxDate) {
        int choice;
        List<Long> transactions = account.getTransactions();
        List<Long> validTransaction;
        while (true) {
            validTransaction = new ArrayList<>();
            int counter = 1;
            for (Long navId : transactions) {
                if (UserInput.filterDate(navId, minDate, maxDate)) {
                    Print.list(counter + ": " + DataBase.findTransaction(navId).summery());
                    validTransaction.add(navId);
                    counter++;
                }
            }
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            if (0 < choice && choice <= validTransaction.size()) {
                UserInput.showTransaction(validTransaction.get(choice - 1));
            } else {
                Print.erorr("invalid choice");
            }
        }
    }
}