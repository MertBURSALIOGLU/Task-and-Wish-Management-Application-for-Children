public class Adult {
    protected ManageTask manageTask;
    String name;
    int ID;

    public Adult(String name, int ID, ManageTask manageTask) {
        this.name = name;
        this.ID = ID;
        this.manageTask = manageTask;
    }

    public void addTask(Task task) {
        manageTask.addTask(task);
        System.out.println("Task assigned by " + name);
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

    public ManageTask getManageTask() {
        return manageTask;
    }

    public void setManageTask(ManageTask manageTask) {
        this.manageTask = manageTask;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
