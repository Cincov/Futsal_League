package softuni.futsalleague.domein.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "teams")
public class TeamEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;
    @OneToOne
    @JoinColumn(name = "coach_entity_id")
    private CoachEntity coachEntity;
    @OneToMany
    private List<PlayerEntity> players;
    @Column
    private int rating;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column
    private BigDecimal budget;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


    public String getName() {
        return name;
    }

    public TeamEntity setName(String name) {
        this.name = name;
        return this;
    }

    public CoachEntity getCoachEntity() {
        return coachEntity;
    }

    public TeamEntity setCoachEntity(CoachEntity coachEntity) {
        this.coachEntity = coachEntity;
        return this;
    }

    public List<PlayerEntity> getPlayers() {
        return players;
    }

    public TeamEntity setPlayers(List<PlayerEntity> players) {
        this.players = players;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public TeamEntity setRating(int rating) {
        this.rating = rating;
        return this;
    }



    public BigDecimal getBudget() {
        return budget;
    }

    public TeamEntity setBudget(BigDecimal budget) {
        this.budget = budget;
        return this;
    }


}
