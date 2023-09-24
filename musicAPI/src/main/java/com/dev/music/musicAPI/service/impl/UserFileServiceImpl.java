package com.dev.music.musicAPI.service.impl;

import com.dev.music.musicAPI.dto.response.UserFileResponseType;
import com.dev.music.musicAPI.service.UserFileService;
import org.springframework.stereotype.Service;

@Service
public class UserFileServiceImpl implements UserFileService {
    @Override
    public UserFileResponseType save(UserFileResponseType userFileResponseType) {
        return null;
    }

    @Override
    public UserFileResponseType update(UserFileResponseType userFileResponseType) {
        return null;
    }

    @Override
    public UserFileResponseType findById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
