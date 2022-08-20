package it.hijack.tasklist;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    Collection<Task> getAll();

    Optional<Task> get(int id);

    Task add(Task task);

    Optional<Task> patch(Integer id, Task patch);

    boolean delete(int id);
}
