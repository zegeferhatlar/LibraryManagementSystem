package com.zegeferhatlar.library.model;

/**
 * Base abstract class for all library members.
 * Holds common fields like id, name and maxBooks.
 * Subclasses must implement calculateFee method to define
 * their own late fee policy.
 */
public abstract class Member {

    // --- Fields ---
    private int id;
    private String name;
    private int maxBooks;

    /**
     * Creates a new Member instance.
     *
     * @param id       unique id of the member
     * @param name     full name of the member
     * @param maxBooks maximum number of books that this member can borrow at once
     */
    public Member(int id, String name, int maxBooks) {
        this.id = id;
        this.name = name;
        this.maxBooks = maxBooks;
    }

    // --- Getters & Setters ---

    /**
     * Returns the id of this member.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of this member.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of this member.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this member.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the maximum number of books that this member can borrow.
     */
    public int getMaxBooks() {
        return maxBooks;
    }

    /**
     * Sets the maximum number of books that this member can borrow.
     */
    public void setMaxBooks(int maxBooks) {
        this.maxBooks = maxBooks;
    }

    /**
     * Calculates late fee for this member type based on overdue days.
     * Subclasses must provide their own implementation.
     *
     * @param overdueDays number of days the book is overdue
     * @return calculated fee as double
     */
    public abstract double calculateFee(int overdueDays);

    @Override
    public String toString() {
        return String.format("Member{id=%d, name='%s', maxBooks=%d}", id, name, maxBooks);
    }
}
