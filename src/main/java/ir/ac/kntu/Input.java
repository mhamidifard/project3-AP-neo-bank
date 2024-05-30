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
                syInput.close();
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
        System.out.println("@:back  #quit:quit");
    }

    public static void userType() {
        int choice = -1;
        String temp;
        while (true) {
            System.out.println("select user type\n1.user\n2.support");
            System.out.println("#quit");
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
        int choice = -1;
        String temp;
        while (true) {
            System.out.println("1.Login\n2.Register");
            printBottom();
            temp = syInput.nextLine();
            Command command = checkLine(temp);
            if (command == Command.NOTHING && isNumber(temp)) {
                choice = Integer.parseInt(temp);
                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        register();
                        break;
                    default:
                        choice = -1;
                        System.out.println("invalid choise");
                        break;
                }
            } else if (command == Command.BACK) {
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
            System.out.println("user login\nEnter phone number:");
            temp = syInput.nextLine();
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
            temp = syInput.nextLine();
            command = checkLine(temp);
            if (command == Command.NOTHING) {
                password = temp;

            } else if (command == Command.BACK) {
                //goUser();
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
        String nationalId = "", firstName = "", lastName = "", password = "";
        long phoneNumber = 0;
        System.out.println("register");
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

        DataBase.addUser(firstName, lastName, phoneNumber, nationalId, password);
        DataBase.addVerifyReq(phoneNumber);
        System.out.println("Register successfully");
        //userType();
    }

    public static void userAccount(Account account) {

    }

    public static String defineId(Account allowedAcc) {
        String temp, nationalId = "";
        Command command;
        while (nationalId.isEmpty()) {
            System.out.println("Enter id:");
            temp = syInput.nextLine();
            command = checkLine(temp);
            if (command == Command.BACK) {
                return "@";

            } else if (!isNumber(temp)) {
                System.out.println("invalid id");

            } else if (DataBase.findById(temp) == null || DataBase.findById(temp) == allowedAcc) {
                nationalId = temp;
            } else {
                System.out.println("Duplicate id");
            }
        }
        return nationalId;
    }

    public static long definePhone(Account allowedAcc) {
        String temp;
        Command command;
        long phoneNumber = 0;
        while (phoneNumber == 0) {
            System.out.println("Enter phone number:");
            temp = syInput.nextLine();
            command = checkLine(temp);
            if (command == Command.BACK) {
                return 0;

            } else if (!isNumber(temp)) {
                System.out.println("invalid phone number");

            } else if (DataBase.findByPhone(Long.parseLong(temp)) == null || DataBase.findByPhone(Long.parseLong(temp)) == allowedAcc) {
                phoneNumber = Long.parseLong(temp);
            } else {
                System.out.println("Duplicate Phone number");
            }
        }
        return phoneNumber;
    }

    public static String defineFistName() {
        String temp, firstName = "";
        Command command;
        while (firstName.isEmpty()) {
            System.out.println("Enter first name:");
            temp = syInput.nextLine();
            command = checkLine(temp);
            temp = temp.strip().toLowerCase();
            if (command == Command.BACK) {
                return "@";
            } else if (!isLetter(temp)) {
                System.out.println("invalid first name");
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
            System.out.println("Enter last name:");
            temp = syInput.nextLine();
            command = checkLine(temp);
            temp = temp.strip().toLowerCase();
            if (command == Command.BACK) {
                return "@";

            } else if (!isLetter(temp)) {
                System.out.println("invalid last name");
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
            System.out.println("Enter password:");
            temp = syInput.nextLine();
            command = checkLine(temp);
            if (command == Command.BACK) {
                return "@";

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
        String temp, password = "", userName = "";
        Command command;
        boolean loop = true;
        while (userName.isEmpty()) {
            System.out.println("support login\nEnter username:");
            temp = syInput.nextLine();
            command = checkLine(temp);
            if (command == Command.NOTHING) {
                userName = temp;
                support = DataBase.suppFind(userName);
                if (support == null) {
                    System.out.println("Wrong user name");
                    userName = "";
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
            temp = syInput.nextLine();
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
                SupportInput.menu(support);
                return;
            } else {
                System.out.println("Wrong password");
            }
        }
    }

    public static void checkUserVr(Account account) {
        boolean supportchecked = true;

        VerificationRequest verifyReq = DataBase.findVerifyReq(account.getPhoneNumber());
        if (verifyReq != null) {
            supportchecked = verifyReq.isSupportChecked();
        }
        if (account.isVerifyStatus()) {
            System.out.println("hello menu");
            UserInput.menu(account);
            return;
        } else if (!supportchecked) {
            String temp;
            System.out.println("hi");
            System.out.println(verifyReq.getMessage());
            while (true) {
                Input.printBottom();
                temp = syInput.nextLine();
                if (Input.checkLine(temp) == Command.BACK) {
                    //goUser();
                    return;
                } else {
                    System.out.println("invalid choice");
                }
            }
        } else {
            System.out.println(verifyReq.getMessage());
            String temp;
            int choice = 0;
            while (choice == 0) {
                System.out.println("1.editRegister");
                Input.printBottom();
                temp = syInput.nextLine();
                if (Input.checkLine(temp) == Command.BACK) {
                    //goUser();
                    return;
                } else if (!Input.isNumber(temp)) {
                    System.out.println("invalid choice");
                } else {
                    choice = Integer.parseInt(temp);
                    if (choice == 1) {
                        editRegister(account);
                        return;
                    } else {
                        choice = 0;
                        System.out.println("invalid choice");
                    }
                }

            }
        }
    }

    public static void editRegister(Account account) {
        String nationalId = "", firstName = "", lastName = "", password = "";
        long phoneNumber = 0;
        System.out.println("edit register");
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
        lastName = defineLastName();
        if ("@".equals(lastName)) {
            return;
        }
        password = definePassword();
        if ("@".equals(password)) {
            return;
        }
        long lastPhoneNumber = account.getPhoneNumber();
        account.setNationalId(nationalId);
        account.setPhoneNumber(phoneNumber);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setPasswordHash(password);
        DataBase.renewVerifyReq(lastPhoneNumber, phoneNumber);
        System.out.println("edit register successfully");
        //userType();
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
