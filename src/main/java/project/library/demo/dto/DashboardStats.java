package project.library.demo.dto;

public class DashboardStats {
    private int totalBooks;
    private int totalMembers;
    private int activeBorrows;
    private int overdueBorrows;
    private int newMembersThisMonth;
    private int borrowsThisMonth;

    // Getters and Setters
    public int getTotalBooks() { return totalBooks; }
    public void setTotalBooks(int totalBooks) { this.totalBooks = totalBooks; }

    public int getTotalMembers() { return totalMembers; }
    public void setTotalMembers(int totalMembers) { this.totalMembers = totalMembers; }

    public int getActiveBorrows() { return activeBorrows; }
    public void setActiveBorrows(int activeBorrows) { this.activeBorrows = activeBorrows; }

    public int getOverdueBorrows() { return overdueBorrows; }
    public void setOverdueBorrows(int overdueBorrows) { this.overdueBorrows = overdueBorrows; }

    public int getNewMembersThisMonth() { return newMembersThisMonth; }
    public void setNewMembersThisMonth(int newMembersThisMonth) { this.newMembersThisMonth = newMembersThisMonth; }

    public int getBorrowsThisMonth() { return borrowsThisMonth; }
    public void setBorrowsThisMonth(int borrowsThisMonth) { this.borrowsThisMonth = borrowsThisMonth; }
}