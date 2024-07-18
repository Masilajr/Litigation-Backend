package com.LDLS.Litigation.Project.diary.service;

import com.LDLS.Litigation.Project.diary.model.Events;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class EventNotificationService {
    @Autowired
    private EventService eventService;
    @Autowired
    //private SimpMessagingTemplate simpMessagingTemplate;

    public EventNotificationService() {
    }

    @Scheduled(
            fixedRate = 60000L
    )
    public void checkUpcomingEventsAndNotify() {
        LocalDate now = LocalDate.now();
        List<Events> ViewEntries = this.eventService.findUpcomingEvents(now);
        Iterator var3 = ViewEntries.iterator();

        while(var3.hasNext()) {
            Events event = (Events)var3.next();
            String var10000 = event.getShortTitle();
            String message = "Upcoming event: " + var10000 + " on " + event.getEventDate() + " at " + event.getTime();
            //this.simpMessagingTemplate.convertAndSend("/topic/upcomingEvents", message);
        }

    }
}