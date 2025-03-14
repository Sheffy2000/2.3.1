package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String printWelcome(Model model) {
        List<User> allUsers = userService.showUsers ();
        model.addAttribute ("users", allUsers);
        return "allUsers";
    }

    @GetMapping(value = "/addUser")
    public String showUserInfo(Model model) {
        model.addAttribute ("user", new User ());
        return "userFormForAdd";
    }

    @PostMapping(value = "/addUser")
    public String addUser(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors ()) {
            return "userFormForAdd";
        }
        userService.addUser (user);
        return "redirect:/";
    }

    @PostMapping(value = "/updateUser")
    public String updateUser(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors ()) {
            return "userFormForUpdate";
        }
        userService.updateUser (user);
        return "redirect:/";
    }

    @GetMapping("/editUser")
    public String updateInfo(@RequestParam("id") int id, Model model) {
        User user = userService.getUserById (id);
        model.addAttribute ("user", user);
        return "userFormForUpdate";
    }

    @GetMapping("deleteUser")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteUser (id);
        return "redirect:/";
    }

}