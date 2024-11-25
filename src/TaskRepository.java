import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TaskRepository {
    private static final String TASK_DATA_FILE = "task-data.json";
    private static final String NEXT_TASK_ID_FILE = "next-task-id";
    private Path nextTaskIdPath;

    public synchronized boolean saveData(List<Task> tasks) throws IOException {
        if(tasks == null) {
            return false;
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(TASK_DATA_FILE))) {
            bw.write("[");
            for(int i = 0; i < tasks.size(); i++) {
                bw.newLine();
                bw.write("\t" + tasks.get(i).toJson() + ( i < (tasks.size() - 1) ? "," : System.lineSeparator()));
            }
            bw.write("]");
            return true;
        }
    }
    public long loadNextTaskId() {
        long nextTaskId = 1;
        try {
            nextTaskIdPath = Paths.get(NEXT_TASK_ID_FILE);
            String content = Files.readString(nextTaskIdPath).trim();
            return content.matches("\\d+") ? Long.parseLong(content) : nextTaskId;
        } catch (IOException e) {
            return nextTaskId;
        }
    }
    public synchronized void updateNextTaskId() {
        try {
            Files.writeString(nextTaskIdPath, String.valueOf(loadNextTaskId() + 1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}