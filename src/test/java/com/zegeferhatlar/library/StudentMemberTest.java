package com.zegeferhatlar.library;

import com.zegeferhatlar.library.model.StudentMember;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentMemberTest {

    @Test
    void constructor_shouldSetDefaultMaxBooks() {
        StudentMember member = new StudentMember(1, "Ali Veli");

        assertEquals(1, member.getId());
        assertEquals("Ali Veli", member.getName());
        assertEquals(5, member.getMaxBooks(), "StudentMember default maxBooks 5 olmalı");
    }

    @Test
    void calculateFee_shouldReturnZero_whenNoOverdue() {
        StudentMember member = new StudentMember(1, "Ali");

        double fee = member.calculateFee(0);

        assertEquals(0.0, fee, 0.0001);
    }

    @Test
    void calculateFee_shouldReturnZero_whenNegativeOverdue() {
        StudentMember member = new StudentMember(1, "Ali");

        double fee = member.calculateFee(-5);

        assertEquals(0.0, fee, 0.0001);
    }

    @Test
    void calculateFee_shouldCalculateCorrectly_whenOverdue() {
        StudentMember member = new StudentMember(1, "Ali");

        // 10 gün gecikme, günlük 0.5 TL
        double fee = member.calculateFee(10);

        assertEquals(5.0, fee, 0.0001); // 10 * 0.5 = 5.0
    }

    @Test
    void calculateFee_shouldUseStudentDiscount() {
        StudentMember member = new StudentMember(1, "Ali");

        // 4 gün gecikme
        double fee = member.calculateFee(4);

        assertEquals(2.0, fee, 0.0001); // 4 * 0.5 = 2.0 (öğrenci indirimli ücret)
    }

    @Test
    void inheritance_shouldWorkCorrectly() {
        StudentMember student = new StudentMember(1, "Ali");

        // Member sınıfından gelen metodlar çalışmalı
        student.setName("Mehmet");
        assertEquals("Mehmet", student.getName());

        student.setMaxBooks(10);
        assertEquals(10, student.getMaxBooks());
    }
}
