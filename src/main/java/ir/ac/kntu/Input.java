package ir.ac.kntu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum Command {
    FRONT, REAR, BACK, QUITE, NOTHING;
}

public class Input {
    public static Scanner in;

    public static void first() {
        in = new Scanner(System.in);
        userType();
    }

    public static Command checkLine(String temp) {
        switch (temp) {
            case ">":
                return Command.FRONT;
            case "<":
                return Command.REAR;
            case "@":
                return Command.BACK;
            case "#quit":
                in.close();
                System.exit(0);
                return Command.QUITE;
            default:
                return Command.NOTHING;
        }
    }

    public static boolean isNumber(String temp) {
        Pattern pNumber = Pattern.compile("\\d+");
        Matcher mNumber = pNumber.matcher(temp);
        return mNumber.matches();
    }

    public static boolean isLetter(String temp) {
        Pattern pLetter = Pattern.compile("[a-z ]+");
        Matcher mLetter = pLetter.matcher(temp);
        return mLetter.matches();
    }

    public static boolean isStrongPass(String temp) {
        Pattern pSpace = Pattern.compile("\\S+");
        Pattern pNUmber = Pattern.compile("\\d");
        Pattern pLowLetter = Pattern.compile("[a-z]");
        Pattern pUpLetter = Pattern.compile("[A-Z]");
        Pattern pSpecial = Pattern.compile("[~!@#$%^&*()_+|`}{\"':;?/>.<\\\\,\\-]");

        Matcher matcher = pSpace.matcher(temp);
        if (!matcher.matches()) {
            return false;
        }
        if (temp.length() < 8) {
            return false;
        }
        matcher = pNUmber.matcher(temp);
        if (!matcher.find()) {
            return false;
        }
        matcher = pLowLetter.matcher(temp);
        if (!matcher.find()) {
            return false;
        }
        matcher = pUpLetter.matcher(temp);
        if (!matcher.find()) {
            return false;
        }
        matcher = pSpecial.matcher(temp);
        if (!matcher.find()) {
            return false;
        }

        return true;
    }

    public static void printBottom() {
        System.out.println("@:back  #quit:quit");
    }

    public static void userType() {
        int choice = -1;
        String temp;
        while (true) {
            System.out.println("select user type\n1.user\n2.support");
            temp = in.nextLine();
            if (checkLine(temp) == Command.NOTHING && isNumber(temp)) {
                choice = Integer.parseInt(temp);
                switch (choice) {
                    case 1:
                        goUser();
                    case 2:
                        goSupport();
                    default:
                        choice = -1;
                        System.out.println("invalid choise");
                        break;
                }
            } else {
                System.out.println("invalid input");
            }
        }

    }

    public static void goUser() {
        int choice = 0;
        String temp;
        while (choice!=-1) {
            System.out.println("1.Login\n2.Register");
            printBottom();
            temp = in.nextLine();
            Command command = checkLine(temp);
            if (command == Command.NOTHING && isNumber(temp)) {
                choice = Integer.parseInt(temp);
                switch (choice) {
                    case 1:
                        login();
                    case 2:
                        register();
                    default:
                        choice = -1;
                        System.out.println("invalid choise");
                        return;
                }
            } else if (command == Command.BACK) {
                choice=-1;
                //userType();
                return;
            } else {
                System.out.println("invalid input");
            }
        }

    }

    public static void login() {
        Account account = null;
        String temp, password = "";
        Command command;
        boolean loop = true;
        long phoneNumber = 0;
        while (phoneNumber == 0) {
            System.out.println("Enter phone number:");
            temp = in.nextLine();
            command = checkLine(temp);
            if (command == Command.NOTHING && isNumber(temp)) {
                phoneNumber = Long.parseLong(temp);
                account = DataBase.findByPhone(phoneNumber);
                if (account == null) {
                    phoneNumber = 0;
                    System.out.println("Wrong phoneNumber");
                }

            } else if (command == Command.BACK) {
                //goUser();
                return;

            } else {
                System.out.println("invalid input");
            }
        }

        while (loop) {
            System.out.println("Enter password:");
            temp = in.nextLine();
            command = checkLine(temp);
            if (command == Command.NOTHING) {
                password = temp;

            } else if (command == Command.BACK) {
                goUser();
                return;

            } else {
                System.out.println("invalid input");
            }

            if (account.passwordEqual(password)) {
                loop = false;
                System.out.println("Login successfully");
                checkUserVr(account);
                return;
            } else {
                System.out.println("Wrong password");
            }
        }
    }

    public static void register() {
        String temp, nationalId = "", firstName = "", lastName = "", password = "";
        Command command;
        long phoneNumber = 0;

        nationalId=defineId(null);

        phoneNumber=definePhone(null);

        firstName=defineFistName();

        lastName=defineLastName();

        password=definePassword();

        DataBase.addUser(firstName, lastName, phoneNumber, nationalId, password);
        DataBase.addVerifyReq(phoneNumber);
        System.out.println("Register successfully");
    }

    public static void userAccount(Account account) {

    }

    public static String defineId(Account allowedAcc){
        String temp, nationalId = "";
        Command command;
        while (nationalId.isEmpty()) {
            System.out.println("Enter id:");
            temp = in.nextLine();
            command = checkLine(temp);
            if (command == Command.BACK) {
                goUser();

            } else if (!isNumber(temp)) {
                System.out.println("invalid id");

            } else if (DataBase.findById(temp) == null || DataBase.findById(temp)==allowedAcc) {
                nationalId = temp;
            } else {
                System.out.println("Duplicate id");
            }
        }
        return nationalId;
    }

    public static long definePhone(Account allowedAcc){
        String temp;
        Command command;
        long phoneNumber = 0;
        while (phoneNumber == 0) {
            System.out.println("Enter phone number:");
            temp = in.nextLine();
            command = checkLine(temp);
            if (command == Command.BACK) {
                goUser();

            } else if (!isNumber(temp)) {
                System.out.println("invalid phone number");

            } else if (DataBase.findByPhone(Long.parseLong(temp)) == null || DataBase.findByPhone(Long.parseLong(temp))==allowedAcc) {
                phoneNumber = Long.parseLong(temp);
            } else {
                System.out.println("Duplicate Phone number");
            }
        }
        return phoneNumber;
    }

    public static String defineFistName(){
        String temp,  firstName = "";
        Command command;
        while (firstName.isEmpty()) {
            System.out.println("Enter first name:");
            temp = in.nextLine();
            command = checkLine(temp);
            temp = temp.strip().toLowerCase();
            if (command == Command.BACK) {
                goUser();

            } else if (!isLetter(temp)) {
                System.out.println("invalid first name");
            } else {
                firstName = temp;
            }
        }
        return firstName;
    }

    public static String defineLastName(){
        String temp,  lastName = "";
        Command command;
        while (lastName.isEmpty()) {
            System.out.println("Enter last name:");
            temp = in.nextLine();
            command = checkLine(temp);
            temp = temp.strip().toLowerCase();
            if (command == Command.BACK) {
                goUser();

            } else if (!isLetter(temp)) {
                System.out.println("invalid last name");
            } else {
                lastName = temp;
            }
        }
        return lastName;
    }

    public static String definePassword(){
        String temp, password = "";
        Command command;
        while (password.isEmpty()) {
            System.out.println("Enter password:");
            temp = in.nextLine();
            command = checkLine(temp);
            if (command == Command.BACK) {
                goUser();

            } else if (!isStrongPass(temp)) {
                System.out.println("weak password");
            } else {
                password = temp;
            }
        }
        return password;
    }

    public static void goSupport() {
        Support support = null;
        String temp, password = "",userName="";
        Command command;
        boolean loop = true;
        while (userName.isEmpty()) {
            System.out.println("Enter username:");
            temp = in.nextLine();
            command = checkLine(temp);
            if (command == Command.NOTHING ) {
                userName=temp;
                support = DataBase.suppFind(userName);
                if (support == null) {
                    System.out.println("Wrong user name");
                    userName="";
                }

            } else if (command == Command.BACK) {
                //userType();
                return;

            } else {
                System.out.println("invalid input");
            }
        }

        while (loop) {
            System.out.println("Enter password:");
            temp = in.nextLine();
            command = checkLine(temp);
            if (command == Command.NOTHING) {
                password = temp;

            } else if (command == Command.BACK) {
                //userType();
                return;

            } else {
                System.out.println("invalid input");
            }
            if (support.passwordEqual(password)) {
                loop = false;
                System.out.println("Login successfully");
                SupportInput.menu(support,in);
                return;
            } else {
                System.out.println("Wrong password");
            }
        }
    }

    public static void checkUserVr(Account account) {
        if (account.isVerifyStatus()) {

        } else if (!account.isSupportChecked()) {
            String temp;
            System.out.println(DataBase.findVerifyReq(account.getPhoneNumber()).getMessage());
            while (true){
                Input.printBottom();
                temp = in.nextLine();
                if (Input.checkLine(temp) == Command.BACK) {
                    //goUser();
                    return;
                }else {
                    System.out.println("invalid choice");
                }
            }
        } else {
            String temp;
            int choice = 0;
            while (choice == 0) {
                System.out.println("1.editRegister");
                Input.printBottom();
                temp = in.nextLine();
                if (Input.checkLine(temp) == Command.BACK) {
                    //goUser();
                    return;
                } else if (!Input.isNumber(temp)) {
                    System.out.println("invalid choice");
                } else {
                    choice = Integer.parseInt(temp);
                    if (choice == 1) {
                        editRegister(account);
                    }
                    else {
                        choice=0;
                        System.out.println("invalid choice");
                    }
                }

            }
        }
    }


    public static void editRegister(Account account){
        String temp, nationalId = "", firstName = "", lastName = "", password = "";
        Command command;
        long phoneNumber = 0;

        nationalId=defineId(null);

        phoneNumber=definePhone(null);

        firstName=defineFistName();

        lastName=defineLastName();

        password=definePassword();
        account.setNationalId(nationalId);
        account.setPhoneNumber(phoneNumber);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setPasswordHash(password);
        DataBase.renewVerifyReq(account.getPhoneNumber(),phoneNumber);
        System.out.println("edit register successfully");
    }

}
