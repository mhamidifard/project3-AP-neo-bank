package ir.ac.kntu;

public class Transfer extends Transaction{
    private long fromAccount;
    private long fromPhone;
    private long toAccount;
    private long toPhone;

    public Transfer(double value,long fromAccount,long toAccount) {
        super(value, TraType.TRANSFER);
        setFromAccount(fromAccount);
        setToAccount(toAccount);
        setFromPhone(DataBase.findByAccNum(fromAccount).getPhoneNumber());
        setToPhone(DataBase.findByAccNum(toAccount).getPhoneNumber());
    }


    public long getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(long fromAccount) {
        this.fromAccount = fromAccount;
    }

    public long getFromPhone() {
        return fromPhone;
    }

    public void setFromPhone(long fromPhone) {
        this.fromPhone = fromPhone;
    }

    public long getToAccount() {
        return toAccount;
    }

    public void setToAccount(long toAccount) {
        this.toAccount = toAccount;
    }

    public long getToPhone() {
        return toPhone;
    }

    public void setToPhone(long toPhone) {
        this.toPhone = toPhone;
    }
}
