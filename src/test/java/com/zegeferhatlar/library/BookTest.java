import com.zegeferhatlar.library.model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    void newBook_shouldBeAvailableByDefault() {
        Book book = new Book("123", "Test Kitabı", "Test Yazar");

        assertEquals("123", book.getIsbn());
        assertEquals("Test Kitabı", book.getTitle());
        assertEquals("Test Yazar", book.getAuthor());
        assertTrue(book.isAvailable(), "Yeni book default olarak available olmalı");
    }

    @Test
    void setAvailable_shouldChangeAvailability() {
        Book book = new Book("123", "Test Kitabı", "Test Yazar");

        book.setAvailable(false);

        assertFalse(book.isAvailable());
    }
}
