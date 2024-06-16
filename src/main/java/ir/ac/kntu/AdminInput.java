package ir.ac.kntu;

import java.util.*;

enum AdminFilter {
    NAME, USERNAME;
}

public class AdminInput {
    private static Scanner syInput;
    private static Admin admin;

    public static void setSyInput(Scanner syInput) {
        AdminInput.syInput = syInput;
    }

    public static void goAdmin() {
        String userName = "";
        while (userName.isEmpty()) {
            Print.input("admin login\nEnter username:");
            userName = Input.simpleString();
            if ("@".equals(userName)) {
                return;
            }
            admin = DataBase.adminFind(userName);
            if (admin == null) {
                Print.erorr("Wrong user name");
                userName = "";
            }
        }
        if(!admin.isActive()){
            Print.erorr("you are block");
            return;
        }
        goAdminPass();
    }

    public static void goAdminPass() {
        String password;
        boolean loop = true;
        while (loop) {
            Print.input("Enter password:");
            password = Input.simpleString();
            if ("@".equals(password)) {
                return;
            } else if (admin.passwordEqual(password)) {
                loop = false;
                Print.info("Login successfully");
                menu();
                return;
            } else {
                Print.erorr("Wrong password");
            }
        }
    }

    public static void menu() {
        int choice = 0;
        while (true) {
            Print.info(admin.summery());
            Print.menu("1.main setting\n2.manage users\n3.Auto trasaction");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }

            switch (choice) {
                case 1:
                    //verifylist();
                    break;
                case 2:
                    manageUser();
                    break;
                case 3:
                    //inFilterUser();
                    break;
                default:
                    Print.erorr("invalid choice");
                    break;
            }

        }
    }

    public static void manageUser(){
        int choice = 0;
        while (true) {
            Print.menu("1.add admin\n2.add support\n3.list of admins\n4.list of supports\n5.list of users");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switchManageUser(choice);

        }
    }

    public static void switchManageUser(int choice){
        switch (choice) {
            case 1:
                AdminListAdmin.addAdmin();
                break;
            case 2:
                AdminListSupp.addSupport();
                break;
            case 3:
                AdminListAdmin.inFilterAdmin();
                break;
            case 4:
                AdminListSupp.inFilterSupport();
                break;
            case 5:
                AdminListUser.inFilterUser();
                break;
            default:
                Print.erorr("invalid choice");
                break;
        }
    }




    public static void inNameFilter(Map<AdminFilter, String> filters) {
        String name = defineName();
        if ("@".equals(name)) {
            return;
        }
        filters.put(AdminFilter.NAME, name);
    }

    public static void inUserNameFilter(Map<AdminFilter, String> filters) {
        String userName = defUserName();
        if ("@".equals(userName)) {
            return;
        }
        filters.put(AdminFilter.USERNAME, userName);
    }

    public static String defineName() {
        String temp, name = "";
        Command command;
        while (name.isEmpty()) {
            Print.input("Enter name:");
            temp = syInput.nextLine();
            command = Input.checkLine(temp);
            temp = temp.strip().toLowerCase();
            if (command == Command.BACK) {
                return "@";
            } else if (!Input.isLetter(temp)) {
                Print.erorr("invalid name");
            } else {
                name = temp;
            }
        }
        return name;
    }

    public static String defUserName() {
        String temp, userName = "";
        Command command;
        while (userName.isEmpty()) {
            Print.input("Enter username:");
            temp = syInput.nextLine();
            command = Input.checkLine(temp);
            temp = temp.strip().toLowerCase();
            if (command == Command.BACK) {
                return "@";
            }else {
                userName = temp;
            }
        }
        return userName;
    }
}
