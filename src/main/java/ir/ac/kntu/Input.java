package ir.ac.kntu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum Command {
    FRONT, REAR, BACK, QUITE, NOTHING;
}

public class Input {
    private static Scanner syInput;

    public static Scanner getSyInput() {
        return syInput;
    }

    public static void first() {
        syInput = new Scanner(System.in);
        SupportInput.setSyInput(syInput);
        UserInput.setSyInput(syInput);
        UserSupport.setSyInput(syInput);
        UserSetting.setSyInput(syInput);
        AdminInput.setSyInput(syInput);
        while (true){
            try {
                userType();
            }catch (Exception h){
                Print.erorr("Unknown error");
                h.printStackTrace();
            }
        }

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
                syInput.close();
                System.exit(0);
                return Command.QUITE;
            default:
                return Command.NOTHING;
        }
    }

    public static String simpleString() {
        String temp;
        while (true) {
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                return "@";
            } else {
                return temp;
            }
        }
    }

    public static boolean isNumber(String temp) {
        Pattern pNumber = Pattern.compile("\\d+");
        Matcher mNumber = pNumber.matcher(temp);
        return mNumber.matches();
    }

    public static boolean isDouble(String temp) {
        Pattern pNumber = Pattern.compile("\\d+(\\.\\d+)?");
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
        return matcher.find();
    }

    public static void printBottom() {
        Print.bottom("@:back  #quit:quit");
    }

    public static void userType() {
        int choice = -1;
        String temp;
        while (true) {
            Print.menu("select user type\n1.user\n2.support\n3.admin");
            Print.bottom("#quit");
            temp = syInput.nextLine();
            if (checkLine(temp) == Command.NOTHING && isNumber(temp)) {
                choice = Integer.parseInt(temp);
                switch (choice) {
                    case 1:
                        goUser();
                        break;
                    case 2:
                        goSupport();
                        break;
                    case 3:
                        AdminInput.goAdmin();
                        break;
                    default:
                        Print.erorr("invalid choise");
                        break;
                }
            } else {
                Print.erorr("invalid input");
            }
        }
    }

    public static void goUser() {
        int choice = 0;
        while (true) {
            Print.menu("1.Login\n2.Register");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    loginPhone();
                    break;
                case 2:
                    register();
                    break;
                default:
                    choice = -1;
                    Print.erorr("invalid choise");
                    break;
            }
        }

    }

    public static void loginPhone() {
        Account account = null;
        long phoneNumber = 0;
        while (phoneNumber == 0) {
            Print.input("user login\nEnter phone number:");
            phoneNumber = UserInput.simpleLong();
            if (phoneNumber == -1) {
                return;
            }
            account = DataBase.findByPhone(phoneNumber);
            if (account == null) {
                phoneNumber = 0;
                Print.erorr("Wrong phoneNumber");
            }

        }
        loginPassword(account);
    }

    public static void loginPassword(Account account) {
        boolean loop = true;
        String password = "";
        while (loop) {
            Print.input("Enter password:");
            password = simpleString();
            if ("@".equals(password)) {
                return;
            } else if (account.passwordEqual(password)) {
                loop = false;
                Print.info("Login successfully");
                checkUserVr(account);
                return;
            } else {
                Print.erorr("Wrong password");
            }
        }
    }

    public static void register() {
        String nationalId = "", firstName = "", lastName = "", password = "";
        long phoneNumber = 0;
        Print.input("register");
        nationalId = defineId(null);
        if ("@".equals(nationalId)) {
            return;
        }
        phoneNumber = definePhone(null);
        if (phoneNumber == 0) {
            return;
        }
        firstName = defineFistName();
        if ("@".equals(firstName)) {
            return;
        }
        lastName = defineLastName();
        if ("@".equals(lastName)) {
            return;
        }
        password = definePassword();
        if ("@".equals(password)) {
            return;
        }
        Account newAccount=new Account(firstName, lastName, phoneNumber, nationalId, password);
        DataBase.addUser(newAccount);
        //DataBase.addVerifyReq(phoneNumber);
        newAccount.setVerifyReq(DataBase.addSuppVerify(phoneNumber));
        newAccount.addSuppReq(newAccount.getVerifyReq());
        Print.info("Register successfully");
        //userType();
    }

    public static String defineId(Account allowedAcc) {
        String temp, nationalId = "";
        Command command;
        while (nationalId.isEmpty()) {
            Print.input("Enter id:");
            temp = syInput.nextLine();
            command = checkLine(temp);
            if (command == Command.BACK) {
                return "@";

            } else if (!isNumber(temp)) {
                Print.erorr("invalid id");

            } else if (DataBase.findById(temp) == null || DataBase.findById(temp) == allowedAcc) {
                nationalId = temp;
            } else {
                Print.erorr("Duplicate id");
            }
        }
        return nationalId;
    }

    public static long definePhone(Account allowedAcc) {
        String temp;
        Command command;
        long phoneNumber = 0;
        while (phoneNumber == 0) {
            Print.input("Enter phone number:");
            temp = syInput.nextLine();
            command = checkLine(temp);
            if (command == Command.BACK) {
                return 0;

            } else if (!isNumber(temp)) {
                Print.erorr("invalid phone number");

            } else if (DataBase.findByPhone(Long.parseLong(temp)) == null || DataBase.findByPhone(Long.parseLong(temp)) == allowedAcc) {
                phoneNumber = Long.parseLong(temp);
            } else {
                Print.erorr("Duplicate Phone number");
            }
        }
        return phoneNumber;
    }

    public static String defineFistName() {
        String temp, firstName = "";
        Command command;
        while (firstName.isEmpty()) {
            Print.input("Enter first name:");
            temp = syInput.nextLine();
            command = checkLine(temp);
            temp = temp.strip().toLowerCase();
            if (command == Command.BACK) {
                return "@";
            } else if (!isLetter(temp)) {
                Print.erorr("invalid first name");
            } else {
                firstName = temp;
            }
        }
        return firstName;
    }

    public static String defineLastName() {
        String temp, lastName = "";
        Command command;
        while (lastName.isEmpty()) {
            Print.input("Enter last name:");
            temp = syInput.nextLine();
            command = checkLine(temp);
            temp = temp.strip().toLowerCase();
            if (command == Command.BACK) {
                return "@";

            } else if (!isLetter(temp)) {
                Print.erorr("invalid last name");
            } else {
                lastName = temp;
            }
        }
        return lastName;
    }

    public static String definePassword() {
        String temp, password = "";
        Command command;
        while (password.isEmpty()) {
            Print.input("Enter password:");
            temp = syInput.nextLine();
            command = checkLine(temp);
            if (command == Command.BACK) {
                return "@";

            } else if (!isStrongPass(temp)) {
                Print.erorr("weak password");
            } else {
                password = temp;
            }
        }
        return password;
    }

    public static void goSupport() {
        Support support = null;
        String userName = "";
        while (userName.isEmpty()) {
            Print.input("support login\nEnter username:");
            userName = simpleString();
            if ("@".equals(userName)) {
                return;
            }
            support = DataBase.suppFind(userName);
            if (support == null) {
                Print.erorr("Wrong user name");
                userName = "";
            }
        }
        if(!support.isActive()){
            Print.erorr("you are block");
            return;
        }
        goSupportPass(support);
    }

    public static void goSupportPass(Support support) {
        String password;
        boolean loop = true;
        while (loop) {
            Print.input("Enter password:");
            password = simpleString();
            if ("@".equals(password)) {
                return;
            } else if (support.passwordEqual(password)) {
                loop = false;
                Print.info("Login successfully");
                SupportInput.menu(support);
                return;
            } else {
                Print.erorr("Wrong password");
            }
        }
    }

    public static void checkUserVr(Account account) {
        //VerificationRequest verifyReq = DataBase.findVerifyReq(account.getPhoneNumber());
        SupportRequest suppReq=DataBase.findSuppReq(account.getVerifyReq());
        if (account.isVerifyStatus()) {
            UserInput.menu(account);
            return;
        } else if (suppReq.getLastMessage().getSender()==Sender.USER) {
            int choice;
            Print.erorr("please wait to verify");
            while (true) {
                choice = UserInput.simpleMenu();
                if (choice == -1) {
                    return;
                } else {
                    Print.erorr("invalid choice");
                }
            }
        } else {
            reRegister(account, suppReq);
        }
    }

    public static void reRegister(Account account, SupportRequest suppReq) {
        Print.erorr(suppReq.getLastMessage().getText());
        int choice = 0;
        while (choice == 0) {
            Print.menu("1.editRegister");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            if (choice == 1) {
                editRegister(account);
                return;
            } else {
                choice = 0;
                Print.erorr("invalid choice");
            }

        }
    }

    public static void editRegister(Account account) {
        String nationalId = "", firstName = "";
        long phoneNumber = 0;
        Print.info("edit register");
        nationalId = defineId(account);
        if ("@".equals(nationalId)) {
            return;
        }
        phoneNumber = definePhone(account);
        if (phoneNumber == 0) {
            return;
        }
        firstName = defineFistName();
        if ("@".equals(firstName)) {
            return;
        }
        editRegister2(account, nationalId, firstName, phoneNumber);


    }

    public static void editRegister2(Account account, String nationalId, String firstName, long phoneNumber) {
        String lastName = "", password = "";
        lastName = defineLastName();
        if ("@".equals(lastName)) {
            return;
        }
        password = definePassword();
        if ("@".equals(password)) {
            return;
        }
        //long lastPhoneNumber = account.getPhoneNumber();
        account.setPhoneNumber(phoneNumber);
        account.setNationalId(nationalId);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setPasswordHash(password);
        //DataBase.renewVerifyReq(lastPhoneNumber, phoneNumber);
        account.setVerifyReq(DataBase.addSuppVerify(phoneNumber));
        account.addSuppReq(account.getVerifyReq());
        Print.info("edit register successfully");

    }

    public static boolean similarity(String text1, String text2) {
        for (int i = 0; i <= text1.length() - text2.length(); i++) {
            if (text1.substring(i, i + text2.length()).equals(text2)) {
                return true;
            }
        }
        return false;
    }

}
