package project.library.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import project.library.demo.entity.Book;
import project.library.demo.repo.BookRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.Objects;


@Service
public class BookService {

    private final BookRepository bookRepository;

    @Value("${app.upload.dir:${user.home}/library-uploads}")
    private String uploadDir;  // Default fallback if not in properties

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Get top N popular available books (used on member dashboard)
     */
   

    /**
     * Get a book by ID (used in Thymeleaf templates)
     */
    public Book getBookById(Long bookId) {
        Objects.requireNonNull(bookId, "bookId");
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found: " + bookId));
    }

    // === File Upload Helper ===
    private String saveFile(MultipartFile file, String subDir) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        Path uploadPath = Paths.get(uploadDir, subDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String filename = UUID.randomUUID().toString() + extension;

        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return relative path for storing in DB
        return subDir + "/" + filename;
    }

    // === CRUD Operations ===

    public Book create(Book book, MultipartFile coverFile) throws IOException {
        if (coverFile != null && !coverFile.isEmpty()) {
            book.setCoverImage(saveFile(coverFile, "covers"));
        }

        book.setAvailableCopies(book.getTotalCopies());
        return bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        Objects.requireNonNull(id, "id");
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found: " + id));
    }

    public Book update(Long id, Book updatedBook, MultipartFile coverFile) throws IOException {
        Objects.requireNonNull(id, "id");
        Book book = findById(id);

        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setIsbn(updatedBook.getIsbn());
        book.setPublisher(updatedBook.getPublisher());

        if (coverFile != null && !coverFile.isEmpty()) {
            book.setCoverImage(saveFile(coverFile, "covers"));
        }

        int diff = updatedBook.getTotalCopies() - book.getTotalCopies();
        book.setTotalCopies(updatedBook.getTotalCopies());
        book.setAvailableCopies(book.getAvailableCopies() + diff);

        return bookRepository.save(book);
    }

    public void delete(Long id) {
        Objects.requireNonNull(id, "id");
        bookRepository.deleteById(id);
    }
}