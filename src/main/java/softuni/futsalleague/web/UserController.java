package softuni.futsalleague.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import softuni.futsalleague.domein.dtos.binding.UserEditFormDTO;
import softuni.futsalleague.domein.dtos.binding.UserWithRoleDTO;
import softuni.futsalleague.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userEditFormDTO")
    public UserEditFormDTO userEditFormDTO() {
        return new UserEditFormDTO();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public String allUsers(Model model,
                           @AuthenticationPrincipal UserDetails user) {

        List<UserWithRoleDTO> allUsers = userService.getAllUsers();

        model.addAttribute("allUsers", allUsers);

        return "all-users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}/edit")
    public String editUSer(@PathVariable("userId") Long userId,
                           Model model) {

        model.addAttribute("id", userId);

        return "edit-user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{userId}/edit")
    public String confirmEditUser(@PathVariable("userId") Long userId,
                                  UserEditFormDTO userEditFormDTO) {

        userService.editUser(userId, userEditFormDTO);

        return "redirect:/users/all";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long userId,
                             @AuthenticationPrincipal UserDetails userDetails) {

        userService.deleteUser(userId);

        return "redirect:/users/all";
    }
}
