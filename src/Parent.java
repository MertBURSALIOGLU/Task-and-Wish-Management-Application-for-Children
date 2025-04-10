public class Parent extends Adult {
    public Parent(String name, int ID) {
        super(name, ID);
    }

    public void approveWish(Child child, String wishID, String status) {
        Wish w = child.getWishByID(wishID);
        if (w != null) {
            w.setApproved(status);
            System.out.println("Wish " + wishID + " " + status.toLowerCase() + ".");
        } else {
            System.out.println("Error: Wish not found!");
        }
    }
}
