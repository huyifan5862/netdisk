package com.bobcfc.service;

import com.bobcfc.entity.MyFile;
import com.github.pagehelper.PageInfo;

public interface FileService {
    int saveFile(MyFile file);
    PageInfo<MyFile> selByPath(String path, int uid, int currentpage, int pagesize);
    MyFile selPath(String ffatherpath,int uid);
    int delFile(int fid,int uid);
    MyFile selByFid(int fid);
}
