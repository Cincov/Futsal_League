package softuni.futsalleague.validations;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.modelmapper.ModelMapper;
import softuni.futsalleague.domein.dtos.binding.UserLoginFormDto;
import softuni.futsalleague.domein.entities.UserEntity;
import softuni.futsalleague.repository.UserRepository;

import java.util.Optional;

public record LoginUserValidator(UserRepository userRepository,
                                 ModelMapper modelMapper)
        implements ConstraintValidator<ValidateLoginUser, UserLoginFormDto> {

    @Override
    public void initialize(ValidateLoginUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserLoginFormDto userLogin, ConstraintValidatorContext constraintValidatorContext) {
        Optional<UserEntity> loginCandidate = this.userRepository.findByEmail(userLogin.getEmail());

        return loginCandidate.isPresent()
                && loginCandidate.get()
                .getPassword()
                .equals(userLogin.getPassword());
    }
}
