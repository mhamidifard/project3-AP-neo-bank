package ir.ac.kntu;

import com.sun.jdi.Value;
import ir.ac.kntu.util.Calendar;

import java.time.Instant;

enum TraType {
    TRANSFER, CHARGE;
}

public abstract class Transaction {
    double value;
    Instant date;
    TraType type;
    long id;
    public Transaction(double value, TraType type){
        setDate(Calendar.now());
        setType(type);
        setValue(value);
        setId(createId());

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
