package br.ufc.quixada.dsdm.trabalho4.Model;

import com.google.firebase.Timestamp;

public class Message {

    private String sender;
    private String receiver;
    private String message;
    private String group;
    private Timestamp instant;

    public Message() {}

    public Message(String sender, String receiver, String message, String group, Timestamp instant) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.group = group;
        this.instant = instant;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Timestamp getInstant() {
        return instant;
    }

    public void setInstant(Timestamp instant) {
        this.instant = instant;
    }

    @Override
    public String toString() {
        return message;
    }
}
