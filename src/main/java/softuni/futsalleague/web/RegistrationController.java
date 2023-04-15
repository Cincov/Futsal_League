package softuni.futsalleague.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.futsalleague.domein.dtos.binding.UserRegisterFormDto;
import softuni.futsalleague.service.UserService;

@Controller
@RequestMapping("/users")
public class RegistrationController {

    private static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    // Model attributes

    @ModelAttribute(name = "userRegisterForm")
    public UserRegisterFormDto initUserRegisterFormDto() {
        return new UserRegisterFormDto();
    }

    @GetMapping("/register")
    public String getRegister() {

        return "auth-register";
    }

    @PostMapping("/register")
    public String postRegister(
            @Valid UserRegisterFormDto userRegisterForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("userRegisterForm", userRegisterForm)
                    .addFlashAttribute(BINDING_RESULT_PATH + "userRegisterForm", bindingResult);

            return "redirect:/users/register";
        }

        userService.registerUser(userRegisterForm);

        return "redirect:/users/login";
    }





}
