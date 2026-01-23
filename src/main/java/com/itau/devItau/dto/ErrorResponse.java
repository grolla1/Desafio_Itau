package com.itau.devItau.dto;

import java.time.OffsetDateTime;

public class ErrorResponse {
    private String message;

    OffsetDateTime timeStamp = OffsetDateTime.now();

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public OffsetDateTime getDateTime() {
        return timeStamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimeStamp(OffsetDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
