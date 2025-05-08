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
        appendTasksToFile("Tasks.txt");
    }

    public void addWish(Wish wish) {
        wishList.add(wish);
        appendWishesToFile("Wishes.txt");
    }

    public void completeTask(int ID) {
        Task task = getTaskbyID(ID);
        if (task != null) {
            task.setCompleted(true);
            appendTasksToFile("Tasks.txt");
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
    public void wishlevel(String wishID,int level) {
    Wish wish = getWishByID(wishID);
    wish.setLevel(level);
        approveEligibleWishes();
    }
    private void approveEligibleWishes() {
        for (Wish wish : wishList) {
            if (wish.getIsApproved().equals("PENDING") && getLevel() >= wish.getLevel()&&wish.getLevel() != -1) {
                wish.setIsApproved("APPROVED");
            }
        }
    }


    public void showAllWishes() {
        boolean found = false;
        for (Wish w : wishList) {
            printWishDetails(w);
            found = true;
        }
        if (!found) {
            System.out.println("No wishes found!");
        }
    }

    public void printWishDetails(Wish wish) {
        System.out.println("────────────────────────────────────────────");
        System.out.println("🎁  Wish ID     : " + wish.getWishID());
        System.out.println("📌  Title       : " + wish.getWishName());
        System.out.println("📝  Description : " + wish.getWishDescription());
        if (wish.getStartdate() != null && wish.getStartTime() != null) {
            System.out.println("📅  Start       : " + wish.getStartdate() + " " + wish.getStartTime());
        }
        if (wish.getEnddate() != null && wish.getEndTime() != null) {
            System.out.println("📅  End         : " + wish.getEnddate() + " " + wish.getEndTime());
        }
        System.out.print("📊  Status      : ");
        String status = wish.getIsApproved().toUpperCase();
        if (status.equals("APPROVED")) {
            System.out.println("APPROVED (Approved by Parent)");
        } else if (status.equals("REJECTED")) {
            System.out.println("REJECTED (Denied by Parent)");
        } else {
            if (level < wish.getLevel()) {
                System.out.println("WAITING (Level too low)");
            } else {
                System.out.println("PENDING (Waiting for Approval)");
            }
        }
        System.out.println("────────────────────────────────────────────");
        System.out.println();
    }

    public void showAllTasks() {
        boolean found = false;
        for (Task t : tasks) {
            printTaskDetails(t);
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
                    (t.getStartdate() == null || !today.isBefore(t.getStartdate()))) {
                printTaskDetails(t);
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
            if (t.getEnddate() != null && !t.getEnddate().isBefore(Monday) && !t.getEnddate().isAfter(Sunday)) {
                printTaskDetails(t);
                isWeekly = true;
            }
        }
        if (!isWeekly) {
            System.out.println("No tasks for this week :)");
        }
    }

    public void printTaskDetails(Task task) {
        System.out.println("────────────────────────────────────────────");
        System.out.println("🆔  Task ID     : " + task.getTaskID());
        System.out.println("📝  Title       : " + task.getTaskTitle());
        System.out.println("📖  Description : " + task.getTaskDescription());
        if (task.getStartdate() != null && task.getStartTime() != null) {
            System.out.println("📅  Start       : " + task.getStartdate() + " " + task.getStartTime());
        }
        if (task.getEnddate() != null && task.getEndTime() != null) {
            System.out.println("📅  End         : " + task.getEnddate() + " " + task.getEndTime());
        }
        System.out.println("💰  Coin        : " + task.getCoin());
        System.out.println("👤  Assigned By : " + (task.getAssigner().equalsIgnoreCase("T") ? "Teacher" : "Parent"));
        System.out.println("📌  Status      : " +
                (task.isCompleted() ? "Completed ✅" : "Pending"));
        System.out.println("────────────────────────────────────────────");
        System.out.println();
    }

    public void appendTasksToFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Task t : tasks) {
                bw.write("ADD_TASK " + t.getAssigner() + " " + t.getTaskID() + " \"" +
                        t.getTaskTitle() + "\" \"" + t.getTaskDescription() + "\" " +
                        (t.getStartdate() != null ? t.getStartdate() + " " : "") +
                        (t.getStartTime() != null ? t.getStartTime() + " " : "") +
                        (t.getEnddate() != null ? t.getEnddate() + " " : "") +
                        (t.getEndTime() != null ? t.getEndTime() + " " : "") +
                        t.getCoin());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to save tasks to file!");
        }
    }

    public void appendWishesToFile(String fileName) {
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
        approveEligibleWishes();
    }
}
