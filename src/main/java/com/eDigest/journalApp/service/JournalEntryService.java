package com.eDigest.journalApp.service;

import com.eDigest.journalApp.entity.JournalEntry;
import com.eDigest.journalApp.entity.User;
import com.eDigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
     public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            journalEntry.setDate(LocalDateTime.now());
            User user = userService.findByUserName(userName);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the entry.",e);
        }
     }

    public void saveEntry(JournalEntry journalEntry){
         journalEntryRepository.save(journalEntry);
    }

     public List<JournalEntry> getAllEntries(){
         return  journalEntryRepository.findAll();
     }

     public Optional<JournalEntry> findbyId(ObjectId id){
         return journalEntryRepository.findById(id);
     }

    @Transactional
     public boolean deleteEntry(ObjectId id, String userName){
        try{
            boolean removed = false;
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
            return removed;
        }catch(Exception e){
            throw new RuntimeException("An error occured while deleting" + e);
        }
     }
}
