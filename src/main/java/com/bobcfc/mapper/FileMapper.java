package com.bobcfc.mapper;

import com.bobcfc.entity.MyFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileMapper {
    int saveFile(MyFile file);
    List<MyFile> selByPath(@Param("path") String path,@Param("uid") int uid);
}
