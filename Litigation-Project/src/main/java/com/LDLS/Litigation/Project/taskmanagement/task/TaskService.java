package com.LDLS.Litigation.Project.taskmanagement.task;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTaskById(Long id) {
        return this.taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " not found"));
    }


    public List<Task> getAllTasks() {
        return this.taskRepository.findAll();
    }
    public Task editTask(Long id, Task updatedTask) {
        Task existingTask = this.taskRepository.findById(id).orElse(null);
        if (existingTask != null) {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setStatus(updatedTask.getStatus());
            existingTask.setAssignedTo(updatedTask.getAssignedTo());
            existingTask.setPriority(updatedTask.getPriority());
            return this.taskRepository.save(existingTask);
        } else {
            return null;
        }
    }

    public void deleteTask(Long id) {
        this.taskRepository.deleteById(id);
    }
}
