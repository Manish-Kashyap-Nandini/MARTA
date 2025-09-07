package com.project.backend.controllers.dto;



public class RealtimeMessage {
    private String type;   // "CHAT" or "NOTIFICATION"
    private Object payload;

    public RealtimeMessage() {}

    public RealtimeMessage(String type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Object getPayload() { return payload; }
    public void setPayload(Object payload) { this.payload = payload; }
}

