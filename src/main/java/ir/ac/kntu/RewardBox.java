package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.Instant;

public class RewardBox extends Box{
    private boolean profeted=false;
    private Instant date;
    private long days;
    private Account account;

    public RewardBox(Long balance, long days) {
        super(balance, TypeBox.REWARD);
        this.days = days;
        setDate(days);
    }



    public Instant getDate() {
        return date;
    }

    public void setDate(long days) {
        this.date = Calendar.now().plusSeconds(days*24*60*60);
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isProfeted() {
        return profeted;
    }

    public void setProfeted(boolean profeted) {
        this.profeted = profeted;
    }

    @Override
    public String summery(){
        return super.summery()+"    "+date+"  profited: "+profeted;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\ndays=" + days +
                "   date=" + date +
                "\nprofeted=" + profeted;
    }
}
