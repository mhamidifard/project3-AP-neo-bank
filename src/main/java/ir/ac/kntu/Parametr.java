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
    private static long profit=1L;

    public static long getCardToMax() {
        return cardToMax;
    }

    public static void setCardToMax(long cardToMax) {
        if(cardToMax==-1){
            return;
        }
        Parametr.cardToMax = cardToMax;
    }

    public static long getPolMax() {
        return polMax;
    }

    public static void setPolMax(long polMax) {
        if(polMax==-1){
            return;
        }
        Parametr.polMax = polMax;
    }

    public static long getPayaMax() {
        return payaMax;
    }

    public static void setPayaMax(long payaMax) {
        if(payaMax==-1){
            return;
        }
        Parametr.payaMax = payaMax;
    }

    public static long getFariToMax() {
        return fariToMax;
    }

    public static void setFariToMax(long feriToMax) {
        if(feriToMax==-1){
            return;
        }
        Parametr.fariToMax = feriToMax;
    }

    public static long getCardToFee() {
        return cardToFee;
    }

    public static void setCardToFee(long cardToFee) {
        if(cardToFee==-1){
            return;
        }
        Parametr.cardToFee = cardToFee;
    }

    public static long getPolFee() {
        return polFee;
    }

    public static void setPolFee(long polFee) {
        if(polFee==-1){
            return;
        }
        Parametr.polFee = polFee;
    }

    public static long getPayaFee() {
        return payaFee;
    }

    public static void setPayaFee(long payaFee) {
        if(payaFee==-1){
            return;
        }
        Parametr.payaFee = payaFee;
    }

    public static long getFariToFee() {
        return fariToFee;
    }

    public static void setFariToFee(long fariToFee) {
        if(fariToFee==-1){
            return;
        }
        Parametr.fariToFee = fariToFee;
    }

    public static long getSimChargeFee() {
        return simChargeFee;
    }

    public static void setSimChargeFee(long simChargeFee) {
        if(simChargeFee==-1){
            return;
        }
        Parametr.simChargeFee = simChargeFee;
    }

    public static long getProfit() {
        return profit;
    }

    public static void setProfit(long profit) {
        if(profit==-1){
            return;
        }
        Parametr.profit = profit;
    }
}
