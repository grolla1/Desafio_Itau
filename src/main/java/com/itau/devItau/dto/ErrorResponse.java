package com.itau.devItau.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ErrorResponse {
    private String messageEx;

    OffsetDateTime timeStamp = OffsetDateTime.now();

    public ErrorResponse(String messageEx) {
        this.messageEx = messageEx;
    }
}
