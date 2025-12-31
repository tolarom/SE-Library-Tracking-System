package project.library.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.library.demo.entity.User;
import project.library.demo.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Important: inject BCryptPasswordEncoder

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Add New Member");
        return "users/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.getUserById(id);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Member not found");
            return "redirect:/admin/users";
        }
        // Clear password field for security (don't show hashed password)
        user.setPassword("");
        model.addAttribute("user", user);
        model.addAttribute("title", "Edit Member");
        return "users/form";
    }

    @PostMapping("/save")
    public String saveUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            @RequestParam(required = false) String confirmPassword,
            Model model,
            RedirectAttributes redirectAttributes) {

        boolean isNewUser = user.getId() == null;

        // === Password validation only for new users ===
        if (isNewUser) {
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                result.rejectValue("password", "password.required", "Password is required");
            } else if (!user.getPassword().equals(confirmPassword)) {
                model.addAttribute("passwordMismatch", "Passwords do not match");
            }
        } else {
            // Editing existing user
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                // Keep old password if new one is blank
                User existing = userService.getUserById(user.getId());
                user.setPassword(existing.getPassword());
            }
        }

        // === Final validation check ===
        if (result.hasErrors() || model.containsAttribute("passwordMismatch")) {
            model.addAttribute("title", isNewUser ? "Add New Member" : "Edit Member");
            return "users/form"; // Return to form with errors
        }

        // === Encode password if it's new or changed ===
        if (isNewUser || 
            (user.getPassword() != null && !user.getPassword().startsWith("$2a$"))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Save user
        userService.saveUser(user);

        redirectAttributes.addFlashAttribute("success", 
            isNewUser ? "Member added successfully!" : "Member updated successfully!");

        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("success", "Member deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete member");
        }
        return "redirect:/admin/users";
    }
}