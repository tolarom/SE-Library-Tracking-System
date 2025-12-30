package project.library.demo.controller;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.library.demo.repo.UserRepository;
import project.library.demo.service.LibraryService;

@Controller
public class PageController {

    private final LibraryService libraryService;
    private final UserRepository userRepository;

    public PageController(LibraryService libraryService, UserRepository userRepository) {
        this.libraryService = libraryService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; 
    }
    @GetMapping("/register")
    public String register() {
        return "register"; 
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            userRepository.findByUsername(username).ifPresent(user -> model.addAttribute("user", user));
        }
        model.addAttribute("books", libraryService.getAllBooks());
        return "dashboard";
    }

    @GetMapping("/products")
    public String products() {
        return "products";
    }
}
