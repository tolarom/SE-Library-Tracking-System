package project.library.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import project.library.demo.entity.Book;
import project.library.demo.entity.BorrowRecord;
import project.library.demo.entity.User;
import project.library.demo.repo.BorrowRepository;
import project.library.demo.repo.LibraryRepository;
import project.library.demo.repo.UserRepository;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final BorrowRepository borrowRepository;
    private final UserRepository userRepository;

    public LibraryService(LibraryRepository libraryRepository, BorrowRepository borrowRepository, UserRepository userRepository) {
        this.libraryRepository = libraryRepository;
        this.borrowRepository = borrowRepository;
        this.userRepository = userRepository;
    }

    // BOOK
    public List<Book> getAllBooks() {
        return libraryRepository.findAll();
    }

    public Book getBookById(String id) {
        return libraryRepository.findById(id).orElse(null);
    }

    public Book saveBook(Book book) {
        if (book.getId() == null || book.getId().isEmpty()) {
            book.setId(UUID.randomUUID().toString());
        }
        return libraryRepository.save(book);
    }

    public void deleteBook(String id) {
        libraryRepository.deleteById(id);
    }

    // BORROW RECORD
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRepository.findAll();
    }

    public BorrowRecord getBorrowRecordById(String id) {
        return borrowRepository.findById(id).orElse(null);
    }

    public BorrowRecord saveBorrowRecord(BorrowRecord record) {
        if (record.getId() == null || record.getId().isEmpty()) {
            record.setId(UUID.randomUUID().toString());
        }
        return borrowRepository.save(record);
    }

    public void deleteBorrowRecord(String id) {
        borrowRepository.deleteById(id);
    }

    // USER
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
