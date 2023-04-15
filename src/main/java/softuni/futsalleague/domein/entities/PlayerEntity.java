package softuni.futsalleague.domein.entities;

import jakarta.persistence.*;
import lombok.*;
import softuni.futsalleague.domein.enums.PlayerPosition;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "players")
public class PlayerEntity extends Person {

    @Enumerated(EnumType.STRING)
    private PlayerPosition position;
    @Column(nullable = false)
    private int pace;
    @Column(nullable = false)
    private int shooting;
    @Column(nullable = false)
    private int passing;
    @Column(nullable = false)
    private int dribbling;
    @Column(nullable = false)
    private int defending;
    @Column(nullable = false)
    private int rating;
    @ManyToOne
    @JoinColumn(name = "team_entity_id")
    private TeamEntity teamEntity;
    @Column
    private Boolean isSale;
    @Column
    private BigDecimal price;

    public TeamEntity getTeamEntity() {
        return teamEntity;
    }

    public void setTeamEntity(TeamEntity teamEntity) {
        this.teamEntity = teamEntity;
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public PlayerEntity setPosition(PlayerPosition position) {
        this.position = position;
        return this;
    }

    public int getPace() {
        return pace;
    }

    public PlayerEntity setPace(int pace) {
        this.pace = pace;
        return this;
    }

    public int getShooting() {
        return shooting;
    }

    public PlayerEntity setShooting(int shooting) {
        this.shooting = shooting;
        return this;
    }

    public int getPassing() {
        return passing;
    }

    public PlayerEntity setPassing(int passing) {
        this.passing = passing;
        return this;
    }

    public int getDribbling() {
        return dribbling;
    }

    public PlayerEntity setDribbling(int dribbling) {
        this.dribbling = dribbling;
        return this;
    }

    public int getDefending() {
        return defending;
    }

    public PlayerEntity setDefending(int defending) {
        this.defending = defending;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public PlayerEntity setRating(int rating) {
        this.rating = rating;
        return this;
    }


    public Boolean getSale() {
        return isSale;
    }

    public PlayerEntity setSale(Boolean sale) {
        isSale = sale;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PlayerEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
