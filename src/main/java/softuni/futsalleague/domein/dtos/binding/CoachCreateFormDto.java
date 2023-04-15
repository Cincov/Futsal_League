package softuni.futsalleague.domein.dtos.binding;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CoachCreateFormDto {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Min(18)
    private int age;

    public CoachCreateFormDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public CoachCreateFormDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public CoachCreateFormDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public int getAge() {
        return age;
    }

    public CoachCreateFormDto setAge(int age) {
        this.age = age;
        return this;
    }
}
