package com.itau.devItau.dto;

import java.time.OffsetDateTime;

public class ErrorResponse {
    private String messageEx;

    OffsetDateTime timeStamp = OffsetDateTime.now();

    public ErrorResponse(String messageEx) {
        this.messageEx = messageEx;
    }

    public String getMessageEx() {
        return messageEx;
    }

    public OffsetDateTime getDateTime() {
        return timeStamp;
    }

    public void setMessageEx(String messageEx) {
        this.messageEx = messageEx;
    }

    public void setTimeStamp(OffsetDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
