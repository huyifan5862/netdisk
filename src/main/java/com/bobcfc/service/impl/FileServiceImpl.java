package com.bobcfc.service.impl;

import com.bobcfc.entity.MyFile;
import com.bobcfc.mapper.FileMapper;
import com.bobcfc.service.FileService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileMapper fileMapper;
    @Override
    public int saveFile(MyFile file) {

        return fileMapper.saveFile(file);
    }

    @Override
    public PageInfo<MyFile> selByPath(String path, int uid, int currentpage, int pagesize) {
        //分页
        PageHelper.startPage(currentpage,pagesize);
        List<MyFile> myFiles = fileMapper.selByPath(path, uid);
        PageInfo<MyFile> pageInfo = new PageInfo(myFiles);
        return pageInfo;
    }

    @Override
    public MyFile selPath(String ffatherpath, int uid) {
        return fileMapper.selPath(ffatherpath,uid);
    }

    @Override
    public int delFile(int fid, int uid) {
        return fileMapper.delFile(fid,uid);
    }

    @Override
    public MyFile selByFid(int fid) {
        return fileMapper.selByFid(fid);
    }
}
