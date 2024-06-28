package com.example.todo.service;
import com.example.todo.repository.TaskRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.todo.entity.Task;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.ArrayList;

@Service

public class TodoListService {
    private List<String> tasks;
    public TaskRepository taskRepository;
    private static final Logger logger = LoggerFactory.getLogger(TodoListService.class);

    @Autowired
    public TodoListService(TaskRepository taskRepository) {
        tasks = new ArrayList<>();
        this.taskRepository = taskRepository;
    }

    public void addTask(String task) {
        tasks.add(task);
        System.out.println("Задача '" + task + "' добавлена в список.");
    }

    public void removeTask(String task) {
        if (tasks.contains(task)) {
            tasks.remove(task);
            System.out.println("Задача '" + task + "' удалена из списка.");
        } else {
            System.out.println("Задачи '" + task + "' нет в списке.");
        }
    }

    public void updateTask(String oldTask, String newTask) {
        if (tasks.contains(oldTask)) {
            int index = tasks.indexOf(oldTask);
            tasks.set(index, newTask);
            System.out.println("Задача '" + oldTask + "' обновлена на '" + newTask + "'.");
        } else {
            System.out.println("Задачи '" + oldTask + "' нет в списке для обновления.");
        }
    }

    public void findTask(String keyword) {
        List<String> foundTasks = new ArrayList<>();
        for (String task : tasks) {
            if (task.contains(keyword)) {
                foundTasks.add(task);
            }
        }

        if (!foundTasks.isEmpty()) {
            System.out.println("Найдены задачи по ключевому слову '" + keyword + "':");
            for (String task : foundTasks) {
                System.out.println("- " + task);
            }
        } else {
            System.out.println("Задачи по ключевому слову '" + keyword + "' не найдены.");
        }
    }


    public List<Task> searchTasks(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            throw new IllegalArgumentException("Keyword cannot be empty");
        }

        List<Task> foundTasks = taskRepository.findAllByName(keyword);

        // Логируем найденные задачи в консоль
        logger.info("Found tasks for keyword '{}': {}", keyword, foundTasks);

        return foundTasks;
    }
}
