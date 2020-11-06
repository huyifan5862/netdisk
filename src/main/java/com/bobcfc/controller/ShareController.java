package com.bobcfc.controller;

import com.bobcfc.entity.Message;
import com.bobcfc.entity.MyFile;
import com.bobcfc.entity.Share;
import com.bobcfc.entity.User;
import com.bobcfc.service.FileService;
import com.bobcfc.service.ShareService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Controller
public class ShareController {
    @Autowired
    private ShareService shareService;
    @Autowired
    private FileService fileService;
    @ResponseBody
    @RequestMapping("/share")
    public Share share(String frealname,String fname,int fid){
        Share share = new Share();
        String s = UUID.randomUUID().toString().replaceAll("-", "");
        share.setSlink("localhost/share/"+s);
        Random random = new Random();

        String pass = "";
        for (int i = 0; i < 4; i++) {
            pass+=random.nextInt(10);
        }
        share.setFid(fid);
        share.setUuid(s);
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

    @RequestMapping("/share/{uuid}")
    public String topass(@PathVariable("uuid") String uuid, Model model){
        Share share = shareService.selShare(uuid);
        if(share!=null) {
            model.addAttribute("uuid",uuid);
            return "pass";
        }else {
            return "notfile";
        }
    }
    @ResponseBody
    @RequestMapping("/share/confirm")
    public Message confirm(String uuid,String pass){
        Share share = shareService.selShare(uuid);
        Message message = new Message();
        if(share.getSpassword().equals(pass)){
            message.setCode("200");
            message.setMsg(uuid);
        }else{
            message.setCode("500");
        }
        return message;
    }

    @RequestMapping("/share/sfile/{uuid}")
    public String file(@PathVariable("uuid")String uuid, Model model){
        Share share = shareService.selShare(uuid);
        MyFile myFile = fileService.selByFid(share.getFid());
        model.addAttribute("file",myFile);
        return "sfile";
    }

    @ResponseBody
    @RequestMapping("/share/save")
    public Message save(int fid){
        MyFile file = fileService.selByFid(fid);
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        file.setUid(user.getUid());
        file.setFfatherpath("/root");
        file.setFtime(new Date());
        int i = fileService.saveFile(file);
        Message message = new Message();
        if(i>0){
            message.setCode("200");
            message.setMsg("保存成功!");
        }else{
            message.setCode("500");
            message.setMsg("网络异常!");
        }
        return message;

    }
}
