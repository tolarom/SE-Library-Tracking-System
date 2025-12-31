package project.library.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.library.demo.repo.BookRepository;
import project.library.demo.repo.BorrowRepository;
import project.library.demo.service.UserService; // ← Add this import

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DashboardController {

    private final BookRepository bookRepository;
    private final BorrowRepository borrowRepository;
    private final UserService userService; // ← Inject UserService for real member count

    public DashboardController(BookRepository bookRepository,
                               BorrowRepository borrowRepository,
                               UserService userService) { // ← Add UserService to constructor
        this.bookRepository = bookRepository;
        this.borrowRepository = borrowRepository;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // 1. Total number of books
        long totalBooks = bookRepository.count();

        // 2. Currently borrowed books
        long borrowedCount = borrowRepository.countByReturnAtIsNull();

        // 3. Overdue loans
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        long overdueCount = borrowRepository.countOverdueLoans(now);

        // 4. Total members - now using real data!
        long totalMembers = userService.countMembers();

        // 5. New members this month (placeholder for now - you can enhance later)
        // For now, we'll just show a static number. Replace with real query when ready.
        long newMembersThisMonth = 12; // TODO: Implement real calculation later

        // Build stats map
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBooks", totalBooks);
        stats.put("borrowedCount", borrowedCount);
        stats.put("overdueCount", overdueCount);
        stats.put("totalMembers", totalMembers);
        stats.put("newMembersThisMonth", newMembersThisMonth); // ← This fixes the error!

        model.addAttribute("stats", stats);

        // Optional: Add lists for overdue and currently borrowed books
        // Uncomment when you add the query methods
        // model.addAttribute("overdueLoans", borrowRepository.findOverdueLoans(now));
        // model.addAttribute("currentlyBorrowed", borrowRepository.findCurrentBorrows());

        return "dashboard";
    }
}