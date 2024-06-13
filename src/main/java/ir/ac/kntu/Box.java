package ir.ac.kntu;


enum TypeBox{
    SIMPLE,SMALLMONEY,REWARD;
}
public class Box {
    private Long balance;
    private TypeBox type;


    public Box(Long balance, TypeBox type) {
        this.balance = balance;
        this.type = type;
    }

    public void withdraw(long amount){
        balance -= amount;
    }

    public void deposit(long amount){
        balance += amount;
    }

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

    public String summery(){
        return type+"  "+balance;
    }

    @Override
    public String toString() {
        return  type+
                "\nbalance=" + balance;
    }
}
