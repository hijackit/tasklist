package it.hijack.tasklist;

import io.micronaut.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskControllerTest {

    private MemTaskService taskService;
    private TaskController taskController;

    @BeforeEach
    public void cleanUp() {
        taskService = new MemTaskService();
        taskController = new TaskController();
        taskController.taskService = taskService;
    }

    @Test
    public void canInsertTask() {
        // action
        HttpResponse<Task> response = taskController.create(newTask("new task"));

        // verify
        assertEquals(201, response.code());
    }

    @Test
    public void canGetTask() {
        // setup
        Task createdTask = taskService.add(newTask("new task"));

        // action
        HttpResponse<Task> response = taskController.get(createdTask.getId());

        // verify
        assertEquals(200, response.code());
        assertEquals(1, response.body().getId());
        assertEquals("new task", response.body().getTitle());
        assertEquals(false, response.body().isCompleted());
    }

    @Test
    public void taskNotFound() {
        // action
        HttpResponse<Task> response = taskController.get(64);

        // verify
        assertEquals(404, response.code());
    }

    @Test
    public void canUpdateTask() {
        // setup
        Task createdTask = taskService.add(newTask("new task"));

        // action
        Task patch = new Task();
        patch.setCompleted(true);
        HttpResponse<Task> response = taskController.patch(createdTask.getId(), patch);

        // verify
        assertEquals(200, response.code());
        assertEquals(true, response.body().isCompleted());
    }

    @Test
    public void canDeleteTask() {
        // setup
        Task createdTask = taskService.add(newTask("new task"));

        // action
        HttpResponse<Task> response = taskController.delete(createdTask.getId());

        // verify
        assertEquals(200, response.code());
        assertEquals(false, taskService.tasks.containsKey(createdTask.getId()));
    }

    private Task newTask(String title) {
        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);
        return task;
    }
}