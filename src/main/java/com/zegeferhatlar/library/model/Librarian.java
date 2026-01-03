package com.zegeferhatlar.library.model;

/**
 * Represents a librarian in the library system.
 * Librarians can perform administrative operations like adding/removing books.
 */
public class Librarian {

    // --- Fields (Encapsulation: private) ---
    private String username;
    private String password;
    private String name;

    /**
     * Creates a new Librarian instance.
     *
     * @param username unique username for login
     * @param password password for authentication
     * @param name     full name of the librarian
     */
    public Librarian(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    // --- Getters & Setters ---

    /**
     * Returns the username of this librarian.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of this librarian.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of this librarian.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of this librarian.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the name of this librarian.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this librarian.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if the provided password matches this librarian's password.
     *
     * @param inputPassword password to verify
     * @return true if password matches, false otherwise
     */
    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    @Override
    public String toString() {
        return String.format("Librarian{username='%s', name='%s'}", username, name);
    }
}
