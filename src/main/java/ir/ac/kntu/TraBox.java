package ir.ac.kntu;

enum BoxAction {
    DEPOSIT, WITHDRAW;
}

public class TraBox extends Transaction {
    private Long accNum;
    private BoxAction boxAction;

    public TraBox(long value, Long accNum, BoxAction boxAction) {
        super(value, TraType.BOXTRANSACTION);
        this.accNum = accNum;
        this.boxAction = boxAction;
    }

    public Long getAccNum() {
        return accNum;
    }

    public void setAccNum(Long accNum) {
        this.accNum = accNum;
    }

    public BoxAction getBoxAction() {
        return boxAction;
    }

    public void setBoxAction(BoxAction boxAction) {
        this.boxAction = boxAction;
    }

    @Override
    public String summery() {
        return getType() + " " + boxAction + "    value: " + getValue() + "  nav id: " + getNavId() + " date: " + getDate();
    }

    @Override
    public String toStringComplete(Account account) {
        return getType() + "   " + boxAction + "\n" +
                "account: " + accNum + " amount: " + getValue() +
                "\ndate: " + getDate() + " nav id: " + getNavId();

    }
}
