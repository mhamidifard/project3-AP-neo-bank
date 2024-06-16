package ir.ac.kntu;

public class Admin {
    private boolean active=true;
    private String name, userName;
    private int hashPass;

    public Admin(String name, String userName, String password) {
        setName(name);
        setUserName(userName);
        setHashPass(password);
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

    public String summery(){
        String status="active";
        if(!active){
            status="block";
        }
        return userName+"   "+name+"    "+status;
    }

    @Override
    public String toString(){
        return "user name: "+userName+"     name: "+name+
                "\nactive status="+active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
