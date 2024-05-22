package ir.ac.kntu;

import java.util.Scanner;

public class UserInput {
    static Account account;
    static Scanner in;
    public static void menu(Account account1, Scanner systemIn){
        account=account1;
        in=systemIn;
        String temp;
        int choice=0;
        while (choice!=-1){
            System.out.println("menu\n1.manage account");
            Input.printBottom();
            temp=in.nextLine();
            if(Input.checkLine(temp)==Command.BACK){
                Input.goUser();
                choice=-1;
            } else if (!Input.isNumber(temp)) {
                System.out.println("invalid choice");
            }else{
                choice=Integer.parseInt(temp);
                switch (choice){
                    case 1:
                        return;

                    default:
                        choice=0;
                        System.out.println("invalid choice");
                        break;
                }
            }
        }
    }
}
