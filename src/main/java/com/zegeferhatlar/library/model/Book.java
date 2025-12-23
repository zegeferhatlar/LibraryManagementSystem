package com.zegeferhatlar.library.model;

/**
 * Represents a book in the library.
 * Each book has an ISBN, a title, an author and
 * an availability status that shows whether it can be borrowed.
 */
public class Book {

    // --- Fields (Encapsulation: private) ---
    private String isbn;
    private String title;
    private String author;
    private boolean available;

    /**
     * Creates a new Book instance.
     *
     * @param isbn   unique identifier of the book
     * @param title  title of the book
     * @param author author of the book
     */
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    // --- Getters & Setters ---

    /**
     * Returns the ISBN of this book.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of this book.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Returns the title of this book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of this book.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the author of this book.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of this book.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns whether this book is currently available for borrowing.
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Updates the availability status of this book.
     *
     * @param available true if the book is available, false if it is on loan
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    // --- Utility methods ---

    @Override
    public String toString() {
        String status = available ? "Müsait" : "Ödünçte";
        return String.format("[%s] %s - %s (%s)", isbn, title, author, status);
    }
}
