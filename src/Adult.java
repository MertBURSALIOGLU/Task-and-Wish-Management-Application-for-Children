import java.util.ArrayList;
import java.util.List;

public class Adult {
    String name;
    int ID;
    private List<Task> myTasks = new ArrayList<>();

    public Adult(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public void addTask(Child child, Task task) {
        child.addTask(task);
        myTasks.add(task);
        System.out.println("Task assigned by " + name);
    }

    public List<Task> getMyTasks() {
        return myTasks;
    }

    public void showMyTasks() {
        boolean found = false;
        for (Task t : myTasks) {
            System.out.println(toString(t));
            found = true;
        }
        if (!found) {
            System.out.println("No tasks found!");
        }
    }

    public void checkTask(Task task, int rating, Child child) {
        task.setStatus("Approved");
        task.setRating(rating);
        int awardedCoin = task.getCoin() * rating / 5;
        child.setCoins(child.getCoins() + awardedCoin);
        System.out.println("Task completed successfully with rating " + rating);
        System.out.println("Child awarded coins: " + child.getCoins());
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

    public void addCoin(Child child, int coin) {
        child.setCoins(child.getCoins() + coin);
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
