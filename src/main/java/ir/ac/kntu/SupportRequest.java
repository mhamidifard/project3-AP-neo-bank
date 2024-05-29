package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

enum Status {
    CREATED, PROCESS, CLOSED, NOTHING;
}

enum Subject {
    REPORT, CONTACTS, TRANSFER, SETTING, NOTHING;
}

public class SupportRequest {
    public static final long firstId = 234000000;
    private List<Message> messages = new ArrayList<>();
    private String title;
    private long userPhone;
    private long navId;
    private Status status;
    private Subject subject;

    public SupportRequest(long userPhone, String title, Subject subject) {
        this.title = title;
        this.userPhone = userPhone;
        setNavId();
        status = Status.CREATED;
        this.subject = subject;
    }

    public void addMessage(String text, Sender sender) {
        messages.add(new Message(text, sender));
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public long getNavId() {
        return navId;
    }

    public void setNavId() {
        this.navId = 234000000 + DataBase.getSupportRequests().size();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String summry() {
        return "subject: " + subject + " title: " + title + " userPhone: " + userPhone + " status: " + status;
    }

    @Override
    public String toString() {
        String ans = "subject: " + subject + " status: " + status + "\ntitle: " + title + " userPhone: " + userPhone+"\n";
        for (Message message : messages) {
            ans += "\nsender: " + message.getSender() + "\nmessage: " + message.getText() + "\n";
        }
        return ans;
    }

}
