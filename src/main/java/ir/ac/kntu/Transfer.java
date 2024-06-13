package ir.ac.kntu;

enum TransferType{
    CardToCard,POL,PAYA,FARITOFARI;
}

enum TransferStatus{
    COMPLETED,PENDING;
}


public class Transfer extends Transaction{
    //public static final long fee=1000;
    private long fromAccount;
    private long fromPhone;
    private long toAccount;
    private long toPhone;
    private String toName;
    private TransferType transferType;
    private TransferStatus transferStatus;

    public Transfer(long value,long fromAccount,long toAccount,TransferType transferType) {
        super(value, TraType.TRANSFER);
        setFromAccount(fromAccount);
        setToAccount(toAccount);
        setFromPhone(DataBase.findByAccNum(fromAccount).getPhoneNumber());
        if(transferType==TransferType.FARITOFARI) {
            Account destAcc = DataBase.findByAccNum(toAccount);                   //to account
            setToPhone(destAcc.getPhoneNumber());
            setToName(destAcc.getFirstName() + " " + destAcc.getLastName());
        }else {
            OtherBanks destAcc;
            if(transferType==TransferType.CardToCard){
                destAcc = OtherDataBase.findByCardNum(toAccount);
            }else {
                destAcc = OtherDataBase.findByAccNum(toAccount);
            }
            setToPhone(0);
            setToName(destAcc.getFirstName()+" "+destAcc.getLastName());
        }
        setTransferType(transferType);
        setTransferStatus(TransferStatus.COMPLETED);
        if(transferType==TransferType.PAYA){
            setTransferStatus(TransferStatus.PENDING);
        }
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

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
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
        return transferType+"   "+transferStatus+"\n"+
                "from: "+fromAccount +" amount: "+value+
                "\nto: "+toAccount+" name: "+toName+
                "\ndate: "+getDate()+" nav id: "+getNavId();

    }
}
