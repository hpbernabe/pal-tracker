package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private final CounterService counter;
    private final GaugeService gauge;
    private TimeEntryRepository timeEntryRepository;


    public TimeEntryController(TimeEntryRepository timeEntryRepository,
                               CounterService counter,
                               GaugeService gauge) {
        this.timeEntryRepository = timeEntryRepository;
        this.counter = counter;
        this.gauge = gauge;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        TimeEntry newTimeEntry = timeEntryRepository.create(timeEntry);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());
        ResponseEntity responseEntity = new ResponseEntity(newTimeEntry, HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        TimeEntry timeEntry = timeEntryRepository.find(id);
        if (timeEntry != null) {
            counter.increment("TimeEntry.read");
            ResponseEntity responseEntity =  new ResponseEntity<>(timeEntry, HttpStatus.OK);
            return responseEntity;
        } else {
            ResponseEntity responseEntity =  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            return responseEntity;
        }
    }

    public TimeEntry find(long id) {
        return new TimeEntry();
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> timeEntryList = timeEntryRepository.list();
        counter.increment("TimeEntry.listed");
        return new ResponseEntity(timeEntryList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable Long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry updatedEntry = timeEntryRepository.update(id, timeEntry);

        if (updatedEntry != null) {
            counter.increment("TimeEntry.updated");
            ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<>(updatedEntry, HttpStatus.OK);
            return responseEntity;
        } else {
            ResponseEntity responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            return responseEntity;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) {
        timeEntryRepository.delete(id);
        ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());

        return responseEntity;
    }

}
