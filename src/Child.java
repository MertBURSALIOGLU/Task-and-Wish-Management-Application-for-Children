import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Child {
    private int ID;
    private String Name;
    private int level;
    private int coins;
    private List<Task> tasks;
    private List<Wish> wishList;

    public Child(int ID, String Name, int level, int coins) {
        this.ID = ID;
        this.Name = Name;
        this.level = 1;
        this.coins = 0;
        this.tasks = new ArrayList<>();
        this.wishList = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTasksToFile("Tasks.txt");
    }

    public void addWish(Wish wish) {
        wishList.add(wish);
        saveWishesToFile("Wishes.txt");
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

    public Task getTaskbyID(int ID) {
        for (Task t : tasks) {
            if (t.getTaskID() == ID) {
                return t;
            }
        }
        return null;
    }

    public Wish getWishByID(String wishID) {
        for (Wish w : wishList) {
            if (w.getWishID().equals(wishID)) {
                return w;
            }
        }
        return null;
    }

    public void showAllWishes() {
        boolean found = false;
        for (Wish w : wishList) {
            System.out.println(toString(w, level));
            found = true;
        }
        if (!found) {
            System.out.println("No wishes found!");
        }
    }

    public String toString(Wish wish, int childLevel) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(wish.getWishID()).append(" | ");
        sb.append("Title: ").append(wish.getWishName()).append(" | ");
        sb.append("Description: ").append(wish.getWishDescription()).append(" | ");
        sb.append("Status: ");
        String status = wish.getIsApproved().toUpperCase();
        if (status.equals("APPROVED")) {
            sb.append("APPROVED (Approved by Parent)");
        } else if (status.equals("REJECTED")) {
            sb.append("REJECTED (Denied by Parent)");
        } else {
            if (childLevel < wish.getLevel()) {
                sb.append("WAITING (Level too low)");
            } else {
                sb.append("PENDING (Waiting for Approval)");
            }
        }
        return sb.toString();
    }

    public void showAllTasks() {
        boolean found = false;
        for (Task t : tasks) {
            System.out.println(toString(t));
            found = true;
        }
        if (!found) {
            System.out.println("No tasks found!");
        }
    }

    public void showDailyTasks() {
        java.time.LocalDate today = java.time.LocalDate.now();
        boolean isToday = false;
        for (Task t : tasks) {
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

    public void showWeeklyTasks() {
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalDate Monday = today.minusDays(today.getDayOfWeek().getValue() - 1);
        java.time.LocalDate Sunday = Monday.plusDays(6);
        boolean isWeekly = false;
        for (Task t : tasks) {
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
            for (Task t : tasks) {
                bw.write(toString(t));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to save tasks to file!");
        }
    }

    public void saveWishesToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Wish w : wishList) {
                writer.write("ADD_WISH " + w.getWishID() + " \"" + w.getWishName() + "\" \"" + w.getWishDescription() + "\" " +
                        (w.getStartdate() != null ? w.getStartdate() + " " : "") +
                        (w.getStartTime() != null ? w.getStartTime() + " " : "") +
                        (w.getEnddate() != null ? w.getEnddate() + " " : "") +
                        (w.getEndTime() != null ? w.getEndTime() + " " : ""));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to save wishes to file!");
        }
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
        updateLevel();
    }

    public int getLevel() {
        return level;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    private void updateLevel() {
        if (coins > 0 && coins <= 40) this.level = 1;
        else if (coins > 40 && coins <= 60) this.level = 2;
        else if (coins > 60 && coins <= 80) this.level = 3;
        else this.level = 4;
    }
}
