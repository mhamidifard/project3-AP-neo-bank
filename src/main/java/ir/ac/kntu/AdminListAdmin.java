package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminListAdmin {
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
                AdminInput.inNameFilter(filters);
            } else if (choice == 2) {
                AdminInput.inUserNameFilter(filters);
            }else if (choice == 3) {
                filterAdmin(filters);
                filters = new HashMap<>();
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static void filterAdmin(Map<AdminFilter, String> filters) {
        ArrayList<ComprableAdmin> listAdmin = new ArrayList<>();
        for (int i = 1; i <DataBase.getAdmins().size(); i++) {
            ComprableAdmin coAdmin = new ComprableAdmin(DataBase.getAdmins().get(i));
            for (HashMap.Entry<AdminFilter, String> filter : filters.entrySet()) {
                checkAdmin(filter, DataBase.getAdmins().get(i), coAdmin);
            }
            if (coAdmin.isCondition()) {
                listAdmin.add(coAdmin);
            }
        }
        listAdmin.sort(ComprableAdmin::compareTo);
        showAdminList(listAdmin);
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

    public static void showAdmin(Admin admin) {
        int choice;
        while (true) {
            Print.info(admin.toString());
            Print.menu("1.change active status\n2.change basic information");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    admin.setActive(!admin.isActive());
                    break;
                case 2:
                    changeAdminInfo(admin);
                    break;
                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void changeAdminInfo(Admin admin){
        int choice;
        while (true) {
            Print.info(admin.summery());
            Print.menu("\n1.change name\n2.change username\n3.change password");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    changeAdminName(admin);
                    break;
                case 2:
                    changeAdminUserName(admin);
                    break;
                case 3:
                    changeAdminPassword(admin);
                    break;
                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void changeAdminName(Admin admin){
        String name=AdminInput.defineName();
        if("@".equals(name)){
            return;
        }
        admin.setName(name);
    }

    public static void changeAdminUserName(Admin admin){
        while (true) {
            String userName = AdminInput.defUserName();
            if ("@".equals(userName)) {
                return;
            } else if (DataBase.adminFind(userName) != null) {
                Print.erorr("duplicate username");
            } else {
                admin.setUserName(userName);
                return;
            }
        }

    }

    public static void changeAdminPassword(Admin admin){
        String password = Input.definePassword();
        if ("@".equals(password)) {
            return;
        }
        admin.setHashPass(password);
    }

    public static void addAdmin(){
        String userName;
        while (true) {
            userName = AdminInput.defUserName();
            if ("@".equals(userName)) {
                return;
            } else if (DataBase.adminFind(userName) != null) {
                Print.erorr("duplicate username");
            } else {
                break;
            }
        }
        String name=AdminInput.defineName();
        if("@".equals(name)){
            return;
        }
        String password = Input.definePassword();
        if ("@".equals(password)) {
            return;
        }
        DataBase.addAdmin(name,userName,password);
        showAdmin(DataBase.adminFind(userName));
    }

}
