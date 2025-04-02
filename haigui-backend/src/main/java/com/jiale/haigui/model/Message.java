package com.jiale.haigui.model;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Message {

    private long roomId;
    private String role;
    private String content;
    private LocalDateTime createdAt;
}
