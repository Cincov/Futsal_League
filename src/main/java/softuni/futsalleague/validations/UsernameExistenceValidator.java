package softuni.futsalleague.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import softuni.futsalleague.repository.UserRepository;

public class UsernameExistenceValidator implements ConstraintValidator<ValidateUsernameExistence, String> {

    private final UserRepository userRepository;

    @Autowired
    public UsernameExistenceValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(ValidateUsernameExistence constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return this.userRepository.findUserEntityByUsername(username).isEmpty();
    }
}
