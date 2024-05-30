package ir.ac.kntu;

public class Print {
    static final String BLACK="\u001B[30m";
    static final String RESET="\u001B[0m";
    static final String RED	="\u001B[31m";
    static final String GREEN="\u001B[32m";
    static final String YELLOW="\u001B[33m";
    static final String BLUE="\u001B[34m";
    static final String PURPLE="\u001B[35m";
    static final String CYAN="\u001B[36m";
    static final String WHITE="\u001B[37m";

    public static void erorr(String text){
        System.out.println(RED+text+RESET);
    }

    public static void menu(String text){
        System.out.println(BLUE+text+RESET);
    }

    public static void bottom(String text){
        System.out.println(YELLOW+text+RESET);
    }

    public static void info(String text){
        System.out.println(GREEN+text+RESET);
    }

    public static void list(String text){
        System.out.println(CYAN+text+RESET);
    }

    public static void input(String text){
        System.out.println(PURPLE+text+RESET);
    }

}
