package ir.ac.kntu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum Command{
        FRONT,REAR,BACK,QUITE,NOTHING;
    }

public class Input {
    public static Scanner in;
    public static void first(){
        in=new Scanner(System.in);
        userType();
    }
    public static Command checkLine(String temp){
        switch (temp){
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
    public static boolean isNumber(String temp){
        Pattern pNumber=Pattern.compile("\\d+");
        Matcher mNumber=pNumber.matcher(temp);
        if (mNumber.matches()){
            return true;
        }
        return false;
    }

    public static void printBottom(){
        System.out.println("@:back  #quit:quit");
    }
    public static void userType(){
        int choice=-1;
        String temp;
        while (choice==-1) {
            System.out.println("select user type\n1.user\n2.support");
            temp = in.nextLine();
            if (checkLine(temp) == Command.NOTHING && isNumber(temp)) {
                choice = Integer.parseInt(temp);
                switch (choice) {
                    case 1:
                    goUser();
                        return;
                    case 2:

                        return;
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

    public static void goUser(){
        int choice=-1;
        String temp;
        while (choice==-1) {
            System.out.println("1.Login\n2.Register");
            printBottom();
            temp = in.nextLine();
            Command command=checkLine(temp);
            if (command == Command.NOTHING && isNumber(temp)) {
                choice = Integer.parseInt(temp);
                switch (choice) {
                    case 1:
                        login();
                        return;
                    case 2:
                        return;
                    default:
                        choice = -1;
                        System.out.println("invalid choise");
                        return;
                }
            } else if (command==Command.BACK) {
                userType();
                return;
            } else {
                System.out.println("invalid input");
            }
        }

    }

    public static void login(){
        Account account=null;
        String temp,password="";
        Command command;
        boolean loop=true;
        long phoneNumber=0;
        while (phoneNumber==0) {
            System.out.println("Enter phone number:");
            temp = in.nextLine();
            command = checkLine(temp);
            if (command == Command.NOTHING && isNumber(temp)) {
                phoneNumber = Long.parseLong(temp);
                account=DataBase.findByPhone(phoneNumber);
                if (account==null){
                    phoneNumber=0;
                    System.out.println("Wrong phoneNumber");
                }

            } else if (command == Command.BACK) {
                goUser();
                return;

            } else {
                System.out.println("invalid input");
            }
        }

        while (loop) {
            System.out.println("Enter password:");
            temp = in.nextLine();
            command = checkLine(temp);
            if (command == Command.NOTHING ) {
                password=temp;
                System.out.printf("(%s)\n", password);

            } else if (command == Command.BACK) {
                goUser();
                return;

            } else {
                System.out.println("invalid input");
            }
             if (account.passwordEqual(password)) {
                loop=false;
                System.out.println("Login successfully");
                userAccount(account);
                return;
            }else {
                System.out.println("Wrong password");
            }
        }
    }
    public static void userAccount(Account account){

    }

}
