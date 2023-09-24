package com.dev.music.musicAPI.dto.converter;

import com.dev.music.musicAPI.dto.response.UserResponseType;
import com.dev.music.musicAPI.entities.Users;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    private ModelMapper modelMapper = new ModelMapper();
    public UserResponseType convertToDTO(Users user){
        UserResponseType response = new UserResponseType();
        response.setAvatar(user.getAvatar());
        response.setActiveStatus(user.getActiveStatus());
        response.setGender(user.isGender());
        response.setFirstName(user.getFirstName());
        response.setEmail(user.getEmail());
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setLastName(user.getLastName());
        response.setBirthDay(user.getBirthDay());
        return response;
    }
    public Users convertToEntity(UserResponseType userResponseType){
        Users users = modelMapper.map(userResponseType,Users.class);
        return users;
    }
}
