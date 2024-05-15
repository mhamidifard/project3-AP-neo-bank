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
        Pattern p1=Pattern.compile("\\d+");
        Matcher m1=p1.matcher(temp);
        if (m1.matches()){
            return true;
        }
        return false;
    }

    public static void printBottom(){
        System.out.println("@:back  #quit:quit\n");
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
            } else {
                System.out.println("invalid input");
            }
        }

    }

}
