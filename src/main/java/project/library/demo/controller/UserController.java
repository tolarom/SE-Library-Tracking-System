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
import project.library.demo.entity.Role;
import project.library.demo.entity.ERole;
import project.library.demo.service.UserService;
import project.library.demo.service.RoleService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
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
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("title", "Edit Member");
        return "users/form";
    }

    @PostMapping("/save")
    public String saveUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            @RequestParam(required = false) String confirmPassword,
            @RequestParam(required = false, name = "roles") Long[] roleIds,  // or remove if you rely on binding
            Model model,
            RedirectAttributes redirectAttributes) {

        boolean isNewUser = user.getId() == null;

        // Password validation (unchanged)
        if (isNewUser) {
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                result.rejectValue("password", "password.required", "Password is required");
            } else if (!user.getPassword().equals(confirmPassword)) {
                model.addAttribute("passwordMismatch", "Passwords do not match");
            }
        } else {
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                User existing = userService.getUserById(user.getId());
                user.setPassword(existing.getPassword());
            }
        }

        // Role validation - ADD THIS CHECK FIRST
        if (roleIds == null || roleIds.length == 0) {
            model.addAttribute("roleError", "Please select at least one role");
            model.addAttribute("allRoles", roleService.getAllRoles());
            model.addAttribute("title", isNewUser ? "Add New Member" : "Edit Member");
            model.addAttribute("user", user);  // Preserve form data
            return "users/form";
        }

        // Clear any validation errors before final check
        if (result.hasErrors() || model.containsAttribute("passwordMismatch")) {
            model.addAttribute("roleError", null);  // Clear role error if other errors exist
            model.addAttribute("allRoles", roleService.getAllRoles());
            model.addAttribute("title", isNewUser ? "Add New Member" : "Edit Member");
            return "users/form";
        }

        // Encode password
        if (isNewUser || 
            (user.getPassword() != null && !user.getPassword().startsWith("$2a$"))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Set roles (unchanged - this works perfectly)
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = roleService.getRoleById(roleId);
            if (role != null) {
                roles.add(role);
            }
        }
        user.setRoles(roles);

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