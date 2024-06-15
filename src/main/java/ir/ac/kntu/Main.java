package ir.ac.kntu;

public class Main {

    public static void main(String[] args) {

        DataBase.addSupport("mohammad", "supp12", "1382*");
        DataBase.addUser(new Account("max","verstappen",912L,"331","20002001Wz@"));
        DataBase.findById("331").verify();
        DataBase.addUser(new Account("amir","hamedi",9301234567L,"007","13871388Wz@"));
        DataBase.findById("007").verify();
        DataBase.addAdmin("mohammad sajjad","admin12","1382+");

        OtherDataBase.setAccounts();
        Input.first();

    }


}
