package ir.ac.kntu;

public class Helper {

    public static long pow(long firstNum, long secondNum) {
        long ans = 1;
        for (int i = 0; i < secondNum; i++) {
            ans *= firstNum;
        }
        return ans;
    }
}
