package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World!");
        Account a=new Account("max","vestapen", 9121265987L,"1593574562","12354w");
        DataBase.getAccounts().add(new Account("mohammad","hamidi",9335236598L,"3265987412","1234"));
        a.addContact("mohammad","hamidi",9335236598L);
        System.out.println(a.getContactMap().values().toString());


    }

}
