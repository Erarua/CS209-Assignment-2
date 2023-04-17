package cn.edu.sustech.cs209.chatting.common;

import java.io.Serializable;

public class Message implements Serializable {
    private MessageType messageType;
    private String sentBy;

    private String sendTo;

    private String data;

    public Message(MessageType messageType, String sentBy, String sendTo, String data) {
        this.messageType = messageType;
        this.sentBy = sentBy;
        this.sendTo = sendTo;
        this.data = data;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getSentBy() {
        return sentBy;
    }

    public String getSendTo() {
        return sendTo;
    }

    public String getData() {
        return data;
    }
}
