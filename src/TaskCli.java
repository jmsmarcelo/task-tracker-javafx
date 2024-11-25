public class TaskCli {
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

    }
    private static void handleUpdateCommand(String[] args) {

    }
    private static void handleDeleteCommand(String[] args) {

    }
    private static void handleMarkAsCommand(String[] args, TaskStatus status) {

    }
    private static void handleListCommand(String[] args) {

    }
    private static void handleHelpCommand() {

    }
}