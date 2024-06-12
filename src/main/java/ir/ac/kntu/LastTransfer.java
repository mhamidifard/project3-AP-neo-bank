package ir.ac.kntu;

import java.util.Objects;

public class LastTransfer {
    private long accountNum;
    private long cardNum;

    public LastTransfer(long accountNum, long cardNum) {
        this.accountNum = accountNum;
        this.cardNum = cardNum;
    }

    public long getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(long accountNum) {
        this.accountNum = accountNum;
    }

    public long getCardNum() {
        return cardNum;
    }

    public void setCardNum(long cardNum) {
        this.cardNum = cardNum;
    }

    @Override
    public String toString() {
        if (cardNum == 0) {
            return "account = " + accountNum;
        } else {
            return "card = " + cardNum;
        }
    }

    @Override
    public boolean equals(Object obj) {
        LastTransfer that = (LastTransfer) obj;
        return accountNum == that.accountNum && cardNum == that.cardNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNum, cardNum);
    }
}
