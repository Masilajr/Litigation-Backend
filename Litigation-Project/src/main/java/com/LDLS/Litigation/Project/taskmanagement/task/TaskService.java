package com.LDLS.Litigation.Project.taskmanagement.task;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
    public Optional<Task> updateTask(Long id, Task updatedTask) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            // Update the fields with values from updatedTask
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setAssignedTo(updatedTask.getAssignedTo());
            // Assuming you want to update the createdAt field as well
            task.setCreatedAt(updatedTask.getCreatedAt());
            task.setPriority(updatedTask.getPriority());
            task.setStatus(updatedTask.getStatus());

            // Save the updated task
            Task savedTask = taskRepository.save(task);
            return Optional.of(savedTask);
        } else {
            return Optional.empty();
        }
    }

//    public Task editTask(Long id, Task updatedTask) {
//        Task existingTask = this.taskRepository.findById(id).orElse(null);
//        if (existingTask != null) {
//            existingTask.setTitle(updatedTask.getTitle());
//            existingTask.setDescription(updatedTask.getDescription());
//            existingTask.setStatus(updatedTask.getStatus());
//            existingTask.setAssignedTo(updatedTask.getAssignedTo());
//            existingTask.setPriority(updatedTask.getPriority());
//            return this.taskRepository.save(existingTask);
//        } else {
//            return null;
//        }
//    }
//public Task editTask(Long id, Task updatedTask) {
//    try {
//        Task existingTask = this.taskRepository.findById(id).orElse(null);
//        if (existingTask != null) {
//            existingTask.setTitle(updatedTask.getTitle());
//            existingTask.setDescription(updatedTask.getDescription());
//            existingTask.setStatus(updatedTask.getStatus());
//            existingTask.setAssignedTo(updatedTask.getAssignedTo());
//            existingTask.setPriority(updatedTask.getPriority());
//            return this.taskRepository.save(existingTask);
//        } else {
//            return null;
//        }
//    } catch (Exception e) {
//        log.error("Error editing task with ID {}: {}", id, e.getMessage(), e);
//        throw e;
//    }}

    public void deleteTask(Long id) {
        this.taskRepository.deleteById(id);
    }

    public List<Task> searchTasks(String title) {
        return taskRepository.findAll(new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("title"), "%" + title + "%");
            }
        });
    }
}
