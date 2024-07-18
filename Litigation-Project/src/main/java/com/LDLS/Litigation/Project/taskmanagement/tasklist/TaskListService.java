package com.LDLS.Litigation.Project.taskmanagement.tasklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskListService {

    private final TaskListRepository taskListRepository;

    @Autowired
    public TaskListService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    public TaskList createTaskList(TaskList taskList) {
        return taskListRepository.save(taskList);
    }

    public List<TaskList> getAllTaskLists() {
        return taskListRepository.findAll();
    }

    public TaskList getTaskListById(Long id) {
        Optional<TaskList> taskList = taskListRepository.findById(id);
        return taskList.orElse(null);
    }

    public TaskList updateTaskList(Long id, TaskList updatedTaskList) {
        Optional<TaskList> taskList = taskListRepository.findById(id);
        if (taskList.isPresent()) {
            updatedTaskList.setId(id);
            return taskListRepository.save(updatedTaskList);
        }
        return null;
    }

    public void deleteTaskList(Long id) {
        taskListRepository.deleteById(id);
    }
}
