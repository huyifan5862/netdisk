package com.bobcfc.entity;

import lombok.Data;

@Data
public class Message {
    private String code;
    private String msg;
    private Object path;
    private Object data;
}
