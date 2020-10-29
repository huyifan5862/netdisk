package com.bobcfc.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
//    public static int num=10000;
//    public static String salt="张三";


    private int uid;
    private String uname;
    private String upassword;
    private String unick;


}
