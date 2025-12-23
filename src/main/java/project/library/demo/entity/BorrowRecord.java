package project.library.demo.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "borrow_record")
public class BorrowRecord {

    @Id
    private String id;
    @Column(name = "returned_at")
    private Timestamp returnAt;
    @Column(name = "book_id")
    private String bookId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "borrowed_at")
    private Timestamp borrowAt;
    @Column(name = "due_date")
    private Timestamp dueDate;
    private String status;
    @Column(name = "is_overdue")
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
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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
