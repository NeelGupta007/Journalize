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

//    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveEntry(@NotNull JournalEntry journalEntry, String username) {
        try{
            log.info("SaveEntry function is called");
            User user = userService.findByUserName(username);

            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedDoc = journalEntryRepository.save(journalEntry);
            user.getUserEntries().add(savedDoc);
//            user.setUsername(null); // for testing Transactional annotation
            userService.saveEntry(user);
        } catch (Exception e) {
//            logger.error("Error occurred for {} : ", username, e);  // when create logger instance in class using LoggerFactory
            log.error("Error occurred for {} : ", username, e); // when using @Slf4j annotation
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

    @Transactional
    public boolean deleteById(ObjectId id, String username) {
        boolean removed = false;
        User user = userService.findByUserName(username);
        removed = user.getUserEntries().removeIf(x -> x.getId().equals(id));
        if(removed) {
            userService.saveEntry(user);
            journalEntryRepository.deleteById(id);
        }
        return removed;
    }
}
