package residentevil.domain.models.view;

public class JwtResponse {

    private String token;
    private UserLoggedViewModel user;

    public JwtResponse(String token, UserLoggedViewModel user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserLoggedViewModel getUser() {
        return user;
    }

    public void setUser(UserLoggedViewModel user) {
        this.user = user;
    }
}
