package io.pivotal.pal.tracker;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private HashMap<Long, TimeEntry> timeEntryMap = new HashMap<Long, TimeEntry>();
     long myId = 0L;
    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        long id = myId+1;
        this.myId=id;
        timeEntry.setId(id);
        timeEntryMap.put(id,timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
    //    System.out.println(timeEntryMap.get(id).getProjectId());
       return timeEntryMap.get(id);
    }

    @Override
    public List list() {
        List values = new ArrayList(timeEntryMap.values());
        return values;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if (timeEntryMap.get(id)==null) return null;


       timeEntry.setId(id);
        timeEntryMap.put(id,timeEntry);
       return timeEntryMap.get(id);
    }

    @Override
    public void delete(long id) {
        timeEntryMap.remove(id);
    }
}
