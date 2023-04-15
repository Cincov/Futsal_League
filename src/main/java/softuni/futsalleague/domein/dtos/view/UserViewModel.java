package softuni.futsalleague.domein.dtos.view;

import softuni.futsalleague.domein.enums.UserRoleEnums;

import java.math.BigDecimal;

public class UserViewModel {

    private Long id;
    private String username;
    private UserRoleEnums role;
    private BigDecimal budget;

    public UserViewModel() {
    }

    public Long getId() {
        return id;
    }

    public UserViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserRoleEnums getRole() {
        return role;
    }

    public UserViewModel setRole(UserRoleEnums role) {
        this.role = role;
        return this;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public UserViewModel setBudget(BigDecimal budget) {
        this.budget = budget;
        return this;
    }
}
