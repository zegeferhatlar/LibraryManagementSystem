package com.zegeferhatlar.library.service;

import com.zegeferhatlar.library.model.Librarian;

/**
 * Service class for handling librarian authentication.
 * Manages login/logout operations and tracks current session.
 */
public class AuthenticationService {

    private Librarian currentLibrarian;
    private LibraryManager libraryManager;

    /**
     * Creates a new AuthenticationService instance.
     *
     * @param libraryManager LibraryManager instance to access librarians
     */
    public AuthenticationService(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
        this.currentLibrarian = null;
    }

    /**
     * Attempts to log in a librarian with the provided credentials.
     *
     * @param username username of the librarian
     * @param password password of the librarian
     * @return true if login successful, false otherwise
     */
    public boolean login(String username, String password) {
        Librarian librarian = libraryManager.findLibrarianByUsername(username);

        if (librarian == null) {
            return false;
        }

        if (librarian.verifyPassword(password)) {
            this.currentLibrarian = librarian;
            return true;
        }

        return false;
    }

    /**
     * Logs out the current librarian.
     */
    public void logout() {
        this.currentLibrarian = null;
    }

    /**
     * Checks if a librarian is currently logged in.
     *
     * @return true if logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return currentLibrarian != null;
    }

    /**
     * Returns the currently logged in librarian.
     *
     * @return current librarian or null if not logged in
     */
    public Librarian getCurrentLibrarian() {
        return currentLibrarian;
    }

    /**
     * Returns the name of the currently logged in librarian.
     *
     * @return name of current librarian or null if not logged in
     */
    public String getCurrentLibrarianName() {
        return currentLibrarian != null ? currentLibrarian.getName() : null;
    }
}
