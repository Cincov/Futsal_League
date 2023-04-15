package softuni.futsalleague.web;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.futsalleague.domein.dtos.binding.CoachCreateFormDto;
import softuni.futsalleague.service.CoachService;

@Controller
@RequestMapping("/coaches")
public class CoachController {

    private static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";
    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @ModelAttribute(name = "coachCreateFormDto")
    public CoachCreateFormDto coachCreateFormDto() {
        return new CoachCreateFormDto();
    }

    @GetMapping("/add")
    public String createCoach(Model model) {

        if (!model.containsAttribute("existCoach")) {
            model.addAttribute("existCoach", false);
        }

        return "create-coach";
    }

    @PostMapping("/add")
    public String confirmCreateCoach(@Valid CoachCreateFormDto coachCreateFormDto,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     @AuthenticationPrincipal UserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("coachCreateFormDto", coachCreateFormDto)
                    .addFlashAttribute(BINDING_RESULT_PATH + "coachCreateFormDto", bindingResult);

            return "redirect:/coaches/add";
        }

        boolean isCreated = coachService.createCoach(coachCreateFormDto, userDetails.getUsername());

        if ( !isCreated) {
            redirectAttributes. addFlashAttribute("existCoach", true);
            return "redirect:/coaches/add";
        }

        return "redirect:/";
    }
}
