package com.jyotsna.journalApp.service;

import com.jyotsna.journalApp.entity.JournalEntry;
import com.jyotsna.journalApp.entity.User;
import com.jyotsna.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;
    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findByUserName(userName);
            if (user == null) {
                throw new IllegalArgumentException("User not found: " + userName);
            }
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            if (user.getJournalEntries() == null) {
                user.setJournalEntries(new ArrayList<>());
            }
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
            return saved;
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the entry.",e);
        }

    }
    public  void saveEntry(JournalEntry journalEntry){

        journalEntryRepository.save(journalEntry);

    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    public void deleteById(ObjectId id,String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }
}
