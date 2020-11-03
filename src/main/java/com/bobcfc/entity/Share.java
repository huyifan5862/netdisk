package com.bobcfc.entity;


import lombok.Data;

import java.util.Date;

@Data
public class Share {

  private long sid;
  private int uid;
  private String unick;
  private String fname;
  private String frealname;
  private String slink;
  private String spassword;
  private Date stime;
  private Date sed;

}
