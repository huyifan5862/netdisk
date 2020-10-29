package com.bobcfc.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class MyFile {
    private int fid;
    private int uid;
    private String fname;
    private String fpath;
    private String ffatherpath;
    private double fsize;
    private Date ftime;
    private int isfile;
    private String frealname;
}
