package ir.ac.kntu;

public class TraSimCharge extends Transaction {
    private long phone;
    private long accNum;

    public TraSimCharge(long value, long phone,long accNum) {
        super(value, TraType.SIMCHARGE);
        this.phone = phone;
        this.accNum=accNum;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public long getAccNum() {
        return accNum;
    }

    public void setAccNum(long accNum) {
        this.accNum = accNum;
    }

    @Override
    public String toStringComplete(Account account) {
        return getType() + "   phone: " + phone + "\n" +
                "account: " + accNum + " amount: " + getValue() +
                "\ndate: " + getDate() + " nav id: " + getNavId();
    }
}
