package project.library.demo.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import project.library.demo.entity.BorrowRecord;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface BorrowRepository extends JpaRepository<BorrowRecord, Long> {

    // Member-specific
    List<BorrowRecord> findByUserIdAndReturnAtIsNull(Long userId);
    List<BorrowRecord> findByUserIdOrderByBorrowAtDesc(Long userId);

    // Dashboard stats
    long countByReturnAtIsNull(); // Active borrows

    long countByReturnAtIsNullAndDueDateBefore(Timestamp now); // Overdue

    long countByBorrowAtAfter(LocalDateTime startOfMonth); // Borrows this month

    // Optional: overdue list for admin view
    List<BorrowRecord> findAllByReturnAtIsNullAndDueDateBefore(Timestamp now);

    @Query("SELECT b FROM BorrowRecord b JOIN FETCH b.book JOIN FETCH b.user")
    List<BorrowRecord> findAllWithBooksAndUsers();

    // or even better with entity graph
    @EntityGraph(attributePaths = {"book", "user"})
    @NonNull
    List<BorrowRecord> findAll();
}