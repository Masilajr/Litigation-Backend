package com.LDLS.Litigation.Project;

import com.google.api.services.calendar.model.Event;
import com.LDLS.Litigation.Project.diary.service.GoogleCalendarService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String calendarId = "bridgesmith86@gmail.com";
        String apiKey = "AIzaSyC8TZX4Vnn3CNgIkBv1LA51WezMikDFJRE";

        GoogleCalendarService calendarService = new GoogleCalendarService(apiKey);

        try {
            // Example usage: Retrieving events
            List<Event> events = calendarService.getEvents(calendarId);
            System.out.println("Events:");
            for (Event event : events) {
                System.out.println(event.getSummary());
            }

            // Example usage: Creating or updating an event
            Event newEvent = new Event().setSummary("New Event").setDescription("Description of the new event");
            calendarService.createOrUpdateEvent(calendarId, newEvent);
            System.out.println("New event created or updated successfully.");
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
