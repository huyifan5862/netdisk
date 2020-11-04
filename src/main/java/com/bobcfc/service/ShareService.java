package com.bobcfc.service;

import com.bobcfc.entity.Share;

public interface ShareService {
    int insertShare(Share share);
    Share selShare(String uuid);
}
