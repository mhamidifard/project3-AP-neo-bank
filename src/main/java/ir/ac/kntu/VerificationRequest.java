package ir.ac.kntu;

public class VerificationRequest {
    private boolean status;
    private long phoneNumber;
    private String message;
    private boolean supportChecked;

    public VerificationRequest(long phoneNumber) {
        setPhoneNumber(phoneNumber);
        setStatus(false);
        setMessage("wait to verify");
        setSupportChecked(false);
    }

    public String summery() {
        Account account = DataBase.findByPhone(phoneNumber);
        return account.getFirstName() + " " + account.getLastName() + " " + phoneNumber;
    }

    @Override
    public String toString() {
        Account account = DataBase.findByPhone(phoneNumber);
        return "first name: " + account.getFirstName() + "  " + "last name: " + account.getLastName() + "\n"
                + "phone: " + account.getPhoneNumber() + " id: " + account.getNationalId();
    }

    public void accept() {
        setStatus(true);
        setSupportChecked(true);
        Account account = DataBase.findByPhone(phoneNumber);
        account.verify();
        DataBase.removeVerifyReq(this);
    }

    public void reject(String text) {
        setMessage(text);
        setSupportChecked(true);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSupportChecked() {
        return supportChecked;
    }

    public void setSupportChecked(boolean supportChecked) {
        this.supportChecked = supportChecked;
    }
}
