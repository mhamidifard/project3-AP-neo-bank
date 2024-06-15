package ir.ac.kntu;

public class Parametr {
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
        Parametr.cardToMax = cardToMax;
    }

    public static long getPolMax() {
        return polMax;
    }

    public static void setPolMax(long polMax) {
        Parametr.polMax = polMax;
    }

    public static long getPayaMax() {
        return payaMax;
    }

    public static void setPayaMax(long payaMax) {
        Parametr.payaMax = payaMax;
    }

    public static long getFariToMax() {
        return fariToMax;
    }

    public static void setFariToMax(long feriToMax) {
        Parametr.fariToMax = feriToMax;
    }

    public static long getCardToFee() {
        return cardToFee;
    }

    public static void setCardToFee(long cardToFee) {
        Parametr.cardToFee = cardToFee;
    }

    public static long getPolFee() {
        return polFee;
    }

    public static void setPolFee(long polFee) {
        Parametr.polFee = polFee;
    }

    public static long getPayaFee() {
        return payaFee;
    }

    public static void setPayaFee(long payaFee) {
        Parametr.payaFee = payaFee;
    }

    public static long getFariToFee() {
        return fariToFee;
    }

    public static void setFariToFee(long fariToFee) {
        Parametr.fariToFee = fariToFee;
    }

    public static long getSimChargeFee() {
        return simChargeFee;
    }

    public static void setSimChargeFee(long simChargeFee) {
        Parametr.simChargeFee = simChargeFee;
    }
}
