package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.Instant;
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
                    mainSetting();
                    break;
                case 2:
                    manageUser();
                    break;
                case 3:
                    autoTransaction();
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

    public static void mainSetting(){
        int choice = 0;
        while (true) {
            Print.menu("1.maximum\n2.fee\n3.profit");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    changeMaxes();
                    break;
                case 2:
                    changeFees();
                    break;
                case 3:
                    changeProfit();
                    break;
                default:
                    Print.erorr("invalid choice");
                    break;
            }

        }
    }

    public static void changeMaxes(){
        int choice = 0;
        while (true) {
            Print.menu("change maximum\n1.card to card="+Parametr.getCardToMax()+"\n2.pol="+Parametr.getPolMax()+
                    "\n3.paya="+Parametr.getPayaMax()+"\n4.fari to fari="+Parametr.getFariToMax());
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    Parametr.setCardToMax(simpleLong("card to card max"));
                    break;
                case 2:
                    Parametr.setPolMax(simpleLong("pol max"));
                    break;
                case 3:
                    Parametr.setPayaMax(simpleLong("paya max"));
                    break;
                case 4:
                    Parametr.setFariToMax(simpleLong("fari to fari max"));
                    break;
                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void changeFees(){
        int choice = 0;
        while (true) {
            Print.menu("change fee\n1.card to card="+Parametr.getCardToFee()+"\n2.pol="+Parametr.getPolFee()+
                    "\n3.paya="+Parametr.getPayaFee()+"\n4.fari to fari="+Parametr.getFariToFee()+"\n5.charge phone="+Parametr.getSimChargeFee());
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switchChangeFees(choice);
        }
    }

    public static void switchChangeFees(int choice){
        switch (choice) {
            case 1:
                Parametr.setCardToFee(simpleLong("card to card fee"));
                break;
            case 2:
                Parametr.setPolFee(simpleLong("pol fee"));
                break;
            case 3:
                Parametr.setPayaFee(simpleLong("paya fee"));
                break;
            case 4:
                Parametr.setFariToFee(simpleLong("fari to fari fee"));
                break;
            case 5:
                Parametr.setSimChargeFee(simpleLong("phone charge fee"));
                break;
            default:
                Print.erorr("invalid choice");
                break;
        }
    }

    public static long simpleLong(String text) {
        String temp;
        while (true) {
            //Input.printBottom();
            Print.input("enter "+text+" :");
            temp = syInput.nextLine();
            if (Input.checkLine(temp) == Command.BACK) {
                return -1;
            } else if (!Input.isNumber(temp) ) {
                Print.erorr("invalid number");
            } else {
                return Long.parseLong(temp);
            }
        }
    }

    public static void changeProfit(){
        int choice = 0;
        while (true) {
            Print.menu("proft="+Parametr.getProfit()+"\n1.change");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            } else if (choice==1) {
                Parametr.setProfit(simpleLong("profit"));
            }else {
                Print.erorr("invalid number");
            }
        }
    }

    public static void autoTransaction() {
        int choice = 0;
        while (true) {
            Print.menu("1.transfers\n2.boxes");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    autoTransfer();
                    break;
                case 2:
                    autoBox();
                    break;
                default:
                    Print.erorr("invalid choice");
                    break;
            }

        }
    }

    public static void autoTransfer(){
        int counter=0;
        for (Transfer transfer:DataBase.getPendingTransfer()){
            transfer.setTransferStatus(TransferStatus.COMPLETED);
            counter++;
        }
        DataBase.getPendingTransfer().clear();
        Print.info("completed "+counter+" transfers");

    }

    public static void autoBox(){
        Instant nowTime= Calendar.now();
        int counter=0;
        for (int i = 0; i <DataBase.getRewardBoxes().size(); i++) {
            if(nowTime.isAfter(DataBase.getRewardBoxes().get(i).getDate())){
                DataBase.getRewardBoxes().get(i).payProfit();
                DataBase.getRewardBoxes().remove(i);
                i--;
                counter++;
            }
        }
        Print.info("payed "+counter+" profits");
    }


}
