package com.zegeferhatlar.library.ui;

import com.zegeferhatlar.library.service.AuthenticationService;
import com.zegeferhatlar.library.service.LibraryManager;
import com.zegeferhatlar.library.model.Book;
import com.zegeferhatlar.library.model.StudentMember;
import java.util.Scanner;

public class LibraryConsoleUI {

    private LibraryManager manager;
    private AuthenticationService authService;
    private Scanner scanner;

    public LibraryConsoleUI() {
        this.manager = new LibraryManager();
        this.authService = new AuthenticationService(manager);
        this.scanner = new Scanner(System.in);

        manager.loadFromFiles();
    }

    public void start() {
        System.out.println("=== Kütüphane Yönetim Sistemi ===");

        while (true) {
            System.out.println("\n--- Menü ---");
            if (authService.isLoggedIn()) {
                System.out.println("Giriş yapıldı: " + authService.getCurrentLibrarianName());
            }
            System.out.println("1) Kitap Ekle " + (authService.isLoggedIn() ? "" : "(Kütüphaneci girişi gerekli)"));
            System.out.println("2) Üye Ekle " + (authService.isLoggedIn() ? "" : "(Kütüphaneci girişi gerekli)"));
            System.out.println("3) Kitap Ara (Title)");
            System.out.println("4) Kitap Ödünç Al");
            System.out.println("5) Kitap İade Et");
            System.out.println("6) Kitapları Listele");
            System.out.println("7) Üyeleri Listele");
            System.out.println("8) Aktif Loans Listele");
            System.out.println("9) Overdue Loans Listele");
            System.out.println("10) Kitap Ara (Author)");
            System.out.println("11) Kitap Sil " + (authService.isLoggedIn() ? "" : "(Kütüphaneci girişi gerekli)"));
            System.out.println("12) Kütüphaneci Girişi");
            System.out.println("13) Kütüphaneci Çıkışı");
            System.out.println("0) Çıkış");
            System.out.print("Seçenek: ");

            int choice = readInt();

            switch (choice) {
                case 1 -> addBook();
                case 2 -> addMember();
                case 3 -> searchBook();
                case 4 -> borrowBook();
                case 5 -> returnBook();
                case 6 -> listBooks();
                case 7 -> listMembers();
                case 8 -> listActiveLoans();
                case 9 -> listOverdueLoans();
                case 10 -> searchBookByAuthor();
                case 11 -> removeBook();
                case 12 -> loginLibrarian();
                case 13 -> logoutLibrarian();
                case 0 -> {
                    System.out.println("Veriler kaydediliyor...");
                    manager.saveToFiles();
                    System.out.println("Programdan çıkılıyor...");
                    return;
                }
                default -> System.out.println("Geçersiz seçim!");
            }

        }
    }

    private void addBook() {
        if (!authService.isLoggedIn()) {
            System.out.println("Bu işlem için kütüphaneci girişi gereklidir!");
            return;
        }

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        Book book = new Book(isbn, title, author);
        manager.addBook(book);

        System.out.println("Kitap eklendi.");
    }

    private void addMember() {
        if (!authService.isLoggedIn()) {
            System.out.println("Bu işlem için kütüphaneci girişi gereklidir!");
            return;
        }

        System.out.print("Üye ID: ");
        int id = readInt();

        System.out.print("Üye adı: ");
        String name = scanner.nextLine();

        StudentMember member = new StudentMember(id, name);
        manager.addMember(member);
        System.out.println("Öğrenci üye eklendi.");
    }

    private void searchBook() {
        System.out.print("Aranacak title: ");
        String title = scanner.nextLine();

        var results = manager.searchByTitle(title);
        if (results.isEmpty()) {
            System.out.println("Sonuç bulunamadı.");
        } else {
            results.forEach(System.out::println);
        }
    }

    private void borrowBook() {
        System.out.print("Üye ID: ");
        int id = readInt();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        manager.borrowBook(id, isbn);
    }

    private void returnBook() {
        System.out.print("Üye ID: ");
        int id = readInt();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        double fee = manager.returnBook(id, isbn);
        System.out.println("Gecikme Ücreti: " + fee);
    }

    private void listBooks() {
        manager.getBooks().forEach(System.out::println);
    }

    private void listMembers() {
        System.out.println("\n--- Üye Listesi ---");
        if (manager.getMembers().isEmpty()) {
            System.out.println("Kayıtlı üye yok.");
            return;
        }
        manager.getMembers().forEach(m -> System.out.println("ID: " + m.getId() + " | Name: " + m.getName()));
    }

    private void listActiveLoans() {
        System.out.println("\n--- Aktif Loans ---");
        var activeLoans = manager.getActiveLoans();

        if (activeLoans.isEmpty()) {
            System.out.println("Aktif loan yok.");
            return;
        }

        activeLoans.forEach(l -> {
            System.out.println("Member: " + l.getMember().getName()
                    + " | Book: " + l.getBook().getTitle()
                    + " | LoanDate: " + l.getLoanDate());
        });
    }

    private void listOverdueLoans() {
        System.out.println("\n--- Overdue Loans (Gecikmiş) ---");
        var overdueLoans = manager.getOverdueLoans();

        if (overdueLoans.isEmpty()) {
            System.out.println("Gecikmiş loan yok.");
            return;
        }

        overdueLoans.forEach(l -> {
            int overdueDays = l.calculateOverdueDays();
            double fee = l.calculateLateFee();
            System.out.println("Member: " + l.getMember().getName()
                    + " | Book: " + l.getBook().getTitle()
                    + " | OverdueDays: " + overdueDays
                    + " | Fee: " + fee);
        });
    }

    private void searchBookByAuthor() {
        System.out.print("Aranacak author: ");
        String author = scanner.nextLine();

        var results = manager.searchByAuthor(author);
        if (results.isEmpty()) {
            System.out.println("Sonuç bulunamadı.");
        } else {
            results.forEach(System.out::println);
        }
    }

    private void removeBook() {
        if (!authService.isLoggedIn()) {
            System.out.println("Bu işlem için kütüphaneci girişi gereklidir!");
            return;
        }

        System.out.print("Silinecek kitabın ISBN'i: ");
        String isbn = scanner.nextLine();

        boolean removed = manager.removeBook(isbn);
        if (removed) {
            System.out.println("Kitap silindi.");
        } else {
            System.out.println("Kitap bulunamadı (silinemedi).");
        }
    }

    private void loginLibrarian() {
        System.out.print("Kullanıcı adı: ");
        String username = scanner.nextLine();

        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        if (authService.login(username, password)) {
            System.out.println("Giriş başarılı! Hoş geldiniz, " + authService.getCurrentLibrarianName());
        } else {
            System.out.println("Giriş başarısız! Kullanıcı adı veya şifre hatalı.");
        }
    }

    private void logoutLibrarian() {
        if (authService.isLoggedIn()) {
            authService.logout();
            System.out.println("Çıkış yapıldı.");
        } else {
            System.out.println("Zaten giriş yapılmamış.");
        }
    }

    private int readInt() {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Geçersiz sayı, tekrar girin: ");
            }
        }
    }

}
