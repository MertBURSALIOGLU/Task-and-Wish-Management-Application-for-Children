public class Parent extends Adult {
    public Parent(String name, int ID) {
        super(name, ID);
    }

    public void approveWish(Child child, String wishID, String status) {
        Wish wish = child.getWishByID(wishID);
        if (wish != null) {
            wish.setIsApproved(status);
        }
    }

}
