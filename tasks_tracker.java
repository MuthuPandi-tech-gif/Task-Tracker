public class TaskTracker {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: add | update | delete | mark | list");
            return;
        }

        String command = args[0];

        switch (command) {
            case "add":
                if (args.length < 2) {
                    System.out.println("Usage: add <description>");
                    return;
                }
                StringBuilder desc = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    desc.append(args[i]).append(" ");
                }
                TaskHandler.addTask(desc.toString().trim());
                break;

            case "update":
                if (args.length < 3) {
                    System.out.println("Usage: update <id> <new_description>");
                    return;
                }
                int updateId = Integer.parseInt(args[1]);
                StringBuilder newDesc = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    newDesc.append(args[i]).append(" ");
                }
                TaskHandler.updateTask(updateId, newDesc.toString().trim());
                break;

            case "delete":
                if (args.length != 2) {
                    System.out.println("Usage: delete <id>");
                    return;
                }
                TaskHandler.deleteTask(Integer.parseInt(args[1]));
                break;

            case "mark":
                if (args.length != 3) {
                    System.out.println("Usage: mark <id> <status>");
                    return;
                }
                TaskHandler.markTask(Integer.parseInt(args[1]), args[2]);
                break;

            case "list":
                if (args.length != 2) {
                    System.out.println("Usage: list <status>");
                    return;
                }
                TaskHandler.listTasks(args[1]);
                break;

            default:
                System.out.println("Unknown command.");
        }
    }
}

