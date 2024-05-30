package ir.ac.kntu;

public class Main {

    public static void main(String[] args) {
        DataBase.addSupport("mohammad", "admin12", "1382*");
        DataBase.addUser(new Account("max","verstappen",912,"331","20002001Wz@"));
        DataBase.findById("331").verify();
        Input.first();

    }


}
