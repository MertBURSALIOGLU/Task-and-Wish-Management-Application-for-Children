import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Operations {
    private Teacher teacher;
    private Parent parent;
    private Child child;

    public Operations(Teacher teacher, Parent parent, Child child) {
        this.teacher = teacher;
        this.parent = parent;
        this.child = child;
    }

    public void operationselector(List<String> prt) {
        if (prt == null || prt.isEmpty()) {
            System.out.println("❗ Error: Empty input detected!");
            return;
        }

        String op = prt.get(0);

        if (op.startsWith("ADD_TASK")) {
            operationAddTask(prt);
        } else if (op.equalsIgnoreCase("LIST_ALL_TASKS")) {
            operationListTask(prt);
        } else if (op.equalsIgnoreCase("TASK_CHECKED")) {
            operationCheckTask(prt);
        } else if (op.equalsIgnoreCase("TASK_DONE")) {
            operationCompleteTask(prt);
        } else if (op.startsWith("ADD_WISH")) {
            operationAddWish(prt);
        } else if (op.equalsIgnoreCase("LIST_ALL_WISHES")) {
            operationListWish(prt);
        } else if (op.equalsIgnoreCase("ADD_BUDGET_COIN")) {
            operationAddCoin(prt);
        } else if (op.equalsIgnoreCase("WISH_CHECKED")) {
            operationCheckWish(prt);
        } else if (op.equalsIgnoreCase("PRINT_BUDGET")) {
            operationPrintBudget(prt);
        } else if (op.equalsIgnoreCase("PRINT_STATUS")) {
            operationPrintStatus(prt);
        } else {
            System.out.println("❗ Error: Invalid command!");
            System.out.println("────────────────────────────────────────────");
        }
    }

    private void operationPrintStatus(List<String> prt) {
        if (prt.size() > 1) {
            System.out.println("ℹ️ Status printing operation does not require any parameters!");
        } else {
            System.out.println("🎮 Current level: " + child.getLevel());
            System.out.println("────────────────────────────────────────────");
        }
    }

    private void operationPrintBudget(List<String> prt) {
        if (prt.size() > 1) {
            System.out.println("ℹ️ Budget printing operation does not require any parameters!");
        } else {
            System.out.println("💰 Current budget: " + child.getCoins());
            System.out.println("────────────────────────────────────────────");
        }
    }

    private void operationCheckWish(List<String> prt) {
        if (prt.size() < 3) {
            System.out.println("⚠️ CHECK_WISH operation needs more parameters! (Wish ID, Wish Status, Optional(Level))");
            System.out.println("────────────────────────────────────────────");
            return;
        }

        String wishID = prt.get(1);
        String status = prt.get(2).toUpperCase();
        int level = (prt.size() == 4) ? Integer.parseInt(prt.get(3)) : -1;

        if (status.equals("APPROVED")) {
            if (level == -1 || child.getLevel() >= level) {
                parent.approveWish(child, wishID, "APPROVED");
                System.out.println("🟢 Wish " + wishID + " approved.");
            } else {
                child.wishleveladder(wishID,level);
                System.out.println("🟡 Wish will be approved when child meet the level!.");
            }
        } else if (status.equals("REJECTED")) {
            parent.approveWish(child, wishID, "REJECTED");
            System.out.println("🔴 Wish " + wishID + " rejected.");
        } else {
            System.out.println("❗ Error: Invalid command!");
        }

        System.out.println("────────────────────────────────────────────");
    }




    private void operationAddCoin(List<String> prt) {
        if (prt.size() == 2) {
            int coin = Integer.parseInt(prt.get(1));
            parent.addCoin(child, coin);
            System.out.println("💰 " + coin + " coins added to child.");
            System.out.println("────────────────────────────────────────────");
        }
    }

    private void operationListWish(List<String> prt) {
        if (prt.size() == 1) {
            child.showAllWishes();
        } else {
            System.out.println("⚠️ Too many parameters for LIST_ALL_WISHES");
            System.out.println("────────────────────────────────────────────");
        }
    }

    public void operationAddWish(List<String> prt) {
        if (prt.size() < 4) {
            System.out.println("❗ Error: Wish adding operation requires at least 4 parameters!");
            System.out.println("────────────────────────────────────────────");
            return;
        }

        String wishId = prt.get(1).trim();
        String wishTitle = prt.get(2).trim();
        String wishDescription = prt.get(3).trim();

        String startDate = null, endDate = null, startTime = null, endTime = null;
        int i = 4;

        while (i < prt.size()) {
            String data = prt.get(i);

            if (isDate(data)) {
                if (endDate == null) {
                    endDate = data;
                } else if (startDate == null) {
                    startDate = endDate;
                    endDate = data;
                } else {
                    System.out.println("❗ Error: Too many dates detected!");
                    System.out.println("────────────────────────────────────────────");
                    return;
                }
            } else if (isTime(data)) {
                if (endTime == null && endDate != null) {
                    endTime = data;
                } else if (startTime == null && startDate != null) {
                    startTime = endTime;
                    endTime = data;
                } else {
                    System.out.println("❗ Error: Too many times detected!");
                    System.out.println("────────────────────────────────────────────");
                    return;
                }
            } else {
                System.out.println("❗ Error: Unrecognized parameter: " + data);
                System.out.println("────────────────────────────────────────────");
                return;
            }

            i++;
        }

        Wish wish = new Wish(wishId, wishTitle, wishDescription, child.getLevel(), startDate, startTime, endDate, endTime, "PENDING");
        child.addWish(wish);

        System.out.println("🎁 Wish Added Successfully!");
        child.printWishDetails(wish);
    }
    private void operationCompleteTask(List<String> prt) {
        if (prt.size() == 2) {
            int taskId;
            try {
                taskId = Integer.parseInt(prt.get(1));
            } catch (NumberFormatException e) {
                System.out.println("❗ Error: Task ID must be a number!");
                System.out.println("────────────────────────────────────────────");
                return;
            }
            child.completeTask(taskId);
            System.out.println("✅ Task " + taskId + " completed successfully!");
            System.out.println("────────────────────────────────────────────");
        } else {
            System.out.println("⚠️ TASK_DONE operation needs 1 parameter! (Task ID)");
            System.out.println("────────────────────────────────────────────");
        }
    }

    private void operationCheckTask(List<String> prt) {
        if (prt.size() == 3) {
            int taskId;
            int rating;
            try {
                taskId = Integer.parseInt(prt.get(1));
                rating = Integer.parseInt(prt.get(2));
            } catch (NumberFormatException e) {
                System.out.println("❗ Error: Task ID and Rating must be numbers!");
                System.out.println("────────────────────────────────────────────");
                return;
            }

            if (rating < 1 || rating > 5) {
                System.out.println("❗ Error: Rating must be between 1 and 5!");
                System.out.println("────────────────────────────────────────────");
                return;
            }

            Task task = child.getTaskbyID(taskId);
            if (task == null) {
                System.out.println("❗ Error: Task ID not found!");
                System.out.println("────────────────────────────────────────────");
                return;
            }

            if (!task.isCompleted()) {
                System.out.println("⚠️ Error: Task is not completed yet!");
                System.out.println("────────────────────────────────────────────");
                return;
            }

            int earned = task.getCoin() * rating / 5;
            System.out.println("⭐ Task " + taskId+" completed successfully with rating " + rating);
            System.out.println("💰 Coins awarded: " + earned);
            System.out.println("────────────────────────────────────────────");

            if (task.getAssigner().equalsIgnoreCase("Teacher")) {
                teacher.checkTask(task, rating, child);
            } else {
                parent.checkTask(task, rating, child);
            }

        } else {
            System.out.println("⚠️ TASK_CHECKED operation needs 2 parameters! (Task ID & Rating)");
            System.out.println("────────────────────────────────────────────");
        }
    }

    private void operationListTask(List<String> prt) {
        if (prt.size() == 2) {
            String time = prt.get(1).toUpperCase();
            if (time.equals("D")) {
                child.showDailyTasks();
            } else if (time.equals("W")) {
                child.showWeeklyTasks();
            } else {
                System.out.println("⚠️ Invalid parameter! Use D for daily or W for weekly.");
                System.out.println("────────────────────────────────────────────");
            }
        } else if (prt.size() == 1) {
            child.showAllTasks();
        } else {
            System.out.println("⚠️ Too many parameters for LIST_ALL_TASKS");
            System.out.println("────────────────────────────────────────────");
        }
    }

    public void operationAddTask(List<String> prt) {
        if (prt.size() < 6) {
            System.out.println("❗ Error: Task adding operation requires at least 6 parameters!");
            System.out.println("────────────────────────────────────────────");
            return;
        }

        String assigner = prt.get(1);
        if (!assigner.equalsIgnoreCase("P") && !assigner.equalsIgnoreCase("T")) {
            System.out.println("❗ Error: Invalid assigner! Only 'T' (Teacher) or 'P' (Parent) can add tasks.");
            System.out.println("────────────────────────────────────────────");
            return;
        }

        int taskId;
        try {
            taskId = Integer.parseInt(prt.get(2));
        } catch (NumberFormatException e) {
            System.out.println("❗ Error: Task ID must be a number!");
            System.out.println("────────────────────────────────────────────");
            return;
        }

        String taskTitle = prt.get(3).trim();
        String taskDescription = prt.get(4).trim();

        String startDate = null, endDate = null, startTime = null, endTime = null;
        int i = 5;

        while (i < prt.size() - 1) {
            String data = prt.get(i);

            if (isDate(data)) {
                if (endDate == null) {
                    endDate = data;
                } else if (startDate == null) {
                    startDate = endDate;
                    endDate = data;
                } else {
                    System.out.println("❗ Error: Too many dates detected!");
                    System.out.println("────────────────────────────────────────────");
                    return;
                }
            } else if (isTime(data)) {
                if (endTime == null && endDate != null) {
                    endTime = data;
                } else if (startTime == null && startDate != null) {
                    startTime = endTime;
                    endTime = data;
                } else {
                    System.out.println("❗ Error: Too many times detected!");
                    System.out.println("────────────────────────────────────────────");
                    return;
                }
            } else {
                System.out.println("❗ Error: Unrecognized parameter: " + data);
                System.out.println("────────────────────────────────────────────");
                return;
            }

            i++;
        }

        int coin;
        try {
            coin = Integer.parseInt(prt.get(prt.size() - 1));
            if (coin < 0) {
                System.out.println("❗ Error: Coin value cannot be negative!");
                System.out.println("────────────────────────────────────────────");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("❗ Error: Coin value must be a valid number!");
            System.out.println("────────────────────────────────────────────");
            return;
        }

        Task task = new Task(assigner, taskId, taskTitle, taskDescription, startDate, startTime, endDate, endTime, coin, false, "Pending");

        if (assigner.equalsIgnoreCase("T")) {
            teacher.addTask(child, task);
        } else {
            parent.addTask(child, task);
        }

        System.out.println("🎯 Task Added Successfully!");
        child.printTaskDetails(task);
    }

    public boolean isDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean isTime(String timeStr) {
        return timeStr.matches("([01]\\d|2[0-3]):[0-5]\\d");
    }
}
