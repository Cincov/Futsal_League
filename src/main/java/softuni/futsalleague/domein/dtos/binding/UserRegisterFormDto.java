package softuni.futsalleague.domein.dtos.binding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegisterFormDto {

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Size(min = 5, max = 20)
    private String password;

    @NotNull
    @Size(min = 5, max = 20)
    private String confirmPassword;

    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 20)
    private String lastName;

    @NotNull
    @Size(min = 3, max = 20)
    private String teamName;

    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    public UserRegisterFormDto() {
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterFormDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterFormDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterFormDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterFormDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterFormDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getTeamName() {
        return teamName;
    }

    public UserRegisterFormDto setTeamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterFormDto setUsername(String username) {
        this.username = username;
        return this;
    }
}
