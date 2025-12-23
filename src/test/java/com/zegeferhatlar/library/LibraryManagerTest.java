package com.zegeferhatlar.library;

import com.zegeferhatlar.library.model.Book;
import com.zegeferhatlar.library.model.Loan;
import com.zegeferhatlar.library.model.StudentMember;
import com.zegeferhatlar.library.service.LibraryManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryManagerTest {

    @Test
    void addBook_and_findBookByIsbn_shouldWork() {
        LibraryManager manager = new LibraryManager();
        Book book = new Book("111", "Küçük Prens", "Exupery");

        manager.addBook(book);
        Book found = manager.findBookByIsbn("111");

        assertNotNull(found);
        assertEquals("Küçük Prens", found.getTitle());
    }

    @Test
    void borrowBook_shouldCreateLoan_andSetBookUnavailable() {
        LibraryManager manager = new LibraryManager();
        Book book = new Book("111", "Küçük Prens", "Exupery");
        manager.addBook(book);

        StudentMember member = new StudentMember(1, "Ali");
        manager.addMember(member);

        Loan loan = manager.borrowBook(1, "111");

        assertNotNull(loan, "Loan oluşturulmalı");
        assertFalse(book.isAvailable(), "Book artık available olmamalı");
        assertEquals(1, manager.getLoans().size());
    }

    @Test
    void borrowBook_shouldFail_whenBookNotAvailable() {
        LibraryManager manager = new LibraryManager();
        Book book = new Book("111", "Küçük Prens", "Exupery");
        manager.addBook(book);

        StudentMember member = new StudentMember(1, "Ali");
        manager.addMember(member);

        Loan first = manager.borrowBook(1, "111");
        assertNotNull(first);

        Loan second = manager.borrowBook(1, "111");
        assertNull(second, "Aynı book ikinci kez ödünç alınamamalı");
    }
}
