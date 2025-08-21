package br.senac.jobfinder.controller;

import br.senac.jobfinder.model.ChangePasswordForm;
import br.senac.jobfinder.model.UserEdit;
import br.senac.jobfinder.service.UserService;
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

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());

        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("email", user.getEmail());

        return "dashboard";
    }

    @GetMapping("/profile/edit")
    public String editProfileForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("user", user);

        return "dashboard-profile-edit";
    }

    @PostMapping("/profile/edit")
    public String editProfileSubmit(@Valid @ModelAttribute("user") UserEdit userDto,
                                    BindingResult br, RedirectAttributes attrs) {
        if (br.hasErrors()) {
            attrs.addFlashAttribute("org.springframework.validation.BindingResult.user", br);
            attrs.addFlashAttribute("user", userDto);
            return "redirect:/dashboard/profile/edit";
        }

        userService.editUser(userDto);

        return "redirect:/dashboard";
    }

    @GetMapping("/password/change")
    public String showChangePasswordForm(Model model) {
        if (!model.containsAttribute("pwdForm")) {
            model.addAttribute("pwdForm", new ChangePasswordForm());
        }
        return "dashboard-password-change";
    }

    @PostMapping("/password/change")
    public String handleChangePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @ModelAttribute("pwdForm") ChangePasswordForm form,
            BindingResult br,
            RedirectAttributes attrs
    ) {
        if (br.hasErrors()) {
            attrs.addFlashAttribute("org.springframework.validation.BindingResult.pwdForm", br);
            attrs.addFlashAttribute("pwdForm", form);
            return "redirect:/dashboard/password/change";
        }

        userService.updatePassword(userDetails.getUsername(), form);

        attrs.addFlashAttribute("message", "Senha alterada com sucesso!");
        return "redirect:/dashboard/password/change";
    }
}
