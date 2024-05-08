package com.LDLS.Litigation.Project.diary.service;
import com.LDLS.Litigation.Project.diary.model.Events;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
@Service
public class GoogleCalendarService {
    private static final String APPLICATION_NAME = "LDLS Litigation Project Diary";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "C:\\secrets";

    public void addEventToGoogleCalendar(Events event) throws IOException, GeneralSecurityException {
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("path/to/credentials.json"))
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/calendar"));

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        Event googleEvent = new Event()
                .setSummary(event.getShortTitle())
                .setLocation(event.getVenue())
                .setDescription(event.getDescription());

        // Convert LocalDate to java.util.Date
        Date startDate = Date.from(event.getEventDate().atStartOfDay().toInstant(ZoneOffset.UTC));
        DateTime startDateTime = new DateTime(startDate);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("UTC");
        googleEvent.setStart(start);

        // Convert LocalDate to java.util.Date for end time
        Date endDate = Date.from(event.getEventDate().atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC));
        DateTime endDateTime = new DateTime(endDate);
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("UTC");
        googleEvent.setEnd(end);

        String calendarId = "primary";
        service.events().insert(calendarId, googleEvent).execute();
    }
}
