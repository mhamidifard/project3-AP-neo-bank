package ir.ac.kntu;

public class Charge extends Transaction {
    private long toAccount;

    public Charge(double value,long toAccount) {
        super(value, TraType.CHARGE);
        setToAccount(toAccount);
    }

    public long getToAccount() {
        return toAccount;
    }

    public void setToAccount(long toAccount) {
        this.toAccount = toAccount;
    }
}
