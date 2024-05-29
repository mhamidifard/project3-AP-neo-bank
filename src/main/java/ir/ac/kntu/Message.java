package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.Instant;

enum Sender {
    SUPPORT, USER;
}

public class Message {
    private String text;
    private Sender sender;
    private Instant date;

    public Message(String text, Sender sender) {
        this.text = text;
        this.sender = sender;
        this.date = Calendar.now();
    }

    public String getText() {
        return text;
    }

    public Sender getSender() {
        return sender;
    }

    public Instant getDate() {
        return date;
    }
}
