package project.library.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.library.demo.entity.Book;
import project.library.demo.entity.BorrowRecord;
import project.library.demo.entity.User;
import project.library.demo.repo.BookRepository;
import project.library.demo.repo.BorrowRepository;
import project.library.demo.repo.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    private static final int BORROW_DAYS = 14;

    // Borrow a book using IDs
    @Transactional
    public void borrowBookByIds(Long userId, Long bookId) {
        User member = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if(!member.getRoles().equals("ROLE_MEMBER")) {
            throw new RuntimeException("User is not a member");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No copies available");
        }

        BorrowRecord record = new BorrowRecord();
        record.setUserId(member.getId());
        record.setBookId(book.getId());
        record.setBorrowAt(Timestamp.valueOf(LocalDateTime.now()));
        record.setDueDate(Timestamp.valueOf(LocalDateTime.now().plusDays(BORROW_DAYS)));
        record.setReturnAt(null);
        record.setStatus("BORROWED");
        record.setOverdue(false);

        borrowRepository.save(record);

        // Decrease available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
    }

    // Return a book using borrow ID
    @Transactional
    public void returnBook(Long borrowId) {
        BorrowRecord record = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (record.getReturnAt() != null) {
            throw new RuntimeException("Book already returned");
        }

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        record.setReturnAt(now);

        // Check overdue
        if (record.getDueDate().before(now)) {
            record.setOverdue(true);
            record.setStatus("OVERDUE_RETURNED");
        } else {
            record.setStatus("RETURNED_ON_TIME");
        }

        borrowRepository.save(record);

        // Increase available copies
        Book book = bookRepository.findById(record.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
    }

    // Get all borrows
    public List<BorrowRecord> findAll() {
        return borrowRepository.findAll();
    }

    // Delete a borrow record
    @Transactional
    public void deleteById(Long borrowId) {
        borrowRepository.deleteById(borrowId);
    }

    // Get current borrows for a member
    public List<BorrowRecord> getCurrentBorrows(Long userId) {
        return borrowRepository.findByUserIdAndReturnAtIsNull(userId);
    }

    // Get borrowing history
    public List<BorrowRecord> getBorrowingHistory(Long userId) {
        return borrowRepository.findByUserIdOrderByBorrowAtDesc(userId);
    }

   // In BorrowService.java
    public List<BorrowRecord> findOverdueBorrows() {
    LocalDate today = LocalDate.now();
    return borrowRepository.findAllByReturnAtIsNullAndDueDateBefore(today);
}
    // Total active borrows
    public long getTotalActiveBorrows() {
        return borrowRepository.countByReturnAtIsNull();
    }

    // Total overdue
    // Get overdue count for a user by userId
    public long getOverdueCount(Long userId) {
    Timestamp now = Timestamp.valueOf(LocalDateTime.now());
    return borrowRepository.findByUserIdAndReturnAtIsNull(userId).stream()
            .filter(r -> r.getDueDate().before(now))
            .count();
}
    public List<BorrowRecord> findAllWithBooksAndUsers() {
        // If you want to be explicit (especially with LAZY fetch later)
        return borrowRepository.findAllWithBooksAndUsers();
     }

}
