package softuni.futsalleague.domein.dtos.binding;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PlayerCreateFormDto {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Min(18)
    private int age;
    private String position;

    public PlayerCreateFormDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public PlayerCreateFormDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PlayerCreateFormDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public int getAge() {
        return age;
    }

    public PlayerCreateFormDto setAge(int age) {
        this.age = age;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public PlayerCreateFormDto setPosition(String position) {
        this.position = position;
        return this;
    }
}
