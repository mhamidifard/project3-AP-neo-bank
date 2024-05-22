package ir.ac.kntu;

import java.util.Scanner;

public class SupportInput {
    public static Scanner in;
    private static Support support;
    public static void menu(Support support1,Scanner systemIn){
        support=support1;
        in=systemIn;
        String temp;

        int choice=0;
        while (choice!=-1){
            System.out.println("1.verify");
            Input.printBottom();
            temp=in.nextLine();
            if(Input.checkLine(temp)==Command.BACK){
                Input.goSupport();
                choice=-1;
            } else if (!Input.isNumber(temp)) {
                System.out.println("invalid choice");
            }else{
                choice=Integer.parseInt(temp);
                switch (choice){
                    case 1:
                        verifylist();

                    default:
                        choice=0;
                        System.out.println("invalid choice");
                }
            }
        }
    }

    public static void verifylist(){
        String temp;
        int choice=0;
        while (choice==0){
            for (int i = 0; i < DataBase.getVerifyRequests().size(); i++) {
                System.out.println(i+1+" : "+DataBase.getVerifyRequests().get(i).summery());
            }
            Input.printBottom();
            temp=in.nextLine();
            if(Input.checkLine(temp)==Command.BACK){
                //menu(support,in);
                return;
            } else if (!Input.isNumber(temp)) {
                System.out.println("invalid choice");
            }else{
                choice=Integer.parseInt(temp);
                if(0<choice && choice<=DataBase.getVerifyRequests().size()){
                    checkVerify(DataBase.getVerifyRequests().get(choice-1));
                }
                else {
                    choice=0;
                    System.out.println("invalid choice");
                }
            }
        }
    }
    public static void checkVerify(VerificationRequest verifyReq){

        String temp;
        int choice=0;
        while (choice==0){
            System.out.println(verifyReq.toString());
            System.out.println("1.accept\n2.reject");
            Input.printBottom();
            temp=in.nextLine();
            if(Input.checkLine(temp)==Command.BACK){
                //verifylist();
                return;
            } else if (!Input.isNumber(temp)) {
                System.out.println("invalid choice");
            }else{
                choice=Integer.parseInt(temp);
                if(choice==1){
                    verifyReq.accept();
                }
                else if(choice==2){
                    System.out.println("Enter the message");
                    Input.printBottom();
                    temp=in.nextLine();
                    if(Input.checkLine(temp)==Command.BACK) {
                        checkVerify(verifyReq);
                        return;
                    }
                    verifyReq.reject(temp);
                }else {
                    choice=0;
                    System.out.println("invalid choice");
                }
            }
        }
    }
}
