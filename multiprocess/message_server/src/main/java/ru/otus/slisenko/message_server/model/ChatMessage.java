package ru.otus.slisenko.message_server.model;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private long id;
    private MessageType type;
    private String content;
    private String sender;
    private String date;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
