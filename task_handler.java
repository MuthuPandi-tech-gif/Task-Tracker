import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskHandler {
    private static final String FILE_PATH = "tasks.json";
    private static final Gson gson = new Gson();

    public static List<Task> readTasks() {
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                Files.write(Paths.get(FILE_PATH), "[]".getBytes());
            }
            Reader reader = new FileReader(FILE_PATH);
            return gson.fromJson(reader, new TypeToken<List<Task>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void writeTasks(List<Task> tasks) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addTask(String description) {
        List<Task> tasks = readTasks();
        int id = tasks.isEmpty() ? 1 : tasks.get(tasks.size() - 1).id + 1;
        String now = getCurrentTime();

        Task task = new Task();
        task.id = id;
        task.description = description;
        task.status = "todo";
        task.createdAt = now;
        task.updatedAt = now;

        tasks.add(task);
        writeTasks(tasks);
        System.out.println("Task added!");
    }

    public static void updateTask(int id, String newDescription) {
        List<Task> tasks = readTasks();
        for (Task task : tasks) {
            if (task.id == id) {
                task.description = newDescription;
                task.updatedAt = getCurrentTime();
                writeTasks(tasks);
                System.out.println("Task updated.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public static void deleteTask(int id) {
        List<Task> tasks = readTasks();
        boolean removed = tasks.removeIf(task -> task.id == id);
        writeTasks(tasks);
        System.out.println(removed ? "Task deleted." : "Task not found.");
    }

    public static void markTask(int id, String status) {
        List<Task> tasks = readTasks();
        for (Task task : tasks) {
            if (task.id == id) {
                task.status = status;
                task.updatedAt = getCurrentTime();
                writeTasks(tasks);
                System.out.println("Task marked as " + status + ".");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public static void listTasks(String status) {
        List<Task> tasks = readTasks();
        for (Task task : tasks) {
            if (task.status.equalsIgnoreCase(status)) {
                System.out.println("[" + task.id + "] " + task.description + " (" + task.status + ")");
            }
        }
    }

    private static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
    }
}
