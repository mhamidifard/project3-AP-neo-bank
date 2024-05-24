package ir.ac.kntu;

public class Contact {
    private String firstName;
    private String lastName;
    private long phoneNumber;
    private long accountNumber;

    public Contact(String firstName, String lastName, long phoneNumber) {
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setAccountNumber(DataBase.findByPhone(phoneNumber).getAccountNumber());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String summery() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public String toString() {
        return "first name : " + getFirstName() + " last name : " + getLastName() + "\n" +
                "phone number : " + phoneNumber + " account number : " + accountNumber;
    }
}
