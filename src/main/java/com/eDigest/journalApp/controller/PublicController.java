package com.eDigest.journalApp.controller;

import com.eDigest.journalApp.entity.User;
import com.eDigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    UserService userService;

    @PostMapping("/create-user" )
    public ResponseEntity<User> createNewUser(@RequestBody User user){
        try{
            User newUser = userService.createNewUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
