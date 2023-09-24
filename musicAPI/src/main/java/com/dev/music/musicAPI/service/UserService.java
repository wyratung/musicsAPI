package com.dev.music.musicAPI.service;

import com.dev.music.musicAPI.dto.response.UserResponseType;

public interface UserService {
    UserResponseType saveUser(UserResponseType userResponseType);
}
