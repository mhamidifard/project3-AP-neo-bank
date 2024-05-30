package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.Instant;
import java.util.Scanner;

import static java.awt.Color.RED;

public class Main {

    public static void main(String[] args) {

        System.out.println("\u001B[31m" + "\u001B[45m" + "Hello World!");
        System.out.println("\u001B[42m" + "hi word" + "\u001B[0m");
        System.out.println("bye");
        long maxNumber = 912;

        DataBase.addSupport("mohammad", "admin12", "1382*");
        DataBase.addUser("max","verstappen",912,"331","20002001Wz@");
        DataBase.findById("331").verify();
//        DataBase.getAccounts().add(new Account("mohammad","hamidi",9335236598L,"3265987412","1234"));
//        a.addContact("mohammad","hamidi",9335236598L);
//        System.out.println(a.getContactMap().values().toString());
        Input.first();

    }


}
