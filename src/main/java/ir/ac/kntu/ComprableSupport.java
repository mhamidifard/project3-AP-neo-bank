package ir.ac.kntu;

public class ComprableSupport implements Comparable {
    private Support support;
    private int length=0;
    private boolean condition=true;

    public ComprableSupport(Support support) {
        this.support = support;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
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
        ComprableSupport other=(ComprableSupport) obj;
        return this.length -other.length;
    }
}
