package users.userviews;

public class validateTokenResponse {
    public String status;
    public String message;
    public String token;

    public validateTokenResponse(){
        this.status = "";
        this.message = "";
        this.token = "";        
    }

    public validateTokenResponse(String status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "validateTokenResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    
}
