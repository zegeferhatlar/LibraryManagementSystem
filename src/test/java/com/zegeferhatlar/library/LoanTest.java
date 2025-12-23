package com.zegeferhatlar.library;

import com.zegeferhatlar.library.model.Book;
import com.zegeferhatlar.library.model.Loan;
import com.zegeferhatlar.library.model.StudentMember;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanTest {

    @Test
    void calculateOverdueDays_shouldReturnZero_whenWithinAllowedDays() {
        Book book = new Book("123", "Test Kitabı", "Test Yazar");
        StudentMember member = new StudentMember(1, "Ali");

        LocalDate loanDate = LocalDate.now().minusDays(10); // 14 günden az
        Loan loan = new Loan(book, member, loanDate);

        assertEquals(0, loan.calculateOverdueDays());
    }

    @Test
    void calculateOverdueDays_shouldReturnPositive_whenOverAllowedDays() {
        Book book = new Book("123", "Test Kitabı", "Test Yazar");
        StudentMember member = new StudentMember(1, "Ali");

        LocalDate loanDate = LocalDate.now().minusDays(20); // 14'ten 6 fazla
        Loan loan = new Loan(book, member, loanDate);

        assertEquals(6, loan.calculateOverdueDays());
    }

    @Test
    void calculateLateFee_shouldUseMemberPolicy() {
        Book book = new Book("123", "Test Kitabı", "Test Yazar");
        StudentMember member = new StudentMember(1, "Ali");

        LocalDate loanDate = LocalDate.now().minusDays(18); // 4 gün gecikme
        Loan loan = new Loan(book, member, loanDate);

        double fee = loan.calculateLateFee();

        assertEquals(2.0, fee, 0.0001); // 4 * 0.5
    }
}
