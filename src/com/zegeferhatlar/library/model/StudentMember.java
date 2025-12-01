package com.zegeferhatlar.library.model;

/**
 * Represents a student type member.
 * StudentMember has its own fee policy and maxBooks limit.
 */
public class StudentMember extends Member {

    private static final int DEFAULT_MAX_BOOKS = 5;

    /**
     * Creates a new StudentMember with default maxBooks value.
     *
     * @param id   unique id of the student
     * @param name full name of the student
     */
    public StudentMember(int id, String name) {
        super(id, name, DEFAULT_MAX_BOOKS);
    }

    /**
     * Calculates late fee for a student member.
     * For example: each overdue day costs 0.5 currency units.
     *
     * @param overdueDays number of days the book is overdue
     * @return calculated fee as double
     */
    @Override
    public double calculateFee(int overdueDays) {
        if (overdueDays <= 0) {
            return 0.0;
        }
        double dailyRate = 0.5; // öğrencilere indirimli günlük gecikme ücreti
        return overdueDays * dailyRate;
    }
}
