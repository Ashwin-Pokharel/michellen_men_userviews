package users.userviews;

public class userReviews {
    
    String user;
    String restaurant;
    int rating;
    String comment;

    public userReviews(){
        user = "";
        restaurant = "";
        rating = 0;
        comment = "";
    }

    public userReviews(String user, String restaurant, int rating, String comment) {
        this.user = user;
        this.restaurant = restaurant;
        this.rating = rating;
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "userReviews{" + "user=" + user + ", restaurant=" + restaurant + ", rating=" + rating + ", comment=" + comment + '}';
    }

    
}
