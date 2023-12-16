package com.smart.helper;

import lombok.Data;


public class Message {
    private String content;
    private String type;

    public Message(String s, String s1) {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
