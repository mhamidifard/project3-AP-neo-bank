package ir.ac.kntu;

import java.util.HashMap;
import java.util.Map;

public class Support {
    private String name, userName;
    private int hashPass;
    private Map<Subject,Boolean> subjects=new HashMap<>();

    public Support(String name, String userName, String password) {
        setName(name);
        setUserName(userName);
        setHashPass(password);
        buildSubjects();
    }

    public void verify(VerificationRequest verifyReq) {
        verifyReq.setStatus(true);
        verifyReq.setMessage("true");

    }

    public boolean passwordEqual(String password) {
        return password.hashCode() == getHashPass();

    }

    public  void buildSubjects(){
        subjects.put(Subject.VERIFY,true);
        subjects.put(Subject.REPORT,true);
        subjects.put(Subject.CONTACTS,true);
        subjects.put(Subject.TRANSFER,true);
        subjects.put(Subject.SETTING,true);
        subjects.put(Subject.PhoneCharge,true);
        subjects.put(Subject.card,true);
    }

    public String subToString(){
        String text="subjects:\n";
        for (HashMap.Entry<Subject,Boolean> subject: subjects.entrySet()){
            if(subject.getValue()){
                text+=subject.getKey()+" ";
            }
        }
        return text;
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

    public Map<Subject, Boolean> getSubjects() {
        return subjects;
    }

    public void setSubjects(Map<Subject, Boolean> subjects) {
        this.subjects = subjects;
    }

    public String summery(){
        return userName+"   "+name;
    }

    @Override
    public String toString(){
        return "user name: "+userName+"     name: "+name;
    }
}
