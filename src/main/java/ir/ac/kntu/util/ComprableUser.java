package ir.ac.kntu.util;

import ir.ac.kntu.Account;

public class ComprableUser implements Comparable{
    private Account account;
    private int length=0;
    private boolean condition=true;

    public ComprableUser(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getLength() {
        return length;
    }

    public void addLength(int num){
        this.length+=num;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    @Override
    public int compareTo(Object obj) {
        ComprableUser other=(ComprableUser) obj;
        return this.length -other.length;
    }
}
