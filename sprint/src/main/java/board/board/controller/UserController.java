package board.board.controller;

import board.board.model.User;
import board.board.service.UserService;
import board.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

    private UserService userService;
    private UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "register";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";

        }

        userService.save(userForm);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, String error/*, String logout*/) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        return "login";
    }

}
