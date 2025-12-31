package project.library.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.library.demo.entity.Book;
import project.library.demo.service.BookService;

import java.io.IOException;

@Controller
@RequestMapping("/admin/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // LIST - /books
    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/list";
    }

    // ADD FORM - /books/new
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/form";
    }

    // SAVE NEW BOOK
    @PostMapping("/new")
    public String create(@ModelAttribute Book book,
                         @RequestParam(value = "coverFile", required = false) MultipartFile coverFile,
                         RedirectAttributes redirectAttributes) {
        try {
            bookService.create(book, coverFile);
            redirectAttributes.addFlashAttribute("success", "Book added successfully!");
            return "redirect:/admin/books";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to upload files");
            return "redirect:/admin/books/new";
        }
    }

    // EDIT FORM - /books/edit/{id}
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "books/form";
    }

    // UPDATE BOOK
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute Book book,
                         @RequestParam(value = "coverFile", required = false) MultipartFile coverFile,
                         RedirectAttributes redirectAttributes) {
        try {
            bookService.update(id, book, coverFile);
            redirectAttributes.addFlashAttribute("success", "Book updated successfully!");
            return "redirect:/admin/books";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to upload files");
            return "redirect:/admin/books/edit/" + id;
        }
    }

    // DELETE - /admin/books/delete/{id}
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Book deleted successfully!");
        return "redirect:/admin/books";
    }
}