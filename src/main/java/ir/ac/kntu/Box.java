package ir.ac.kntu;



import java.time.Instant;

enum TypeBox{
    SAVING,SMALLMONEY,REWARD;
}
public class Box {
    private Long balance;
    private TypeBox type;
    private Instant date;


    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public TypeBox getType() {
        return type;
    }

    public void setType(TypeBox type) {
        this.type = type;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
}
