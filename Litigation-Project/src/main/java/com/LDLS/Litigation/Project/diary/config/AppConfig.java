package com.LDLS.Litigation.Project.diary.config;

import com.LDLS.Litigation.Project.diary.service.GoogleCalendarService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public GoogleCalendarService googleCalendarService() {
        return new GoogleCalendarService();
    }
}

