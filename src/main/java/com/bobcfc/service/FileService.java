package com.bobcfc.service;

import com.bobcfc.entity.MyFile;

import java.util.List;

public interface FileService {
    int saveFile(MyFile file);
    List<MyFile> selByPath(String path,int uid);
}
