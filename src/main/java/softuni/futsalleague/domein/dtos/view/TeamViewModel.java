package softuni.futsalleague.domein.dtos.view;

import java.math.BigDecimal;
import java.util.List;

public class TeamViewModel {

    private Long id;
    private String name;
    private String coachName;
    private int coachRating;
    private List<PlayerViewModel> players;
    private int rating;
    private BigDecimal budget;

    public TeamViewModel() {
    }

    public Long getId() {
        return id;
    }

    public TeamViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TeamViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getCoachName() {
        return coachName;
    }

    public TeamViewModel setCoachName(String coachName) {
        this.coachName = coachName;
        return this;
    }

    public List<PlayerViewModel> getPlayers() {
        return players;
    }

    public TeamViewModel setPlayers(List<PlayerViewModel> players) {
        this.players = players;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public TeamViewModel setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public TeamViewModel setBudget(BigDecimal budget) {
        this.budget = budget;
        return this;
    }

    public int getCoachRating() {
        return coachRating;
    }

    public TeamViewModel setCoachRating(int coachRating) {
        this.coachRating = coachRating;
        return this;
    }
}
