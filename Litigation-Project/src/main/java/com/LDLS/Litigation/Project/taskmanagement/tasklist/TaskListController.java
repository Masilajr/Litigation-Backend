package com.LDLS.Litigation.Project.taskmanagement.tasklist;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/taskList"})
public class TaskListController {
    private final TaskListService taskListService;

    @Autowired
    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @PostMapping({"/create"})
    public ResponseEntity<TaskList> createTaskList(@RequestBody TaskList taskList) {
        TaskList createdTaskList = this.taskListService.createTaskList(taskList);
        return new ResponseEntity(createdTaskList, HttpStatus.CREATED);
    }

    @GetMapping({"/retrieve"})
    public ResponseEntity<List<TaskList>> getAllTaskLists() {
        List<TaskList> taskLists = this.taskListService.getAllTaskLists();
        return new ResponseEntity(taskLists, HttpStatus.OK);
    }

    @GetMapping({"/retrieve{id}"})
    public ResponseEntity<TaskList> getTaskListById(@PathVariable Long id) {
        TaskList taskList = this.taskListService.getTaskListById(id);
        return taskList == null ? new ResponseEntity(HttpStatus.NOT_FOUND) : new ResponseEntity(taskList, HttpStatus.OK);
    }

    @PutMapping({"/update{id}"})
    public ResponseEntity<TaskList> updateTaskList(@PathVariable Long id, @RequestBody TaskList updatedTaskList) {
        TaskList taskList = this.taskListService.updateTaskList(id, updatedTaskList);
        return taskList == null ? new ResponseEntity(HttpStatus.NOT_FOUND) : new ResponseEntity(taskList, HttpStatus.OK);
    }

    @DeleteMapping({"/delete{id}"})
    public ResponseEntity<Void> deleteTaskList(@PathVariable Long id) {
        this.taskListService.deleteTaskList(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
