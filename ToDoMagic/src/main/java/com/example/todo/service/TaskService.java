package com.example.todo.service;
import com.example.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import com.example.todo.entity.Task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private static final Logger logger = LogManager.getLogger(TaskService.class);
@Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        if (task.getName() == null || task.getDescription() == null) {
            // Обработка ошибки - некорректные данные
            throw new IllegalArgumentException("Название и описание задачи не могут быть пустыми");
        }

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // каждую секунду
    public void saveTaskInfo(String taskName, String taskDescription) {
        Task task = new Task();
        task.setName(taskName);
        task.setDescription(taskDescription);
        task.setCreatedAt(LocalDateTime.now());
        taskRepository.save(task);

        logger.info("Информация о задаче успешно сохранена в базе данных");
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }
    public void updateTask(Task task) {
        taskRepository.save(task);
    }


}

