package project.library.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/redirect-dashboard";
        }
        return "redirect:/login"; 
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * Redirects users to the appropriate dashboard based on their role
     */
    @GetMapping("/redirect-dashboard")
    public String redirectToDashboard(Authentication authentication) {
        boolean isLibrarian = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_LIBRARIAN"));

        if (isLibrarian) {
            return "redirect:/dashboard";          // Librarian gets full admin dashboard
        } else {
            return "redirect:/member/home";   // Regular member portal
        }
    }
}