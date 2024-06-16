package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminListSupp {

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
                AdminInput.inNameFilter(filters);
            } else if (choice == 2) {
                AdminInput.inUserNameFilter(filters);
            }else if (choice == 3) {
                filterSupport(filters);
                filters = new HashMap<>();
            } else {
                Print.erorr("invalid choice");
            }
        }
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

    public static void showSupport(Support support) {
        int choice;
        while (true) {
            Print.info(support.toString());
            Print.menu("\n1.set activity subjects\n2.change active status\n3.change basic information");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    setSubjects(support);
                    break;
                case 2:
                    support.setActive(!support.isActive());
                    break;
                case 3:
                    changeSuppInfo(support);
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

    public static void changeSuppInfo(Support support){
        int choice;
        while (true) {
            Print.info(support.summery());
            Print.menu("\n1.change name\n2.change username\n3.change password");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    changeSuppName(support);
                    break;
                case 2:
                    changeSuppUserName(support);
                    break;
                case 3:
                    changeSuppPassword(support);
                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void changeSuppName(Support support){
        String name=AdminInput.defineName();
        if("@".equals(name)){
            return;
        }
        support.setName(name);
    }

    public static void changeSuppUserName(Support support){
        while (true) {
            String userName = AdminInput.defUserName();
            if ("@".equals(userName)) {
                return;
            } else if (DataBase.suppFind(userName) != null) {
                Print.erorr("duplicate username");
            } else {
                support.setUserName(userName);
                return;
            }
        }

    }

    public static void changeSuppPassword(Support support){
        String password = Input.definePassword();
        if ("@".equals(password)) {
            return;
        }
        support.setHashPass(password);
    }

    public static void addSupport(){
        String userName;
        while (true) {
            userName = AdminInput.defUserName();
            if ("@".equals(userName)) {
                return;
            } else if (DataBase.suppFind(userName) != null) {
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
        DataBase.addSupport(name,userName,password);
        showSupport(DataBase.suppFind(userName));
    }


}
