package project.library.demo.dto;
import java.sql.Timestamp;

public class MemberBorrowDTO {
    private Long borrowId;
    private String bookTitle;
    private String coverImage;
    private Timestamp borrowAt;
    private Timestamp dueDate;
    private Timestamp returnAt;
    private String status;
    private boolean overdue;

    // Constructor
    public MemberBorrowDTO(Long borrowId, String bookTitle, String coverImage,
                           Timestamp borrowAt, Timestamp dueDate, Timestamp returnAt,
                           String status, boolean overdue) {
        this.borrowId = borrowId;
        this.bookTitle = bookTitle;
        this.coverImage = coverImage;
        this.borrowAt = borrowAt;
        this.dueDate = dueDate;
        this.returnAt = returnAt;
        this.status = status;
        this.overdue = overdue;
    }

    // Getters (no setters needed for view)
    public Long getBorrowId() { return borrowId; }
    public String getBookTitle() { return bookTitle; }
    public String getCoverImage() { return coverImage; }
    public Timestamp getBorrowAt() { return borrowAt; }
    public Timestamp getDueDate() { return dueDate; }
    public Timestamp getReturnAt() { return returnAt; }
    public String getStatus() { return status; }
    public boolean isOverdue() { return overdue; }
}