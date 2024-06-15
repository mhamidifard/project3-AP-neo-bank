package ir.ac.kntu;

public class ComprableAdmin implements Comparable{

    private Admin admin;
    private int length=0;
    private boolean condition=true;

    public ComprableAdmin(Admin admin) {
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
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
        ComprableAdmin other=(ComprableAdmin) obj;
        return this.length -other.length;
    }
}
