package com.eDigest.journalApp.service;

import com.eDigest.journalApp.entity.JournalEntry;
import com.eDigest.journalApp.entity.User;
import com.eDigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private  static  final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User createNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        return userRepository.save(user);
    }

    public User createNewAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER", "ADMIN"));
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return  userRepository.findAll();
    }

    public Optional<User> findbyId(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteUser(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

}
