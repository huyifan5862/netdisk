package com.bobcfc.service.impl;

import com.bobcfc.entity.Share;
import com.bobcfc.mapper.ShareMapper;
import com.bobcfc.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareServiceImpl implements ShareService {
    @Autowired
    private ShareMapper shareMapper;
    @Override
    public int insertShare(Share share) {

        return shareMapper.insertShare(share);
    }

    @Override
    public Share selShare(String uuid) {
        return shareMapper.selShare(uuid);
    }
}
