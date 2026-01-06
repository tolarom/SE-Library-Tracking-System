package project.library.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.library.demo.dto.DashboardStats;
import project.library.demo.repo.BookRepository;
import project.library.demo.repo.BorrowRepository;
import project.library.demo.repo.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Controller
public class AdminController {

    private final BookRepository bookRepository;
    private final BorrowRepository borrowRepository;
    private final UserRepository userRepository;

    // Constructor injection (recommended)
    public AdminController(BookRepository bookRepository,
                           BorrowRepository borrowRepository,
                           UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.borrowRepository = borrowRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model) {

        model.addAttribute("currentPath", request.getRequestURI());

        DashboardStats stats = new DashboardStats();
        stats.setTotalBooks((int) bookRepository.count());
        stats.setTotalMembers((int) userRepository.count()); // or count only members, not admins



        model.addAttribute("stats", stats);

        return "dashboard";
    }
}