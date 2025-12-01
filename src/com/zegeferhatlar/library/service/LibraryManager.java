package com.zegeferhatlar.library.service;

import com.zegeferhatlar.library.model.Book;
import com.zegeferhatlar.library.model.Loan;
import com.zegeferhatlar.library.model.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LibraryManager is responsible for managing books, members and loans.
 * It implements Searchable to provide search operations on books.
 */
public class LibraryManager implements Searchable {

    private List<Book> books;
    private List<Member> members;
    private List<Loan> loans;

    public LibraryManager() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    // --- Book operations ---

    public void addBook(Book book) {
        books.add(book);
    }

    public boolean removeBook(String isbn) {
        return books.removeIf(b -> b.getIsbn().equals(isbn));
    }

    public Book findBookByIsbn(String isbn) {
        return books.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    // --- Member operations ---

    public void addMember(Member member) {
        members.add(member);
    }

    public Member findMemberById(int id) {
        return members.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // --- Searchable implementation ---

    @Override
    public List<Book> searchByTitle(String title) {
        String lower = title.toLowerCase();
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        String lower = author.toLowerCase();
        return books.stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    // --- Borrow / Return operations ---

    /**
     * Borrows a book for a member.
     *
     * @param memberId id of the member
     * @param isbn     isbn of the book
     * @return created Loan if successful, null otherwise
     */
    public Loan borrowBook(int memberId, String isbn) {
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member bulunamadı: " + memberId);
            return null;
        }

        Book book = findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Book bulunamadı: " + isbn);
            return null;
        }

        if (!book.isAvailable()) {
            System.out.println("Book şu anda ödünçte: " + book.getTitle());
            return null;
        }

        // Bu member'ın aktif loan sayısını kontrol et (maxBooks limitine göre)
        long activeLoansCount = loans.stream()
                .filter(l -> l.getMember().getId() == memberId)
                .filter(l -> l.getReturnDate() == null)
                .count();

        if (activeLoansCount >= member.getMaxBooks()) {
            System.out.println("Member maxBooks limitine ulaşmış: " + member.getName());
            return null;
        }

        // Kitabı ödünç ver
        book.setAvailable(false);
        Loan loan = new Loan(book, member);
        loans.add(loan);

        System.out.println("Book ödünç verildi: " + book.getTitle() + " -> " + member.getName());
        return loan;
    }

    /**
     * Returns a borrowed book and calculates late fee.
     *
     * @param memberId id of the member
     * @param isbn     isbn of the book
     * @return calculated late fee (0 if no fee or operation failed)
     */
    public double returnBook(int memberId, String isbn) {
        // Aktif loan'ı bul (returnDate null olan)
        Loan loan = loans.stream()
                .filter(l -> l.getMember().getId() == memberId)
                .filter(l -> l.getBook().getIsbn().equals(isbn))
                .filter(l -> l.getReturnDate() == null)
                .findFirst()
                .orElse(null);

        if (loan == null) {
            System.out.println("Aktif loan bulunamadı. MemberId: " + memberId + ", isbn: " + isbn);
            return 0.0;
        }

        // Kitabı iade et
        loan.returnBook();

        // Gecikme ücretini hesapla
        double fee = loan.calculateLateFee();
        System.out.println("Book iade edildi: " + loan.getBook().getTitle() +
                ", gecikme ücreti: " + fee);

        return fee;
    }

    // --- Getter'lar (ileride lazım olabilir) ---

    public List<Book> getBooks() {
        return books;
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Loan> getLoans() {
        return loans;
    }
}
