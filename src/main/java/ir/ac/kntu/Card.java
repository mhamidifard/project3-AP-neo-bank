package ir.ac.kntu;

public class Card {
    private long cardNumber;
    private int hashCardPass;
    public Card(long accNumber) {
        cardNumber = 6219000000000000L+accNumber;
        setHashCardPass("1234");
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getHashCardPass() {
        return hashCardPass;
    }

    public void setHashCardPass(String Pass) {
        this.hashCardPass = Pass.hashCode();
    }
}
