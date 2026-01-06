package project.library.demo.dto;

public class DashboardStats {
    private int totalBooks;
    private int totalMembers;
    private int activeBorrows;
    private int overdueBooks;
    private int newMembersThisMonth;
    private int borrowsThisMonth;

    // Getters and Setters
    public int getTotalBooks() { return totalBooks; }
    public void setTotalBooks(int totalBooks) { this.totalBooks = totalBooks; }

    public int getTotalMembers() { return totalMembers; }
    public void setTotalMembers(int totalMembers) { this.totalMembers = totalMembers; }

    public int getActiveBorrows() { return activeBorrows; }
    public void setActiveBorrows(int activeBorrows) { this.activeBorrows = activeBorrows; }

    public int getOverdueBooks() { return overdueBooks; }
    public void setOverdueBooks(int overdueBooks) { this.overdueBooks = overdueBooks; }

    public int getNewMembersThisMonth() { return newMembersThisMonth; }
    public void setNewMembersThisMonth(int newMembersThisMonth) { this.newMembersThisMonth = newMembersThisMonth; }

    public int getBorrowsThisMonth() { return borrowsThisMonth; }
    public void setBorrowsThisMonth(int borrowsThisMonth) { this.borrowsThisMonth = borrowsThisMonth; }
}