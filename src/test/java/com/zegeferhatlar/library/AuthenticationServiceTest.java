package com.zegeferhatlar.library;

import com.zegeferhatlar.library.model.Librarian;
import com.zegeferhatlar.library.service.AuthenticationService;
import com.zegeferhatlar.library.service.LibraryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceTest {

    private LibraryManager libraryManager;
    private AuthenticationService authService;

    @BeforeEach
    void setUp() {
        libraryManager = new LibraryManager();
        authService = new AuthenticationService(libraryManager);
    }

    @Test
    void login_shouldReturnTrue_whenCredentialsAreCorrect() {
        Librarian librarian = new Librarian("admin", "password123", "Ahmet Yılmaz");
        libraryManager.addLibrarian(librarian);

        boolean result = authService.login("admin", "password123");

        assertTrue(result);
        assertTrue(authService.isLoggedIn());
        assertNotNull(authService.getCurrentLibrarian());
        assertEquals("Ahmet Yılmaz", authService.getCurrentLibrarianName());
    }

    @Test
    void login_shouldReturnFalse_whenUsernameIsWrong() {
        Librarian librarian = new Librarian("admin", "password123", "Ahmet Yılmaz");
        libraryManager.addLibrarian(librarian);

        boolean result = authService.login("wronguser", "password123");

        assertFalse(result);
        assertFalse(authService.isLoggedIn());
        assertNull(authService.getCurrentLibrarian());
    }

    @Test
    void login_shouldReturnFalse_whenPasswordIsWrong() {
        Librarian librarian = new Librarian("admin", "password123", "Ahmet Yılmaz");
        libraryManager.addLibrarian(librarian);

        boolean result = authService.login("admin", "wrongpassword");

        assertFalse(result);
        assertFalse(authService.isLoggedIn());
    }

    @Test
    void login_shouldReturnFalse_whenLibrarianDoesNotExist() {
        boolean result = authService.login("nonexistent", "password123");

        assertFalse(result);
        assertFalse(authService.isLoggedIn());
    }

    @Test
    void logout_shouldClearCurrentLibrarian() {
        Librarian librarian = new Librarian("admin", "password123", "Ahmet Yılmaz");
        libraryManager.addLibrarian(librarian);
        authService.login("admin", "password123");

        assertTrue(authService.isLoggedIn());

        authService.logout();

        assertFalse(authService.isLoggedIn());
        assertNull(authService.getCurrentLibrarian());
        assertNull(authService.getCurrentLibrarianName());
    }

    @Test
    void isLoggedIn_shouldReturnFalse_initially() {
        assertFalse(authService.isLoggedIn());
    }

    @Test
    void getCurrentLibrarian_shouldReturnNull_whenNotLoggedIn() {
        assertNull(authService.getCurrentLibrarian());
    }

    @Test
    void getCurrentLibrarianName_shouldReturnNull_whenNotLoggedIn() {
        assertNull(authService.getCurrentLibrarianName());
    }

    @Test
    void login_shouldReplacePreviousLogin_whenLoggedInTwice() {
        Librarian librarian1 = new Librarian("admin1", "pass1", "Ahmet");
        Librarian librarian2 = new Librarian("admin2", "pass2", "Mehmet");
        libraryManager.addLibrarian(librarian1);
        libraryManager.addLibrarian(librarian2);

        authService.login("admin1", "pass1");
        assertEquals("Ahmet", authService.getCurrentLibrarianName());

        authService.login("admin2", "pass2");
        assertEquals("Mehmet", authService.getCurrentLibrarianName());
        assertTrue(authService.isLoggedIn());
    }
}

