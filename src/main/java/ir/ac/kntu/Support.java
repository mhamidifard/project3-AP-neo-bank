package ir.ac.kntu;

public class Support {
    private String name, userName;
    private int hashPass;

    public Support(String name, String userName, String password) {
        setName(name);
        setUserName(userName);
        setHashPass(password);
    }

    public void verify(VerificationRequest verifyReq) {
        verifyReq.setStatus(true);
        verifyReq.setMessage("true");

    }

    public boolean passwordEqual(String password) {
        return password.hashCode() == getHashPass();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getHashPass() {
        return hashPass;
    }

    public void setHashPass(String password) {
        this.hashPass = password.hashCode();
    }
}
