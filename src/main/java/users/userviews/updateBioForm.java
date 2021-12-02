package users.userviews;

public class updateBioForm {
    String bio;

    public updateBioForm(){
        this.bio = "";
    }

    public updateBioForm(String bio) {
        this.bio = bio;
    }
    
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
    
}
