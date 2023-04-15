package softuni.futsalleague.domein.dtos.binding;

import java.math.BigDecimal;

public class UserEditFormDTO {

    private Long id;
    private String username;
    private String admin;
    private String moderator;
    private String user;
    private BigDecimal budget;

    public UserEditFormDTO() {
    }

    public Long getId() {
        return id;
    }

    public UserEditFormDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEditFormDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getAdmin() {
        return admin;
    }

    public UserEditFormDTO setAdmin(String admin) {
        this.admin = admin;
        return this;
    }

    public String getModerator() {
        return moderator;
    }

    public UserEditFormDTO setModerator(String moderator) {
        this.moderator = moderator;
        return this;
    }

    public String getUser() {
        return user;
    }

    public UserEditFormDTO setUser(String user) {
        this.user = user;
        return this;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public UserEditFormDTO setBudget(BigDecimal budget) {
        this.budget = budget;
        return this;
    }
}
