package com.LDLS.Litigation.Project.taskmanagement.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@AllArgsConstructor
@Entity
@Data
@Table(
        name = "tasks"
)
public class Task {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            name = "title"
    )
    private String title;
    @Column(
            name = "description"
    )
    private String description;
    @Column(
            name = "assigned_to"
    )
    private String assignedTo;
    @Column(
            name = "created_at"
    )
    private LocalDate createdAt;
    private String priority;
    private String status;

    public Task() {
    }

//    public Long getId() {
//        return this.id;
//    }
//
//    public String getTitle() {
//        return this.title;
//    }
//
//    public String getDescription() {
//        return this.description;
//    }
//
//    public String getAssignedTo() {
//        return this.assignedTo;
//    }
//
//    public LocalDate getCreatedAt() {
//        return this.createdAt;
//    }
//
//    public String getPriority() {
//        return this.priority;
//    }
//
//    public String getStatus() {
//        return this.status;
//    }
//
//    public void setId(final Long id) {
//        this.id = id;
//    }
//
//    public void setTitle(final String title) {
//        this.title = title;
//    }
//
//    public void setDescription(final String description) {
//        this.description = description;
//    }
//
//    public void setAssignedTo(final String assignedTo) {
//        this.assignedTo = assignedTo;
//    }
//
//    public void setCreatedAt(final LocalDate createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public void setPriority(final String priority) {
//        this.priority = priority;
//    }
//
//    public void setStatus(final String status) {
//        this.status = status;
//    }
}
