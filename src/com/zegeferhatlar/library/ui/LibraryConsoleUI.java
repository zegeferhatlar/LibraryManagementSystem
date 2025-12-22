package com.zegeferhatlar.library.ui;

import com.zegeferhatlar.library.service.LibraryManager;
import com.zegeferhatlar.library.model.Book;
import com.zegeferhatlar.library.model.StudentMember;
import java.util.Scanner;

public class LibraryConsoleUI {

    private LibraryManager manager;
    private Scanner scanner;

    public LibraryConsoleUI() {
        this.manager = new LibraryManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== Kütüphane Yönetim Sistemi ===");

        while (true) {
            System.out.println("\n--- Menü ---");
            System.out.println("1) Kitap Ekle");
            System.out.println("2) Üye Ekle");
            System.out.println("3) Kitap Ara (Title)");
            System.out.println("4) Kitap Ödünç Al");
            System.out.println("5) Kitap İade Et");
            System.out.println("6) Kitapları Listele");
            System.out.println("7) Üyeleri Listele");
            System.out.println("8) Aktif Loans Listele");
            System.out.println("9) Overdue Loans Listele");
            System.out.println("0) Çıkış");
            System.out.print("Seçenek: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // buffer temizleme

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
                case 0 -> {
                    System.out.println("Programdan çıkılıyor...");
                    return;
                }
                default -> System.out.println("Geçersiz seçim!");
            }

        }
    }

    private void addBook() {
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
        System.out.print("Üye ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

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
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        manager.borrowBook(id, isbn);
    }

    private void returnBook() {
        System.out.print("Üye ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

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

}
