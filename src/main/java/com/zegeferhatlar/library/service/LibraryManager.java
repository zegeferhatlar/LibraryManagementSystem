package com.zegeferhatlar.library.service;

import com.zegeferhatlar.library.model.Book;
import com.zegeferhatlar.library.model.Librarian;
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
    private List<Librarian> librarians;

    public LibraryManager() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.librarians = new ArrayList<>();
    }

    // --- Book operations ---

    /**
     * Adds a new book to the library.
     * 
     * @param book the book to add
     * @return true if book was added successfully, false if book with same ISBN
     *         already exists
     */
    public boolean addBook(Book book) {
        // ISBN kontrolü: Aynı ISBN'e sahip kitap var mı?
        if (findBookByIsbn(book.getIsbn()) != null) {
            return false; // Aynı ISBN'e sahip kitap zaten var
        }
        books.add(book);
        return true;
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

    /**
     * Adds a new member to the library.
     * 
     * @param member the member to add
     * @return true if member was added successfully, false if member with same ID
     *         already exists
     */
    public boolean addMember(Member member) {
        // ID kontrolü: Aynı ID'ye sahip üye var mı?
        if (findMemberById(member.getId()) != null) {
            return false; // Aynı ID'ye sahip üye zaten var
        }
        members.add(member);
        return true;
    }

    public Member findMemberById(int id) {
        return members.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Removes a member from the library by ID.
     * 
     * @param memberId the ID of the member to remove
     * @return true if member was removed successfully, false if member not found
     */
    public boolean removeMember(int memberId) {
        return members.removeIf(m -> m.getId() == memberId);
    }

    // --- Librarian operations ---

    public void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
    }

    public Librarian findLibrarianByUsername(String username) {
        return librarians.stream()
                .filter(l -> l.getUsername().equals(username))
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
            System.out.println("Üye bulunamadı: " + memberId);
            return null;
        }

        Book book = findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Kitap bulunamadı: " + isbn);
            return null;
        }

        if (!book.isAvailable()) {
            System.out.println("Kitap şu anda ödünçte: " + book.getTitle());
            return null;
        }

        // Bu üyenin aktif ödünç sayısını kontrol et (maxBooks limitine göre)
        long activeLoansCount = loans.stream()
                .filter(l -> l.getMember().getId() == memberId)
                .filter(l -> l.getReturnDate() == null)
                .count();

        if (activeLoansCount >= member.getMaxBooks()) {
            System.out.println("Üye maksimum kitap limitine ulaşmış: " + member.getName());
            return null;
        }

        // Kitabı ödünç ver
        book.setAvailable(false);
        Loan loan = new Loan(book, member);
        loans.add(loan);

        System.out.println("Kitap ödünç verildi: " + book.getTitle() + " -> " + member.getName());
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
            System.out.println("Aktif ödünç kaydı bulunamadı. Üye ID: " + memberId + ", ISBN: " + isbn);
            return 0.0;
        }

        // Kitabı iade et
        loan.returnBook();

        // Gecikme ücretini hesapla
        double fee = loan.calculateLateFee();
        System.out.println("Kitap iade edildi: " + loan.getBook().getTitle() +
                ", gecikme ücreti: " + fee);

        return fee;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    /**
     * Returns all active loans (not returned yet).
     */
    public List<Loan> getActiveLoans() {
        return loans.stream()
                .filter(l -> l.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    /**
     * Returns loans that are overdue (gecikmiş).
     */
    public List<Loan> getOverdueLoans() {
        return loans.stream()
                .filter(l -> l.getReturnDate() == null)
                .filter(l -> l.calculateOverdueDays() > 0)
                .collect(Collectors.toList());
    }

    public void saveToFiles() {
        FileStorage storage = new FileStorage();
        try {
            storage.saveBooks(books);
            storage.saveMembers(members);
            storage.saveLoans(loans);
            storage.saveLibrarians(librarians);
        } catch (Exception e) {
            System.out.println("Kaydetme hatası: " + e.getMessage());
        }
    }

    public void loadFromFiles() {
        FileStorage storage = new FileStorage();
        try {
            List<Book> loadedBooks = storage.loadBooks();
            List<Member> loadedMembers = storage.loadMembers();
            List<Loan> loadedLoans = storage.loadLoans(loadedBooks, loadedMembers);
            List<Librarian> loadedLibrarians = storage.loadLibrarians();

            this.books.clear();
            this.members.clear();
            this.loans.clear();
            this.librarians.clear();

            this.books.addAll(loadedBooks);
            this.members.addAll(loadedMembers);
            this.loans.addAll(loadedLoans);
            this.librarians.addAll(loadedLibrarians);

            System.out.println("Veriler dosyadan yüklendi. (data/ klasörü)");
        } catch (Exception e) {
            System.out.println("Yükleme hatası: " + e.getMessage());
        }
    }

}
