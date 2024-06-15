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
        Admin admin1 = null;
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
        goAdminPass(admin1);
    }

    public static void goAdminPass(Admin admin1) {
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
                admin=admin1;
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

            switch (choice) {
                case 1:
                    //verifylist();
                    break;
                case 2:
                    //inFilterReq();
                    break;
                case 3:
                    inFilterAdmin();
                    break;
                case 4:
                    inFilterSupport();
                    break;
                case 5:
                    SupportInput.inFilterUser();
                default:
                    Print.erorr("invalid choice");
                    break;
            }

        }
    }

    public static void inFilterSupport() {
        Map<AdminFilter, String> filters = new HashMap<>();
        int choice = 0;
        while (true) {
            Print.info("filter support by");
            Print.menu("1.name\n2.user name\n3.apply");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            } else if (choice == 1) {
                inNameFilter(filters);
            } else if (choice == 2) {
                inUserNameFilter(filters);
            }else if (choice == 3) {
                filterSupport(filters);
                filters = new HashMap<>();
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static void inFilterAdmin() {
        Map<AdminFilter, String> filters = new HashMap<>();
        int choice = 0;
        while (true) {
            Print.info("filter admin by");
            Print.menu("1.name\n2.user name\n3.apply");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            } else if (choice == 1) {
                inNameFilter(filters);
            } else if (choice == 2) {
                inUserNameFilter(filters);
            }else if (choice == 3) {
                filterAdmin(filters);
                filters = new HashMap<>();
            } else {
                Print.erorr("invalid choice");
            }
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

    public static void filterSupport(Map<AdminFilter, String> filters) {
        ArrayList<ComprableSupport> listSupport = new ArrayList<>();
        for (Support support : DataBase.getSupports()) {
            ComprableSupport coSupport = new ComprableSupport(support);
            for (HashMap.Entry<AdminFilter, String> filter : filters.entrySet()) {
                checkSupport(filter, support, coSupport);
            }
            if (coSupport.isCondition()) {
                listSupport.add(coSupport);
            }
        }
        listSupport.sort(ComprableSupport::compareTo);
        showSupportList(listSupport);
    }

    public static void filterAdmin(Map<AdminFilter, String> filters) {
        ArrayList<ComprableAdmin> listAdmin = new ArrayList<>();
        for (Admin admin : DataBase.getAdmins()) {
            ComprableAdmin coAdmin = new ComprableAdmin(admin);
            for (HashMap.Entry<AdminFilter, String> filter : filters.entrySet()) {
                checkAdmin(filter, admin, coAdmin);
            }
            if (coAdmin.isCondition()) {
                listAdmin.add(coAdmin);
            }
        }
        listAdmin.sort(ComprableAdmin::compareTo);
        showAdminList(listAdmin);
    }

    public static void checkSupport(HashMap.Entry<AdminFilter, String> filter, Support support, ComprableSupport coSupport) {
        switch (filter.getKey()) {
            case NAME:
                coSupport.addLength(support.getName().length());
                if (!Input.similarity(support.getName(), filter.getValue())) {
                    coSupport.setCondition(false);
                }
                break;
            case USERNAME:
                coSupport.addLength(support.getUserName().length());
                if (!Input.similarity(support.getUserName(), filter.getValue())) {
                    coSupport.setCondition(false);
                }
                break;
            default:
                break;
        }
    }

    public static void checkAdmin(HashMap.Entry<AdminFilter, String> filter, Admin admin, ComprableAdmin coAdmin) {
        switch (filter.getKey()) {
            case NAME:
                coAdmin.addLength(admin.getName().length());
                if (!Input.similarity(admin.getName(), filter.getValue())) {
                    coAdmin.setCondition(false);
                }
                break;
            case USERNAME:
                coAdmin.addLength(admin.getUserName().length());
                if (!Input.similarity(admin.getUserName(), filter.getValue())) {
                    coAdmin.setCondition(false);
                }
                break;
            default:
                break;
        }
    }

    public static void showSupportList(List<ComprableSupport> listSupport) {
        Print.info("list of supports:");
        int choice;
        while (true) {
            for (int i = 0; i < listSupport.size(); i++) {
                Print.list(i + 1 + ". " + listSupport.get(i).getSupport().summery());
            }
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            if (0 < choice && choice <= listSupport.size()) {
                showSupport(listSupport.get(choice - 1).getSupport());
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static void showAdminList(List<ComprableAdmin> listAdmin) {
        Print.info("list of admins:");
        int choice;
        while (true) {
            for (int i = 0; i < listAdmin.size(); i++) {
                Print.list(i + 1 + ". " + listAdmin.get(i).getAdmin().summery());
            }
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            if (0 < choice && choice <= listAdmin.size()) {
                showAdmin(listAdmin.get(choice - 1).getAdmin());
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static void showSupport(Support support) {
        int choice;
        while (true) {
            Print.info(support.toString());
            Print.menu("\n1.set activity subjects\n2.block");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    setSubjects(support);
                    break;
                case 2:

                    break;
                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void showAdmin(Admin admin) {
        int choice;
        while (true) {
            Print.info(admin.toString());
            Print.menu("\n1.block");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:

                    break;
                case 2:

                    break;
                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void setSubjects(Support support){
        int choice;
        List<Map.Entry<Subject,Boolean>> subjects=new ArrayList<>();
        for(Map.Entry<Subject,Boolean> element:support.getSubjects().entrySet()){
            subjects.add(element);
        }
        while (true){
            Print.menu("change subject status\n0.all subjects");
            for (int i = 0; i < subjects.size(); i++) {
                Print.menu(i+1+"."+subjects.get(i).getKey()+"="+subjects.get(i).getValue());
            }
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            } else if (choice==0) {
                for(Map.Entry<Subject,Boolean> element:support.getSubjects().entrySet()){
                    element.setValue(!element.getValue());
                }
            } else if (0 < choice && choice <= subjects.size()) {
                subjects.get(choice-1).setValue(!subjects.get(choice-1).getValue());
            } else {
                Print.erorr("invalid choice");
            }
        }
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
