package ir.ac.kntu;

public class Transfer extends Transaction{
    public static final long fee=1000;
    private long fromAccount;
    private long fromPhone;
    private long toAccount;
    private long toPhone;
    private String toName;

    public Transfer(long value,long fromAccount,long toAccount) {
        super(value, TraType.TRANSFER);
        setFromAccount(fromAccount);
        setToAccount(toAccount);
        setFromPhone(DataBase.findByAccNum(fromAccount).getPhoneNumber());
        Account destAcc=DataBase.findByAccNum(toAccount);                   //to account
        setToPhone(destAcc.getPhoneNumber());
        setToName(destAcc.getFirstName()+" "+destAcc.getLastName());
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

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    @Override
    public String toStringComplete(Account account) {
        String toName=this.toName;
        long value=getValue();
        if(fromAccount==account.getAccountNumber()){
            value*=-1;
            if(account.containContact(toPhone)) {
                toName = account.findContactName(toPhone);
            }
        }
        return "Transfer\n"+
                "from: "+fromAccount +" amount: "+value+
                "\nto: "+toAccount+" name: "+toName+
                "\ndate: "+getDate()+" nav id: "+getNavId();

    }
}
