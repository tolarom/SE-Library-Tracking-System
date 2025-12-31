package project.library.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.library.demo.entity.BorrowRecord;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<BorrowRecord, Long> {

    // Total active borrows (not returned)
    long countByReturnAtIsNull();

    // Total overdue loans (not returned AND past due date)
    @Query("SELECT COUNT(b) FROM BorrowRecord b " +
           "WHERE b.returnAt IS NULL AND b.dueDate < :today")
    long countOverdueLoans(@Param("today") Timestamp today);

    // Active borrows for a specific user
    List<BorrowRecord> findByUserIdAndReturnAtIsNull(Long userId);

    // Count active borrows for a specific user
    long countByUserIdAndReturnAtIsNull(Long userId);

    // All borrowing records for a user, newest first
    List<BorrowRecord> findByUserIdOrderByBorrowAtDesc(Long userId);

    // Overdue borrows for a specific user
    @Query("SELECT b FROM BorrowRecord b " +
           "WHERE b.userId = :userId " +
           "AND b.returnAt IS NULL " +
           "AND b.dueDate < :today")
    List<BorrowRecord> findOverdueByUserId(@Param("userId") Long userId,
                                          @Param("today") Timestamp today);
}