package com.example.todo.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.todo.entity.Task;
import com.example.todo.repository.TaskRepository;
import com.example.todo.service.TaskService;
import com.example.todo.service.TodoListService;

@Controller
@RequestMapping(path = "/")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    private final TaskService taskService;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TodoListService todoListService;

    @Autowired
    public TaskController(TaskService taskService, TaskRepository taskRepository, TodoListService todoListService) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
        this.todoListService = todoListService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("task", new Task()); // Добавляем объект task в модель для передачи в шаблон
        return "index"; // Возвращаем имя шаблона, который нужно отобразить
    }

    @GetMapping("/taskSuccessNotification.html")
    public String showSuccessNotificationPage() {
        return "taskSuccessNotification";
    }


    // Это метод для обработки JSON данных

    @PostMapping("/task")
    public String handleTaskRequest(@RequestBody Task task,
                                    @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "description", required = false) String description,
                                    @RequestParam(value = "action", required = false, defaultValue = "add") String action) {

        LocalDateTime now = LocalDateTime.now();

        if (task != null) {
            task.setCreatedAt(now);
            taskService.saveTaskInfo(task.getName(), task.getDescription());
            logger.info("Added new task: " + task.getName());

        } else if (name != null && !name.isEmpty()) {
            Task newTask = new Task();
            newTask.setName(name);
            newTask.setDescription(description);
            newTask.setCreatedAt(now);

            if ("add".equals(action)) {
                taskService.saveTaskInfo(newTask.getName(), newTask.getDescription());
                logger.info("Ура, добавили новую задачу с именем: " + newTask.getName());

            } else if ("get".equals(action)) {
                taskService.saveTaskInfo(newTask.getName(), newTask.getDescription());

            } else {
                logger.error("Неизвестное действие: " + action);
            }
        } else {
            logger.error("Получено пустое значение для имени задачи");
        }

        return "taskFailed.html";
    }


    @PostMapping("/returnToTaskPage")
    public String returnToTaskPage() {
        return "index"; // Перенаправление пользователя на страницу с формой для добавления задач
    }

    @GetMapping("/searchTasksPage")
    public String showSearchTasksPage() {
        return "searchTasks";
    }
   
 @GetMapping("/search")
    public String searchPage() {
        return "searchTasks"; // Возвращает страницу search_results.html
    }

    @PostMapping("/api/searchTasks")
    @ResponseBody
    public ResponseEntity<List<Task>> searchTasks(@RequestBody Map<String, String> payload) {
        String searchQuery = payload.get("searchQuery");

        if (searchQuery != null && !searchQuery.isEmpty()) {
            try {
                List<Task> tasks = todoListService.searchTasks(searchQuery);
                return ResponseEntity.ok(tasks);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @RequestMapping(value = {"/searchTasks.html", "/searchTasks"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String searchTasksPage(@RequestParam(required = false, defaultValue = "") String searchQuery, Model model) {
        if (searchQuery.isEmpty()) {
            model.addAttribute("error", "Параметр поискового запроса обязателен");
            return "searchTasks"; // Возвращает страницу с формой, если запрос пустой
        }

        try {
            List<Task> searchResults = todoListService.searchTasks(searchQuery);
            model.addAttribute("searchResults", searchResults);

            // Выводим результаты поиска в консоль
            for (Task task : searchResults) {
                System.out.println(task.toString());
            }
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка поиска задач: " + e.getMessage());
        }

        return "search_results"; // Возвращает страницу searchResults.html с результатами поиска
    }
}

