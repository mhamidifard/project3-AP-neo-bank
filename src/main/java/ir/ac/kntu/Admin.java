package ir.ac.kntu;

public class Admin {
    private static long cardToMax=100000L;
    private static long polMax=5000000L;
    private static long payaMax=5000000L;
    private static long fariToMax=16000000L;
    private static long cardToFee=300L;
    private static long polFee=2L;
    private static long payaFee=300L;
    private static long fariToFee=0L;
    private static long simChargeFee=10L;

    public static long getCardToMax() {
        return cardToMax;
    }

    public static void setCardToMax(long cardToMax) {
        Admin.cardToMax = cardToMax;
    }

    public static long getPolMax() {
        return polMax;
    }

    public static void setPolMax(long polMax) {
        Admin.polMax = polMax;
    }

    public static long getPayaMax() {
        return payaMax;
    }

    public static void setPayaMax(long payaMax) {
        Admin.payaMax = payaMax;
    }

    public static long getFariToMax() {
        return fariToMax;
    }

    public static void setFariToMax(long feriToMax) {
        Admin.fariToMax = feriToMax;
    }

    public static long getCardToFee() {
        return cardToFee;
    }

    public static void setCardToFee(long cardToFee) {
        Admin.cardToFee = cardToFee;
    }

    public static long getPolFee() {
        return polFee;
    }

    public static void setPolFee(long polFee) {
        Admin.polFee = polFee;
    }

    public static long getPayaFee() {
        return payaFee;
    }

    public static void setPayaFee(long payaFee) {
        Admin.payaFee = payaFee;
    }

    public static long getFariToFee() {
        return fariToFee;
    }

    public static void setFariToFee(long fariToFee) {
        Admin.fariToFee = fariToFee;
    }

    public static long getSimChargeFee() {
        return simChargeFee;
    }

    public static void setSimChargeFee(long simChargeFee) {
        Admin.simChargeFee = simChargeFee;
    }
}
