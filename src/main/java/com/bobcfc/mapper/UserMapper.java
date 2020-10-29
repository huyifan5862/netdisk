package com.bobcfc.mapper;

import com.bobcfc.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select * from user where uname=#{uname}")
    User selUserBuName(String uname);

}
