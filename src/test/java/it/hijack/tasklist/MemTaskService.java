package it.hijack.tasklist;

import java.util.*;

/**
 * An in-memory task service for tests
 */
public class MemTaskService implements TaskService {

    Map<Integer, Task> tasks = new HashMap<>();
    private int nextInt = 1;

    @Override
    public Collection<Task> getAll() {
        return tasks.values();
    }

    @Override
    public Optional<Task> get(int id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public Task add(Task task) {
        task.setId(nextInt++);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> patch(Integer id, Task patch) {
        Task task = tasks.get(id);
        if (patch.getTitle() != null)
            task.setTitle(patch.getTitle());

        if (patch.isCompleted() != null)
            task.setCompleted(patch.isCompleted());

        return Optional.of(task);
    }

    @Override
    public boolean delete(int id) {
        return tasks.remove(id) != null;
    }
}
