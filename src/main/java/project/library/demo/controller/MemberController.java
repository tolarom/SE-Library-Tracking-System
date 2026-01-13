package project.library.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.library.demo.config.CustomUserDetails;
import project.library.demo.dto.BorrowDTO;
import project.library.demo.entity.Book;
import project.library.demo.entity.BorrowRecord;
import project.library.demo.repo.BookRepository;
import project.library.demo.service.BorrowService;

import java.util.List;

@Controller
public class MemberController {

    private final BorrowService borrowService;
    private final BookRepository bookRepository;

    public MemberController(BorrowService borrowService, BookRepository bookRepository) {
        this.borrowService = borrowService;
        this.bookRepository = bookRepository;
    }

    // Home page
    @GetMapping("/member/home")
    public String memberHome(HttpServletRequest request, @AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails == null) return "redirect:/login";

        Long userId = userDetails.getId();

        List<BorrowDTO> currentDTOs = borrowService.getCurrentBorrows(userId).stream()
                .map(this::toBorrowDTO).toList();
        List<BorrowDTO> historyDTOs = borrowService.getBorrowingHistory(userId).stream()
                .map(this::toBorrowDTO).toList();

        model.addAttribute("currentBorrows", currentDTOs);
        model.addAttribute("overdueCount", borrowService.getOverdueCount(userId));
        model.addAttribute("history", historyDTOs);
        model.addAttribute("currentPath", request.getRequestURI());

        return "member/home";
    }

    // Current Borrows page
    @GetMapping("/member/borrows/current")
    public String currentBorrows(HttpServletRequest request, @AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails == null) return "redirect:/login";

        Long userId = userDetails.getId();

        List<BorrowDTO> dtos = borrowService.getCurrentBorrows(userId).stream()
                .map(this::toBorrowDTO).toList();

        model.addAttribute("borrows", dtos);
        model.addAttribute("currentPath", request.getRequestURI());

        return "member/current-borrow";
    }

    // Borrowing History page
    @GetMapping("/member/borrows/history")
    public String borrowingHistory(HttpServletRequest request, @AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails == null) return "redirect:/login";

        Long userId = userDetails.getId();

        List<BorrowDTO> dtos = borrowService.getBorrowingHistory(userId).stream()
                .map(this::toBorrowDTO).toList();

        model.addAttribute("borrows", dtos);
        model.addAttribute("currentPath", request.getRequestURI());

        return "member/borrows-history";
    }

    private BorrowDTO toBorrowDTO(BorrowRecord borrow) {
        Long bookId = borrow.getBookId();
        Book book = (bookId != null) ? bookRepository.findById(bookId).orElse(null) : null;

        String title = book != null ? book.getTitle() : "Unknown Book";
        String cover = book != null && book.getCoverImage() != null
            ? book.getCoverImage()
            : "/images/default-cover.jpg";

        return new BorrowDTO(
                borrow.getId(),
                title,
                cover,
                borrow.getBorrowAt(),
                borrow.getDueDate(),
                borrow.getReturnAt(),
                borrow.getStatus(),
                borrow.isOverdue()
        );
    }
}