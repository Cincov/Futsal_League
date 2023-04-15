package softuni.futsalleague.domein.dtos.view;

import java.math.BigDecimal;

public class PlayerViewModel {

    private Long id;
    private String firstName;
    private String lastName;
    private int rating;
    private String teamName;
    private String position;
    private int pace;
    private int shooting;
    private int passing;
    private int dribbling;
    private int defending;
    private BigDecimal price;


    public PlayerViewModel() {
    }

    public Long getId() {
        return id;
    }

    public PlayerViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public PlayerViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PlayerViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public PlayerViewModel setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public String getTeamName() {
        return teamName;
    }

    public PlayerViewModel setTeamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public PlayerViewModel setPosition(String position) {
        this.position = position;
        return this;
    }

    public int getPace() {
        return pace;
    }

    public PlayerViewModel setPace(int pace) {
        this.pace = pace;
        return this;
    }

    public int getShooting() {
        return shooting;
    }

    public PlayerViewModel setShooting(int shooting) {
        this.shooting = shooting;
        return this;
    }

    public int getPassing() {
        return passing;
    }

    public PlayerViewModel setPassing(int passing) {
        this.passing = passing;
        return this;
    }

    public int getDribbling() {
        return dribbling;
    }

    public PlayerViewModel setDribbling(int dribbling) {
        this.dribbling = dribbling;
        return this;
    }

    public int getDefending() {
        return defending;
    }

    public PlayerViewModel setDefending(int defending) {
        this.defending = defending;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PlayerViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
