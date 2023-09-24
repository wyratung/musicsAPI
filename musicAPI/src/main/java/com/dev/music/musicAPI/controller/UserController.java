package com.dev.music.musicAPI.controller;

import com.dev.music.musicAPI.dto.response.UserResponseType;
import com.dev.music.musicAPI.service.UserService;
import com.dev.music.musicAPI.service.impl.FileStorageServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    public UserController(UserService userService, FileStorageServiceImpl fileStorageService) {
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }
    @PostMapping("/save")
    public ResponseEntity<UserResponseType> saveUser(@RequestParam("user") String userJson,
                                                     @RequestParam(value = "image",required = false)MultipartFile image) throws JsonProcessingException {
        String imageUrl = "";
        ResponseEntity<UserResponseType> pResponse;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        UserResponseType userResponseType = mapper.readValue(userJson,UserResponseType.class);
        if(null != image){
            imageUrl = fileStorageService.storeFile(image);
            userResponseType.setAvatar(imageUrl);
        }
        pResponse = new ResponseEntity<>( userService.saveUser(userResponseType), HttpStatus.OK);
        return pResponse;
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponseType> registerUser(@RequestParam("user") String userJson) throws JsonProcessingException {
        ResponseEntity<UserResponseType> pResponse;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        UserResponseType userResponseType = mapper.readValue(userJson,UserResponseType.class);
        pResponse = new ResponseEntity<>( userService.saveUser(userResponseType), HttpStatus.OK);
        return pResponse;
    }
}

