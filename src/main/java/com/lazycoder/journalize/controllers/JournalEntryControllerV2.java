package com.lazycoder.journalize.controllers;

import com.lazycoder.journalize.Entities.JournalEntry;
import com.lazycoder.journalize.Entities.User;
import com.lazycoder.journalize.services.JournalEntryService;
import com.lazycoder.journalize.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v2/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalsByUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> allEntries = user.getUserEntries();

//        List<JournalEntry> allEntries = journalEntryService.getAll();
        if(allEntries != null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<?> createNewEntry(@RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try{
            journalEntryService.saveEntry(newEntry, username);
            return new ResponseEntity<>(newEntry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> journals = user.getUserEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!journals.isEmpty()){
            Optional<JournalEntry> entry= journalEntryService.getEntryById(id);
            if(entry.isPresent()) {
                return new ResponseEntity<>(entry.get(),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deleteById(id,username);
        if(removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry updateEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> journals = user.getUserEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!journals.isEmpty()){
            JournalEntry old = journalEntryService.getEntryById(id).orElse(null);
            if(old != null) {
                old.setTitle(updateEntry.getTitle() != null && !updateEntry.getTitle().equals("") ? updateEntry.getTitle() : old.getTitle());
                old.setContent(updateEntry.getContent() != null && !updateEntry.getContent().equals("") ? updateEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
