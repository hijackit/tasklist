package it.hijack.tasklist;

import io.micronaut.context.annotation.Value;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

@Controller("/task")
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    @Inject
    TaskService taskService;

    @Value("${micronaut.server.port}")
    int port;

    @EventListener
    void onStartup(StartupEvent event) {
        log.info("Open up swagger-ui at http://127.0.0.1:" + port + "/swagger/views/swagger-ui/index.html");
    }

    @Get(value = "/all", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Collection<Task>> getAll() {
        Collection<Task> tasks = taskService.getAll();
        return HttpResponse.ok(tasks);
    }

    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Task> get(@PathVariable Integer id) {
        Optional<Task> task = taskService.get(id);
        if (task.isPresent())
            return HttpResponse.ok(task.get());
        else
            return HttpResponse.notFound();
    }

    @Post(value = "/")
    public HttpResponse<Task> create(Task task) {
        Task newTask = taskService.add(task);
        return HttpResponse.created(newTask);
    }

    @Patch(value = "/{id}")
    public HttpResponse<Task> patch(@PathVariable Integer id, Task task) {
        Optional<Task> patched = taskService.patch(id, task);
        if (patched.isPresent())
            return HttpResponse.ok(patched.get());
        else
            return HttpResponse.notFound();
    }

    @Delete(value = "/{id}")
    public HttpResponse delete(@PathVariable Integer id) {
        boolean deleted = taskService.delete(id);
        if (deleted)
            return HttpResponse.ok();
        else
            return HttpResponse.notFound();
    }
}
