package com.bobcfc.service.impl;

import com.bobcfc.entity.User;
import com.bobcfc.mapper.UserMapper;
import com.bobcfc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User selUserByName(String uname) {
        return userMapper.selUserBuName(uname);
    }
}
