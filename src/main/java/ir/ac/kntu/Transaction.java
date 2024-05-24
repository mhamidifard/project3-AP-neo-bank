package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.Instant;

enum TraType {
    TRANSFER, CHARGE;
}

public abstract class Transaction {
    private long value;
    private Instant date;
    private TraType type;
    private long navId;

    public Transaction(long value, TraType type) {
        setDate(Calendar.now());
        setType(type);
        setValue(value);
        setNavId(createId());

    }

    private long createId() {
        return 12300000 + DataBase.getTransactions().size();
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

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public abstract String toStringComplete(Account account);
}
