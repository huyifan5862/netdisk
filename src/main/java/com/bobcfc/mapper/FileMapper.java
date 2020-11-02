package com.bobcfc.mapper;

import com.bobcfc.entity.MyFile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileMapper {
    int saveFile(MyFile file);
    List<MyFile> selByPath(@Param("path") String path,@Param("uid") int uid);
    MyFile selPath(@Param("ffatherpath") String ffatherpath, @Param("uid") int uid);
    @Delete("delete from file where fid=#{fid} and uid=#{uid}")
    int delFile(@Param("fid") int fid, int uid);
}
