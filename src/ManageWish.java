import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageWish {
    private List<Wish> wishList;

    public ManageWish() {
        this.wishList = new ArrayList<>();
    }

    public void addWish(Wish wish) {
        wishList.add(wish);
        saveWishesToFile("Wishes.txt");
    }

    public List<Wish> getWishList() {
        return wishList;
    }

    public Wish getWishByID(String wishID) {
        for (Wish w : wishList) {
            if (w.getWishID().equals(wishID)) {
                return w;
            }
        }
        return null;
    }

    public void approveWish(String wishID, String status) {
        Wish w = getWishByID(wishID);
        if (w != null) {
            w.setApproved(status);
            saveWishesToFile("Wishes.txt");
        } else {
            System.out.println("Error: Wish not found!");
        }
    }

    public void showAllWishes(int childLevel) {
        boolean found = false;
        for (Wish w : wishList) {
            System.out.println(toString(w, childLevel));
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
            sb.append("APPROVED");
        } else if (status.equals("REJECTED")) {
            sb.append("REJECTED");
        } else {
            if (childLevel < wish.getLevel()) {
                sb.append("WAITING (Level too low)");
            } else {
                sb.append("PENDING");
            }
        }
        return sb.toString();
    }

    public void saveWishesToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
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
}
