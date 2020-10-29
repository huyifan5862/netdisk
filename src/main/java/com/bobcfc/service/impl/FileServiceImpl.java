package com.bobcfc.service.impl;

import com.bobcfc.entity.MyFile;
import com.bobcfc.mapper.FileMapper;
import com.bobcfc.service.FileService;
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
    public List<MyFile> selByPath(String path, int uid) {
        return fileMapper.selByPath(path,uid);
    }
}
