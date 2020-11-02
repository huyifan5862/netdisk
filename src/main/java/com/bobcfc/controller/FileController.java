package com.bobcfc.controller;

import com.bobcfc.entity.Message;
import com.bobcfc.entity.MyFile;
import com.bobcfc.entity.User;
import com.bobcfc.service.FileService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping("/upload")
    public Map upload(MultipartFile file, HttpSession session) throws IOException {
        String fileName = file.getOriginalFilename();
        System.out.println("fileName = " + fileName);
        long size = file.getSize();
        System.out.println("size = " + size);
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        int uid = user.getUid();
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");

        Map map = new HashMap();

        try {

            file.transferTo(new File("D:\\netdisk\\" + uuid));
        } catch (Exception e) {
            map.put("code", "500");
            map.put("msg", "网络异常,请稍后再试!");
            return map;
        }

        MyFile myFile = new MyFile();
        String[] split = fileName.split("[.]");
        if (split.length > 1) {
            String s = split[split.length - 1];
            System.out.println("s = " + s);
            if ("png".equalsIgnoreCase(s) || "bmp".equalsIgnoreCase(s) || "jgp".equalsIgnoreCase(s)) {
                myFile.setIsfile(2);
            } else myFile.setIsfile(1);
        } else {
            myFile.setIsfile(1);
        }

        //todo  获取所在目录  保存信息到数据库
        String path = (String) session.getAttribute("path");
        myFile.setFname(uuid);
        myFile.setFrealname(fileName);
        myFile.setUid(uid);
        myFile.setFfatherpath(path);
        myFile.setFsize(size);
        myFile.setFpath("D:\\netdisk");
        Date date = new Date();
        myFile.setFtime(date);
        int i = fileService.saveFile(myFile);
        String code = "";
        String msg = "";
        if (i == 1) {
            code = "200";
            msg = "上传成功!";

        } else {
            code = "500";
            msg = "网络异常,请稍后再试!";
        }
        map.put("code", code);
        map.put("msg", msg);
        return map;

    }

    @RequestMapping("/createPath")
    public Map createPath(String name, HttpSession session) {
        MyFile myFile = new MyFile();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        int uid = user.getUid();
        myFile.setUid(uid);
        myFile.setFname(name);
        myFile.setFrealname(name);
        String path = (String) session.getAttribute("path");
        myFile.setFfatherpath("/root");
        myFile.setFpath(path+"/"+name);
        int i = fileService.saveFile(myFile);
        Map map = new HashMap();
        String code = "";
        String msg = "";
        if (i == 1) {
            code = "200";
            msg = "创建成功!";

        } else {
            code = "500";
            msg = "网络异常,请稍后再试!";
        }
        map.put("code", code);
        map.put("msg", msg);

        return map;
    }

    @RequestMapping("/search")
    public Message toPath(String path, @RequestParam(defaultValue = "1") int currentpage, HttpSession session) {
        int pagesize=10;
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        int uid = user.getUid();
        System.out.println("path = " + path);
        String path1 = path;
        //获取所有父级目录
        LinkedList<Map<String,String>> ll = new LinkedList<>();
        while (!"/".equals(path)) {
            Map map = new HashMap();
            MyFile myFile = fileService.selPath(path,uid);
            map.put(myFile.getFname(),myFile.getFpath());
            ll.addFirst(map);
            path = myFile.getFfatherpath();
        }


        PageInfo<MyFile> myFiles = fileService.selByPath(path1, uid,currentpage,pagesize);
        Message message = new Message();
        message.setCode("200");
        message.setMsg("查询成功!");
        message.setPath(ll);
        message.setData(myFiles);

        session.setAttribute("path",path1);

        return message;

    }

    @RequestMapping("/down")
    public void down(String fname, String frealname, HttpServletResponse response) {
        try {
            System.out.println("fname = " + fname + "frealname=" + frealname);
            FileInputStream fis = new FileInputStream(new File("D:\\netdisk\\" + fname));
            response.setContentType("application/force-download");
            String filename = new String(frealname.getBytes("GBK"), "ISO-8859-1");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            byte[] buff = new byte[1024];
            //创建缓冲输入流
            OutputStream outputStream = null;
            try {
                outputStream = response.getOutputStream();
                int read = 0;
                //通过while循环写入到指定了的文件夹中
                while ((read = fis.read(buff)) > 0) {
                    outputStream.write(buff, 0, read);
                }
            } catch (IOException e) {
                e.printStackTrace();
                //出现异常返回给页面失败的信息
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("网络异常!");
        }

    }
    @RequestMapping("/img")
    public byte[] img(String fname) throws IOException {
        File file = new File("D:/netdisk/"+fname);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;

    }

    @RequestMapping("/del")
    public Message delFile(int fid){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        int uid = user.getUid();
        int i = fileService.delFile(fid, uid);
        Message message = new Message();
        if(i>0){
            message.setCode("200");
            message.setMsg("删除成功!");
        }else{
            message.setCode("500");
            message.setMsg("网络异常!");
        }
        return message;
    }
}
