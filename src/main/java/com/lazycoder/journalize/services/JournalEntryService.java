package com.lazycoder.journalize.services;

import com.lazycoder.journalize.Entities.JournalEntry;
import com.lazycoder.journalize.Entities.User;
import com.lazycoder.journalize.Repositories.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(@NotNull JournalEntry journalEntry, String username) {
        try{
            User user = userService.findByUserName(username);

            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedDoc = journalEntryRepository.save(journalEntry);
            user.getUserEntries().add(savedDoc);
//            user.setUsername(null); // for testing Transactional annotation
            userService.saveEntry(user);
        } catch (Exception e) {
            log.error("Exception : ", e);
            throw new RuntimeException("An error occured while saving the entry: ", e);
        }
    }

    public void saveEntry(@NotNull JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> getAllEntriesByUser(String username) {

        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String username) {
        User user = userService.findByUserName(username);
        user.getUserEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }
}
