package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private HashMap<Long, TimeEntry> timeEntryList = new HashMap<>();


    public InMemoryTimeEntryRepository() {
    }

    public InMemoryTimeEntryRepository(HashMap<Long, TimeEntry> timeEntryList) {
        this.timeEntryList = timeEntryList;
    }

    public TimeEntry find(TimeEntry timeEntry) {
        return new TimeEntry();
    }

    public TimeEntry find(Long id) {
        return timeEntryList.get(id);
    }

    public TimeEntry create(TimeEntry timeEntry) {
        Long id = timeEntryList.size() + 1L;
        TimeEntry newTimeEntry = new TimeEntry(
                id,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );

        timeEntryList.put(id, newTimeEntry);
        return newTimeEntry;
    }

    public TimeEntry update(Long id, TimeEntry timeEntry) {
//        return new TimeEntry();
        timeEntry.setId(id);
        timeEntryList.replace(id,  timeEntry );

        return timeEntry;
    }

    public void delete(Long id) {
        timeEntryList.remove(id);
    }

    @Override
    public List<TimeEntry> list() {
        List<TimeEntry> list = new ArrayList<TimeEntry>(timeEntryList.values());
        return list;
    }


}
