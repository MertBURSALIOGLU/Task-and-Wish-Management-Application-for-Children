import java.util.ArrayList;
import java.util.List;

public class Child {
    private ManageWish manageWish;
    private int ID;
    private String Name;
    private int level;
    private int coins;
    private List<Task> tasks;

    public Child(int ID, String Name, int level, int coins,ManageWish manageWish) {
        this.level = 1;
        this.coins = 0;
        this.manageWish = manageWish;
        this.tasks = new ArrayList<>();
    }

    public void addWish(Wish wish) {
        manageWish.addWish(wish);
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
