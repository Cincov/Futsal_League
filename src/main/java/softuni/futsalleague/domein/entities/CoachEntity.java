package softuni.futsalleague.domein.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "coaches")
public class CoachEntity extends Person{

    @Column(nullable = false)
    private int technique;
    @Column(nullable = false)
    private int tactical;
    @Column(nullable = false)
    private int physical;
    @Column(nullable = false, name = "team_work")
    private int teamWork;
    @Column(nullable = false)
    private int rating;


    public int getTechnique() {
        return technique;
    }

    public CoachEntity setTechnique(int technique) {
        this.technique = technique;
        return this;
    }

    public int getTactical() {
        return tactical;
    }

    public CoachEntity setTactical(int tactical) {
        this.tactical = tactical;
        return this;
    }

    public int getPhysical() {
        return physical;
    }

    public CoachEntity setPhysical(int physical) {
        this.physical = physical;
        return this;
    }

    public int getTeamWork() {
        return teamWork;
    }

    public CoachEntity setTeamWork(int teamWork) {
        this.teamWork = teamWork;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public CoachEntity setRating(int rating) {
        this.rating = rating;
        return this;
    }



//
}
