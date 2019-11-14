package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository , MeterRegistry meterRegistry) {
        this.timeEntryRepository=timeEntryRepository;

        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
         TimeEntry createdTimeEntry=  timeEntryRepository.create(timeEntryToCreate);
          actionCounter.increment();
         timeEntrySummary.record(timeEntryRepository.list().size());
         return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }
    @GetMapping ("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {

        TimeEntry readTimeEntry=  timeEntryRepository.find(timeEntryId);
        if (readTimeEntry==null) {
            return new ResponseEntity<>(readTimeEntry, HttpStatus.NOT_FOUND);
        }
        else
            actionCounter.increment();
            return new ResponseEntity<>(readTimeEntry, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {

        return new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK);
    }
    @PutMapping("{timeEntryId}")
    public ResponseEntity update( @PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        //if (timeEntryRepository.find(timeEntryId)==null) return null;
        actionCounter.increment();
        TimeEntry updatedTimeEntry = timeEntryRepository.update(timeEntryId,expected);

        if (updatedTimeEntry==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            actionCounter.increment();
            return new ResponseEntity<>(updatedTimeEntry, HttpStatus.OK);
    }
@DeleteMapping("{timeEntryId}")
    public ResponseEntity delete( @PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
