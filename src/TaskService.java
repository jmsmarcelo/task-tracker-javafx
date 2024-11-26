import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private final TaskRepository repository = new TaskRepository();

    public long add(String description) throws IOException {
        List<Task> tasks = new ArrayList<Task>();
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
}