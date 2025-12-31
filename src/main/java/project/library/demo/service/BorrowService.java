package project.library.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.library.demo.entity.Book;
import project.library.demo.entity.BorrowRecord;
import project.library.demo.entity.User;
import project.library.demo.repo.BookRepository;
import project.library.demo.repo.BorrowRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

    private static final int BORROW_DAYS = 14;
    // Note: No fine system yet in entity → we'll skip fine calculation for now

    // Borrow a book
    @Transactional
    public void borrowBook(User member, Book book) {
        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No copies available");
        }

        BorrowRecord record = new BorrowRecord();
        record.setUserId(member.getId());
        record.setBookId(book.getId());
        record.setBorrowAt(Timestamp.valueOf(LocalDateTime.now()));
        record.setDueDate(Timestamp.valueOf(LocalDateTime.now().plusDays(BORROW_DAYS)));
        record.setReturnAt(null);           // not returned yet
        record.setStatus("BORROWED");
        record.setOverdue(false);

        borrowRepository.save(record);

        // Decrease available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
    }

    // Return a book
    @Transactional
    public void returnBook(Long borrowId) {
        BorrowRecord record = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (record.getReturnAt() != null) {
            throw new RuntimeException("Book already returned");
        }

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        record.setReturnAt(now);

        // Check if overdue
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

    // Get current borrows for a member (not returned)
    public List<BorrowRecord> getCurrentBorrows(User member) {
        return borrowRepository.findByUserIdAndReturnAtIsNull(member.getId());
    }

    // Get full borrowing history for a member (newest first)
    public List<BorrowRecord> getBorrowingHistory(User member) {
        return borrowRepository.findByUserIdOrderByBorrowAtDesc(member.getId());
    }

    // Get all currently overdue loans (system-wide)
    public List<BorrowRecord> getAllOverdue() {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        return borrowRepository.findOverdueByUserId(null, now); // You'll need to adjust repo or write custom query
        // Temporary workaround if method doesn't exist yet:
        // return borrowRepository.findAllByReturnAtIsNullAndDueDateBefore(now);
    }

    // Get member's overdue count
    public long getOverdueCount(User member) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        // You'll need a repo method like countOverdueByUserId(Long userId, Timestamp today)
        // For now, filter in memory (not ideal for large data)
        return borrowRepository.findByUserIdAndReturnAtIsNull(member.getId()).stream()
                .filter(r -> r.getDueDate().before(now))
                .count();
    }

    // Get total active borrows (system-wide)
    public long getTotalActiveBorrows() {
        return borrowRepository.countByReturnAtIsNull();
    }

    // Get total overdue count (system-wide)
    public long getTotalOverdueCount() {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        return borrowRepository.countOverdueLoans(now);
    }

    // Note: payFine() removed because no fineAmount/paid fields exist yet
}