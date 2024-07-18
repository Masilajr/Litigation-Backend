package com.LDLS.Litigation.Project.taskmanagement.tasklist;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
        name = "taskList"
)
public class TaskList {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            name = "task_name"
    )
    private String taskName;
    @Column(
            name = "assignee"
    )
    private String assignee;
    @Column(
            name = "date_assigned"
    )
    private LocalDate dateAssigned;
    @Column(
            name = "time_assigned"
    )
    private LocalTime timeAssigned;
    @Column(
            name = "due_date"
    )
    private LocalDate dueDate;
    @Column(
            name = "time_due"
    )
    private LocalTime timeDue;
    private TaskStatus status;
    private Priority priority;

    public TaskList() {
    }

    public Long getId() {
        return this.id;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public String getAssignee() {
        return this.assignee;
    }

    public LocalDate getDateAssigned() {
        return this.dateAssigned;
    }

    public LocalTime getTimeAssigned() {
        return this.timeAssigned;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public LocalTime getTimeDue() {
        return this.timeDue;
    }

    public TaskStatus getStatus() {
        return this.status;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setTaskName(final String taskName) {
        this.taskName = taskName;
    }

    public void setAssignee(final String assignee) {
        this.assignee = assignee;
    }

    public void setDateAssigned(final LocalDate dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    public void setTimeAssigned(final LocalTime timeAssigned) {
        this.timeAssigned = timeAssigned;
    }

    public void setDueDate(final LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setTimeDue(final LocalTime timeDue) {
        this.timeDue = timeDue;
    }

    public void setStatus(final TaskStatus status) {
        this.status = status;
    }

    public void setPriority(final Priority priority) {
        this.priority = priority;
    }
}