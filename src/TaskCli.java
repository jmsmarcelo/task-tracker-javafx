import java.io.IOException;

public class TaskCli {
    private static final TaskService service = new TaskService();

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("No command found\nUse 'help' for a list of commands");
            return;
        }
        switch(args[0]) {
            case "add" -> handleAddCommand(args);
            case "update" -> handleUpdateCommand(args);
            case "delete" -> handleDeleteCommand(args);
            case "mark-in-progress" -> handleMarkAsCommand(args, TaskStatus.IN_PROGRESS);
            case "mark-done" -> handleMarkAsCommand(args, TaskStatus.DONE);
            case "list" -> handleListCommand(args);
            case "help" -> handleHelpCommand();
            default -> System.out.println("Invalid command\nUse 'help' for a list of commands");
        }
    }
    private static void handleAddCommand(String[] args) {
        if(args.length != 2) {
            System.out.print("""
                Invalid command.
                Usage:      add <description>
                Example:    add "Buy groceries"
                """);
            return;
        }
        if(args[1].isBlank()) {
            System.out.println("Task no description is not allowed");
            return;
        }
        try {
            Long id = service.add(args[1]);
            if(id == 0) {
                System.out.println("No Task found for add");
            } else {
                System.out.println("Task added successfully (ID: %d)".formatted(id));
            }
        } catch (IOException e) {
            System.out.print("""
                Error:              Could not add the task to data file
                Possible reason:    No permission to write to file
                """);
        }
    }
    private static void handleUpdateCommand(String[] args) {

    }
    private static void handleDeleteCommand(String[] args) {

    }
    private static void handleMarkAsCommand(String[] args, TaskStatus status) {

    }
    private static void handleListCommand(String[] args) {
        try {
            if(args.length == 1) {
                service.list("all").forEach(System.out::println);
            } else if(args.length == 2 && TaskStatus.isValid(args[1])) {
                service.list(args[1]).forEach(System.out::println);
            } else {
                System.out.print("""
                    Invalid command
                    Usage:      list [%s]
                    Example:    list
                                list done
                    """.formatted(TaskStatus.valuesJoin("|")));
            }
        } catch (IOException e) {
            System.out.print("""
                Error:              Could not load the tasks from data file
                Possible reason:    No permission to read to file
                """);
        }
    }
    private static void handleHelpCommand() {

    }
}