import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class TaskService {
    private final TaskRepository repository = new TaskRepository();

    public long add(String description) throws IOException {
        List<Task> tasks = list("all");
        Task task = new Task();
        task.setId(repository.loadNextTaskId());
        task.setDescription(description);
        task.setCreatedAt(LocalDateTime.now());
        tasks.add(task);
        if(repository.saveData(tasks)) {
            repository.updateNextTaskId();
            return task.getId();
        }
        return 0L;
    }
    public List<Task> list(String filter) throws FileNotFoundException, IOException {
        String match = (filter.equals("all") ? ".*\"id\":\\d+.*" : ".*\"status\":\"%s\".*".formatted(filter));
        return repository.loadData(match);
    }
}