package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository ) {
        this.timeEntryRepository=timeEntryRepository;
    }
    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
         TimeEntry createdTimeEntry=  timeEntryRepository.create(timeEntryToCreate);
         return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }
    @GetMapping ("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {

        TimeEntry readTimeEntry=  timeEntryRepository.find(timeEntryId);
        if (readTimeEntry==null) return new ResponseEntity<>(readTimeEntry, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(readTimeEntry, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {

        return new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK);
    }
    @PutMapping("{timeEntryId}")
    public ResponseEntity update( @PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        //if (timeEntryRepository.find(timeEntryId)==null) return null;

        TimeEntry updatedTimeEntry = timeEntryRepository.update(timeEntryId,expected);

        if (updatedTimeEntry==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(updatedTimeEntry, HttpStatus.OK);
    }
@DeleteMapping("{timeEntryId}")
    public ResponseEntity delete( @PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
