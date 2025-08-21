package br.senac.jobfinder.controller;

import br.senac.jobfinder.model.UserRegister;
import br.senac.jobfinder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // Libera CORS para testes
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // renderiza login.html
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserRegister("", "", "", "", ""));
        }
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("user") UserRegister reg,
                             BindingResult br,
                             RedirectAttributes redirectAttrs) {
        if (br.hasErrors()) {
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.user", br);
            redirectAttrs.addFlashAttribute("user", reg);
            return "redirect:/auth/register";
        }

        try {
            userService.registerNewUser(reg);
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("registrationError", e.getMessage());
            redirectAttrs.addFlashAttribute("user", reg);
            return "redirect:/auth/register";
        }

        return "redirect:/auth/login";
    }
}

