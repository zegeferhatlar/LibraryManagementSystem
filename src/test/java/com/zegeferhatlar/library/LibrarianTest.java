package com.zegeferhatlar.library;

import com.zegeferhatlar.library.model.Librarian;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarianTest {

    @Test
    void constructor_shouldSetAllFields() {
        Librarian librarian = new Librarian("admin", "password123", "Ahmet Yılmaz");

        assertEquals("admin", librarian.getUsername());
        assertEquals("password123", librarian.getPassword());
        assertEquals("Ahmet Yılmaz", librarian.getName());
    }

    @Test
    void verifyPassword_shouldReturnTrue_whenPasswordMatches() {
        Librarian librarian = new Librarian("admin", "password123", "Ahmet Yılmaz");

        assertTrue(librarian.verifyPassword("password123"));
    }

    @Test
    void verifyPassword_shouldReturnFalse_whenPasswordDoesNotMatch() {
        Librarian librarian = new Librarian("admin", "password123", "Ahmet Yılmaz");

        assertFalse(librarian.verifyPassword("wrongpassword"));
    }

    @Test
    void setUsername_shouldUpdateUsername() {
        Librarian librarian = new Librarian("admin", "password123", "Ahmet Yılmaz");

        librarian.setUsername("newadmin");

        assertEquals("newadmin", librarian.getUsername());
    }

    @Test
    void setPassword_shouldUpdatePassword() {
        Librarian librarian = new Librarian("admin", "password123", "Ahmet Yılmaz");

        librarian.setPassword("newpassword");

        assertEquals("newpassword", librarian.getPassword());
        assertTrue(librarian.verifyPassword("newpassword"));
    }

    @Test
    void setName_shouldUpdateName() {
        Librarian librarian = new Librarian("admin", "password123", "Ahmet Yılmaz");

        librarian.setName("Mehmet Demir");

        assertEquals("Mehmet Demir", librarian.getName());
    }

    @Test
    void toString_shouldContainUsernameAndName() {
        Librarian librarian = new Librarian("admin", "password123", "Ahmet Yılmaz");

        String result = librarian.toString();

        assertTrue(result.contains("admin"));
        assertTrue(result.contains("Ahmet Yılmaz"));
        assertFalse(result.contains("password123"), "toString şifreyi içermemeli");
    }
}
