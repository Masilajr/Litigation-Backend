package com.LDLS.Litigation.Project.diary.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(
        name = "cases"
)
public class Cases {
    @Id
    private String caseNumber;
    @OneToMany(
            mappedBy = "cases",
            cascade = {CascadeType.ALL}
    )
    private List<Event> events;
    @OneToMany(
            mappedBy = "cases",
            cascade = {CascadeType.ALL}
    )
    private List<Document> documents;

    public Cases() {
    }

    public String getCaseNumber() {
        return this.caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public List<Event> getEvents() {
        return this.events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Document> getDocuments() {
        return this.documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}