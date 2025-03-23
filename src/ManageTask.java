import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManageTask {
    private List<Task> taskList;
    private Child child;

    public ManageTask(Child child) {
        this.taskList = new ArrayList<>();
        this.child = child;
    }

    public void addTask(Task task) {
        taskList.add(task);
        saveTasksToFile("Tasks.txt");
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public Task getTaskbyID(int ID) {
        for (Task t : taskList) {
            if (t.getTaskID() == ID) {
                return t;
            }
        }
        return null;
    }

    public void completeTask(int ID) {
        Task task = getTaskbyID(ID);
        if (task != null) {
            task.setCompleted(true);
            saveTasksToFile("Tasks.txt");
        } else {
            System.out.println("Error: Task not found!");
        }
    }

    public void checkTask(int taskID, int rating) {
        Task t = getTaskbyID(taskID);
        t.setStatus("Approved");
        t.setRating(rating);
        int awardedCoin = t.getCoin() * rating / 5;
        child.setCoins(child.getCoins() + awardedCoin);
        saveTasksToFile("Tasks.txt");
    }

    public void showAllList() {
        boolean found = false;
        for (Task t : taskList) {
            System.out.println(toString(t));
            found = true;
        }
        if (!found) {
            System.out.println("No tasks found!");
        }
    }

    public void showDailyList() {
        LocalDate today = LocalDate.now();
        boolean isToday = false;
        for (Task t : taskList) {
            if (!today.isBefore(t.getEnddate()) &&
                    (t.getStartdate() == null || !today.isBefore(t.getStartdate())) &&
                    !t.isCompleted()) {
                System.out.println(toString(t));
                isToday = true;
            }
        }
        if (!isToday) {
            System.out.println("No tasks for today :)");
        }
    }

    public void showWeeklyList() {
        LocalDate today = LocalDate.now();
        LocalDate Monday = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate Sunday = Monday.plusDays(6);
        boolean isWeekly = false;
        for (Task t : taskList) {
            if (t.getEnddate() != null && !t.getEnddate().isBefore(Monday) && !t.getEnddate().isAfter(Sunday) && !t.isCompleted()) {
                System.out.println(toString(t));
                isWeekly = true;
            }
        }
        if (!isWeekly) {
            System.out.println("No tasks for this week :)");
        }
    }

    public String toString(Task task) {
        return "ADD_TASK " + task.getAssigner() + " " + task.getTaskID() + " \"" +
                task.getTaskTitle() + "\" \"" + task.getTaskDescription() + "\" " +
                (task.getStartdate() != null ? task.getStartdate() + " " : "") +
                (task.getStartTime() != null ? task.getStartTime() + " " : "") +
                (task.getEnddate() != null ? task.getEnddate() + " " : "") +
                (task.getEndTime() != null ? task.getEndTime() + " " : "") +
                task.getCoin();
    }

    public void saveTasksToFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Task t : taskList) {
                bw.write(toString(t));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to save tasks to file!");
        }
    }
}
