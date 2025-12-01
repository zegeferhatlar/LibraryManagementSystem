package com.zegeferhatlar.library.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a loan operation where a member borrows a book.
 * Stores loan date, return date and provides overdue calculations.
 */
public class Loan {

    private Book book;
    private Member member;
    private LocalDate loanDate;
    private LocalDate returnDate;

    /**
     * Creates a new Loan instance.
     *
     * @param book   the borrowed book
     * @param member the member who borrows the book
     */
    public Loan(Book book, Member member) {
        this.book = book;
        this.member = member;
        this.loanDate = LocalDate.now();
        this.returnDate = null;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Marks the book as returned today.
     */
    public void returnBook() {
        this.returnDate = LocalDate.now();
        this.book.setAvailable(true);
    }

    /**
     * Calculates overdue days.
     * Library policy: A book must be returned within 14 days.
     *
     * @return overdue days (0 if no delay)
     */
    public int calculateOverdueDays() {
        LocalDate today = (returnDate != null) ? returnDate : LocalDate.now();

        long daysPassed = ChronoUnit.DAYS.between(loanDate, today);
        int allowedDays = 14;

        if (daysPassed > allowedDays) {
            return (int) (daysPassed - allowedDays);
        } else {
            return 0;
        }
    }

    /**
     * Calculates and returns the late fee using member's fee policy.
     *
     * @return late fee amount
     */
    public double calculateLateFee() {
        int overdue = calculateOverdueDays();
        return member.calculateFee(overdue);
    }

    @Override
    public String toString() {
        return String.format("Loan(book=%s, member=%s, loanDate=%s, returnDate=%s)",
                book.getTitle(),
                member.getName(),
                loanDate,
                returnDate != null ? returnDate : "Henüz İade Edilmedi");
    }
}
