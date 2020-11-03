package com.bobcfc.controller;

import com.bobcfc.entity.Share;
import com.bobcfc.entity.User;
import com.bobcfc.service.ShareService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

@RestController
public class ShareController {
    @Autowired
    private ShareService shareService;
    @RequestMapping("/share")
    public Share share(String frealname,String fname){
        Share share = new Share();
        share.setSlink("localhost/share/"+fname);
        Random random = new Random();

        String pass = "";
        for (int i = 0; i < 4; i++) {
            pass+=random.nextInt(10);
        }
        share.setSpassword(pass);
        share.setStime(new Date());
        share.setFname(fname);
        share.setFrealname(frealname);
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        share.setUid(user.getUid());
        share.setUnick(user.getUnick());
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.plusDays(7);
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zoneId).toInstant();
        share.setSed(Date.from(instant));
        shareService.insertShare(share);
        return share;
    }
}
