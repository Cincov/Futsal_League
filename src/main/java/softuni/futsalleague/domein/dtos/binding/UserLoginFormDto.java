package softuni.futsalleague.domein.dtos.binding;

public class UserLoginFormDto {

    private String email;
    private String password;

    public UserLoginFormDto() {
    }

    public String getEmail() {
        return email;
    }

    public UserLoginFormDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginFormDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
