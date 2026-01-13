package project.library.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.library.demo.entity.BorrowRecord;
import project.library.demo.service.BookService;
import project.library.demo.service.BorrowService;
import project.library.demo.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin/borrows")
public class BorrowController {

    private final BorrowService borrowService;
    private final BookService bookService;
    private final UserService userService;

    public BorrowController(BorrowService borrowService, BookService bookService, UserService userService) {
        this.borrowService = borrowService;
        this.bookService = bookService;
        this.userService = userService;
    }

    // Show all borrow records
    @GetMapping
    public String listBorrows(Model model) {
        List<BorrowRecord> borrows = borrowService.findAllWithBooksAndUsers();
        model.addAttribute("borrows", borrows);
        return "borrows/list";
    }

    // Show "Add Borrow" form
    @GetMapping("/new")
    public String showBorrowForm(Model model) {
        model.addAttribute("borrowRecord", new BorrowRecord());
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("users", userService.getAllMembers());
        return "borrows/form";
    }

    // Handle "Add Borrow" submission
    @PostMapping("/borrow")
    public String addBorrow(@ModelAttribute BorrowRecord borrowRecord,
                            RedirectAttributes redirectAttributes) {
        try {
            
            // Set borrow date and due date
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            borrowRecord.setBorrowAt(now);
            borrowRecord.setDueDate(Timestamp.valueOf(now.toLocalDateTime().plusDays(14)));
            borrowRecord.setStatus("BORROWED");
            borrowRecord.setOverdue(false);

            // Save borrow record using IDs
            borrowService.borrowBookByIds(borrowRecord.getUserId(), borrowRecord.getBookId());

            redirectAttributes.addFlashAttribute("success", "Book borrowed successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot borrow book: " + e.getMessage());
        }
        return "redirect:/admin/borrows";
    }

    // Handle "Return Book" action
    @PostMapping("/return/{id}")
    public String returnBook(@PathVariable Long id,
                             RedirectAttributes redirectAttributes) {
        try {
            borrowService.returnBook(id);
            redirectAttributes.addFlashAttribute("success", "Book returned successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot return book: " + e.getMessage());
        }
        return "redirect:/admin/borrows";
    }

    // Delete a borrow record
    @GetMapping("/delete/{id}")
    public String deleteBorrow(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            borrowService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Borrow record deleted!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete borrow record");
        }
        return "redirect:/admin/borrows";
    }
}
