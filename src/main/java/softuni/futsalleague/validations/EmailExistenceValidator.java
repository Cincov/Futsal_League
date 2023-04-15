package softuni.futsalleague.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import softuni.futsalleague.repository.UserRepository;

public class EmailExistenceValidator implements ConstraintValidator<ValidateEmailExistence, String> {

    private final UserRepository userRepository;

    @Autowired
    public EmailExistenceValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(ValidateEmailExistence constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return this.userRepository.findByEmail(email).isEmpty();
    }
}
