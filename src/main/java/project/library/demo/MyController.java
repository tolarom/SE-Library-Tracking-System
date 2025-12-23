package project.library.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping()
public class MyController {

    private final LibraryService libraryService;
    private final UserRepository userRepository;

    public MyController(LibraryService libraryService, UserRepository userRepository){
        this.libraryService = libraryService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("books", libraryService.getAllBooks());
            return "dashboard";
        } else {
            return "Login";
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiResponse login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(loginRequest.getPassword())) {
                session.setAttribute("user", user);
                return new ApiResponse(true, "Login successful");
            } else {
                return new ApiResponse(false, "Invalid password");
            }
        } else {
            return new ApiResponse(false, "User not found");
        }
    }

    @PostMapping("/logout")
    @ResponseBody
    public ApiResponse logout(HttpSession session) {
        session.invalidate();
        return new ApiResponse(true, "Logged out");
    }

    @GetMapping("/books")
    @ResponseBody
    public List<Book> getAll(){
        return libraryService.getAllBooks();
    }

    @GetMapping("/borrow-records")
    @ResponseBody
    public List<BorrowRecord> getAllBorrowRecords(){
        return libraryService.getAllBorrowRecords();   
    }

    @GetMapping("/user")
    @ResponseBody
    public List<User> getAllUsers() {
        return libraryService.getAllUsers();
    }
    
    
}
