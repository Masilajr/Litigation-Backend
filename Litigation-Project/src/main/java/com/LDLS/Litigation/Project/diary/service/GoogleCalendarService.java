package com.LDLS.Litigation.Project.diary.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleCalendarService {

    private static final String APPLICATION_NAME = "Web client 1";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private Calendar getService() throws GeneralSecurityException, IOException {
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("313643481567-m7tjs01tbe06bvkh4f3urj93hiee3f6a.apps.googleusercontent.com"))
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/calendar"));
        return new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public Event createEvent(String calendarId, Event event) throws GeneralSecurityException, IOException {
        Calendar service = getService();
        return service.events().insert(calendarId, event).execute();
    }
}
