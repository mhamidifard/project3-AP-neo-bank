package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.Instant;

enum TraType {
    TRANSFER, CHARGE;
}

public abstract class Transaction {
    double value;
    Instant date;
    TraType type;
    long navId;
    public Transaction(double value, TraType type){
        setDate(Calendar.now());
        setType(type);
        setValue(value);
        setNavId(createId());

    }

    private long createId(){
        return 12300000+DataBase.getTransactions().size();
    }

//    @Override
//    public abstract String toString();

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public TraType getType() {
        return type;
    }

    public void setType(TraType type) {
        this.type = type;
    }

    public long getNavId() {
        return navId;
    }

    public void setNavId(long navId) {
        this.navId = navId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
