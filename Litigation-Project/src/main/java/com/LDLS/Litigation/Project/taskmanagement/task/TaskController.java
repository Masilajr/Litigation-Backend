package com.LDLS.Litigation.Project.taskmanagement.task;

import com.LDLS.Litigation.Project.Authentication.Utils.Shared.EntityResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping({"/api/tasks"})
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    TaskRepository taskRepository;
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping({"/create"})
    public ResponseEntity<EntityResponse> addTask(@RequestBody Task task) {
        EntityResponse errorResponse;
        try {
            Task savedTask = (Task)this.taskRepository.save(task);
            errorResponse = new EntityResponse();
            errorResponse.setMessage("Task added successfully");
            errorResponse.setEntity(savedTask);
            errorResponse.setStatusCode(HttpStatus.CREATED.value());
            log.info("Task added successfully: {}", savedTask);
            return ResponseEntity.status(HttpStatus.CREATED).body(errorResponse);
        } catch (Exception var4) {
            log.error("Error adding task: {}", var4.getMessage(), var4);
            errorResponse = new EntityResponse();
            errorResponse.setMessage("Error adding task");
            errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping({"/all"})
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = this.taskService.getAllTasks();
        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = this.taskService.getTaskById(id);
        return task == null ? new ResponseEntity(HttpStatus.NOT_FOUND) : new ResponseEntity(task, HttpStatus.OK);
    }

    @PutMapping({"/update"})
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task task = this.taskService.editTask(id, updatedTask);
        return task == null ? new ResponseEntity(HttpStatus.NOT_FOUND) : new ResponseEntity(task, HttpStatus.OK);
    }

    @DeleteMapping({"/delete"})
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        this.taskService.deleteTask(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
