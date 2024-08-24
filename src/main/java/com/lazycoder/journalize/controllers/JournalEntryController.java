package com.lazycoder.journalize.controllers;

import com.lazycoder.journalize.Entities.JournalEntry;
import com.lazycoder.journalize.JournalApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createNewEntry(@RequestBody JournalEntry newEntry) {
        journalEntries.put(newEntry.getId(),newEntry);
        return true;
    }

    @GetMapping("/{id}")
    public JournalEntry getJournalEntryById(@PathVariable Long id){
        return journalEntries.get(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteJournalEntryById(@PathVariable Long id){
        journalEntries.remove(id);
        return true;
    }

    @PutMapping("/{id}")
    public JournalEntry updateEntryById(@PathVariable Long id, @RequestBody JournalEntry updateEntry) {
        journalEntries.put(id,updateEntry);
        return updateEntry;
    }
}
