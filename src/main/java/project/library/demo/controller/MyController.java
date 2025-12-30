package project.library.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.library.demo.entity.Book;
import project.library.demo.entity.BorrowRecord;
import project.library.demo.entity.User;
import project.library.demo.service.LibraryService;

@RestController
@RequestMapping("/api")
public class MyController {

    private final LibraryService libraryService;

    public MyController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    // ===================== BOOK CRUD =====================
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return libraryService.getAllBooks();
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable String id) {
        return libraryService.getBookById(id);
    }

    @PostMapping("/books")
    public Book createBook(@RequestBody Book book) {
        return libraryService.saveBook(book);
    }

    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable String id, @RequestBody Book book) {
        book.setId(id);
        return libraryService.saveBook(book);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable String id) {
        libraryService.deleteBook(id);
    }

    // ===================== BORROW RECORD CRUD =====================
    @GetMapping("/borrow-records")
    public List<BorrowRecord> getAllBorrowRecords() {
        return libraryService.getAllBorrowRecords();
    }

    @GetMapping("/borrow-records/{id}")
    public BorrowRecord getBorrowRecordById(@PathVariable String id) {
        return libraryService.getBorrowRecordById(id);
    }

    @PostMapping("/borrow-records")
    public BorrowRecord createBorrowRecord(@RequestBody BorrowRecord record) {
        return libraryService.saveBorrowRecord(record);
    }

    @PutMapping("/borrow-records/{id}")
    public BorrowRecord updateBorrowRecord(@PathVariable String id, @RequestBody BorrowRecord record) {
        record.setId(id);
        return libraryService.saveBorrowRecord(record);
    }

    @DeleteMapping("/borrow-records/{id}")
    public void deleteBorrowRecord(@PathVariable String id) {
        libraryService.deleteBorrowRecord(id);
    }

    // ===================== USER CRUD =====================
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return libraryService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return libraryService.getUserById(id);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return libraryService.saveUser(user);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return libraryService.saveUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        libraryService.deleteUser(id);
    }
}
