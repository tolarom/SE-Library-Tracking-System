package project.library.demo.dto;

import java.sql.Timestamp;

public class BorrowDTO {
    private Long id;
    private String bookTitle;
    private String coverImage;
    private Timestamp borrowAt;
    private Timestamp dueDate;
    private Timestamp returnAt;
    private String status;
    private boolean overdue;

    public BorrowDTO(Long id, String bookTitle, String coverImage,
                     Timestamp borrowAt, Timestamp dueDate, Timestamp returnAt,
                     String status, boolean overdue) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.coverImage = coverImage;
        this.borrowAt = borrowAt;
        this.dueDate = dueDate;
        this.returnAt = returnAt;
        this.status = status;
        this.overdue = overdue;
    }

    // Getters
    public Long getId() { return id; }
    public String getBookTitle() { return bookTitle; }
    public String getCoverImage() { return coverImage; }
    public Timestamp getBorrowAt() { return borrowAt; }
    public Timestamp getDueDate() { return dueDate; }
    public Timestamp getReturnAt() { return returnAt; }
    public String getStatus() { return status; }
    public boolean isOverdue() { return overdue; }
}