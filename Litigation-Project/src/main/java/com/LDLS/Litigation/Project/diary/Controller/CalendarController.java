//package com.LDLS.Litigation.Project.diary.Controller;
//
//import com.LDLS.Litigation.Project.diary.model.Events;
//import com.LDLS.Litigation.Project.diary.service.GoogleCalendarService;
//import com.google.api.services.calendar.model.Event;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//
//public class CalendarController {
//
//    private final GoogleCalendarService googleCalendarService;
//
//    public CalendarController(GoogleCalendarService googleCalendarService) {
//        this.googleCalendarService = googleCalendarService;
//    }
//
//    @PostMapping("/calendar/event")
//    public void addEvent(@RequestBody Events eventModel) throws Exception {
//        Event googleCalendarEvent = eventModel.toGoogleCalendarEvent();
//        googleCalendarService.createEvent("primary", googleCalendarEvent);
//    }
//}
//
