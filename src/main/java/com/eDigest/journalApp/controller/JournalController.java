package com.eDigest.journalApp.controller;

import com.eDigest.journalApp.entity.JournalEntry;
import com.eDigest.journalApp.entity.User;
import com.eDigest.journalApp.service.JournalEntryService;
import com.eDigest.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> all  =  user.getJournalEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry newEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(newEntry, userName) ;
            log.info("HHHHHHH");
            log.error("HHHHHHH");
            log.warn("HHHHHHH");
            log.debug("HHHHHHH");
            log.trace("HHHHHHH");
            return new ResponseEntity<JournalEntry>(newEntry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("entry/{id}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(
                x-> x.getId().equals(id)
        ).toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findbyId(id);
            return new ResponseEntity<>(journalEntry, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalEntryService.deleteEntry(id, userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry editEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(
                x-> x.getId().equals(id)
        ).toList();
        if(!collect.isEmpty()){
            JournalEntry old = journalEntryService.findbyId(id).orElse(null);
            if(old != null){
                old.setTitle(editEntry.getTitle() != null && !editEntry.getTitle().equals("") ? editEntry.getTitle() : old.getTitle());
                old.setContent(editEntry.getContent() != null && !editEntry.getContent().equals("") ? editEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<JournalEntry>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

}
