package project.library.demo;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.Table;

@Entity
//@Table(name = "borrow_records")
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp returnAt;
    private String bookId;
    private String userId;
    private Timestamp borrowAt;
    private Timestamp dueDate;
    private String status;
    private boolean overdue;

    // Constructors
    public BorrowRecord() {}

    public BorrowRecord(String bookId, String userId,
                        Timestamp borrowAt, Timestamp dueDate,
                        String status, boolean overdue) {
        this.bookId = bookId;
        this.userId = userId;
        this.borrowAt = borrowAt;
        this.dueDate = dueDate;
        this.status = status;
        this.overdue = overdue;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Timestamp getBorrowAt() { return borrowAt; }
    public void setBorrowAt(Timestamp borrowAt) { this.borrowAt = borrowAt; }

    public Timestamp getReturnAt() { return returnAt; }
    public void setReturnAt(Timestamp returnAt) { this.returnAt = returnAt; }

    public Timestamp getDueDate() { return dueDate; }
    public void setDueDate(Timestamp dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isOverdue() { return overdue; }
    public void setOverdue(boolean overdue) { this.overdue = overdue; }
}
