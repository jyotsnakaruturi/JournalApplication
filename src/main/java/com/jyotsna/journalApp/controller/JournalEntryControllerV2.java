package com.jyotsna.journalApp.controller;

import com.jyotsna.journalApp.entity.JournalEntry;
import com.jyotsna.journalApp.entity.User;
import com.jyotsna.journalApp.service.JournalEntryService;
import com.jyotsna.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
@Slf4j
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping("/{userName}") // GET /journal/{userName}
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<JournalEntry> all= user.getJournalEntries();
        return new ResponseEntity<>(all != null ? all : List.of(), HttpStatus.OK);
    }
    @PostMapping("/{userName}") // POST /journal/{userName}
    public  ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName){
        User user = userService.findByUserName(userName);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        }catch (Exception e){
            log.error("Failed to create journal entry for userName={}", userName, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }
    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry>journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId,@PathVariable String userName){
         User user = userService.findByUserName(userName);
         if(user == null){
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
         journalEntryService.deleteById(myId,userName);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/id/{userName}/{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId id,@RequestBody JournalEntry newEntry ,@PathVariable String userName){
        JournalEntry  old = journalEntryService.findById(id).orElse(null);
        if(old !=  null){
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
        return new ResponseEntity<>(old,HttpStatus.OK);
        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
