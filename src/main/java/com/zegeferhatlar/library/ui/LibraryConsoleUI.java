package com.zegeferhatlar.library.ui;

import com.zegeferhatlar.library.service.AuthenticationService;
import com.zegeferhatlar.library.service.LibraryManager;
import com.zegeferhatlar.library.model.Book;
import com.zegeferhatlar.library.model.Loan;
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

            int menuNumber = 1;

            // Sadece kütüphaneci giriş yaptığında görünen seçenekler
            if (authService.isLoggedIn()) {
                System.out.println(menuNumber++ + ") Kitap Ekle");
                System.out.println(menuNumber++ + ") Üye Ekle");
            }

            // Her zaman görünen seçenekler
            System.out.println(menuNumber++ + ") Kitap Ara");
            System.out.println(menuNumber++ + ") Kitap Ödünç Al");
            System.out.println(menuNumber++ + ") Kitap İade Et");
            System.out.println(menuNumber++ + ") Kitapları Listele");

            // Sadece kütüphaneci giriş yaptığında görünen seçenekler
            if (authService.isLoggedIn()) {
                System.out.println(menuNumber++ + ") Üyeleri Listele");
                System.out.println(menuNumber++ + ") Aktif Ödünçler Listele");
                System.out.println(menuNumber++ + ") Gecikmiş Ödünçler Listele");
            }

            // Sadece kütüphaneci giriş yaptığında görünen seçenekler
            if (authService.isLoggedIn()) {
                System.out.println(menuNumber++ + ") Kitap Sil");
                System.out.println(menuNumber++ + ") Üye Sil");
                System.out.println(menuNumber++ + ") Kütüphaneci Çıkışı");
            } else {
                // Sadece giriş yapılmadığında görünen seçenekler
                System.out.println(menuNumber++ + ") Kütüphaneci Girişi");
            }

            System.out.println("0) Çıkış");
            System.out.print("Seçenek: ");

            int choice = readInt();

            if (choice == 0) {
                System.out.println("Veriler kaydediliyor...");
                manager.saveToFiles();
                System.out.println("Programdan çıkılıyor...");
                return;
            }

            boolean isLoggedIn = authService.isLoggedIn();

            // Dinamik menü seçeneklerini işle
            if (isLoggedIn) {
                // Giriş yapıldığında: 1-12 arası seçenekler
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
                    case 10 -> removeBook();
                    case 11 -> removeMember();
                    case 12 -> logoutLibrarian();
                    default -> System.out.println("Geçersiz seçim!");
                }
            } else {
                // Giriş yapılmadığında: 1-5 arası seçenekler
                switch (choice) {
                    case 1 -> searchBook();
                    case 2 -> borrowBook();
                    case 3 -> returnBook();
                    case 4 -> listBooks();
                    case 5 -> loginLibrarian();
                    default -> System.out.println("Geçersiz seçim!");
                }
            }

        }
    }

    private void addBook() {
        if (!authService.isLoggedIn()) {
            System.out.println("Bu işlem için kütüphaneci girişi gereklidir!");
            return;
        }

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();

        // ISBN kontrolü: Bu ISBN zaten kullanılıyor mu?
        if (manager.findBookByIsbn(isbn) != null) {
            System.out.println(
                    "Hata: Bu ISBN (" + isbn + ") zaten kullanılıyor! Her kitabın benzersiz bir ISBN'i olmalıdır.");
            return;
        }

        System.out.print("Başlık: ");
        String title = scanner.nextLine();

        System.out.print("Yazar: ");
        String author = scanner.nextLine();

        Book book = new Book(isbn, title, author);
        boolean added = manager.addBook(book);

        if (added) {
            System.out.println("Kitap eklendi.");
            manager.saveToFiles(); // Otomatik kaydet
        } else {
            System.out.println("Hata: Kitap eklenemedi. Bu ISBN zaten kullanılıyor.");
        }
    }

    private void addMember() {
        if (!authService.isLoggedIn()) {
            System.out.println("Bu işlem için kütüphaneci girişi gereklidir!");
            return;
        }

        System.out.print("Üye ID: ");
        int id = readInt();

        // ID kontrolü: Bu ID zaten kullanılıyor mu?
        if (manager.findMemberById(id) != null) {
            System.out
                    .println("Hata: Bu ID (" + id + ") zaten kullanılıyor! Her üyenin benzersiz bir ID'si olmalıdır.");
            return;
        }

        System.out.print("Üye adı: ");
        String name = scanner.nextLine();

        StudentMember member = new StudentMember(id, name);
        boolean added = manager.addMember(member);

        if (added) {
            System.out.println("Öğrenci üye eklendi.");
            manager.saveToFiles(); // Otomatik kaydet
        } else {
            System.out.println("Hata: Üye eklenemedi. Bu ID zaten kullanılıyor.");
        }
    }

    private void searchBook() {
        System.out.print("Aranacak başlık: ");
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

        Loan loan = manager.borrowBook(id, isbn);
        if (loan != null) {
            manager.saveToFiles(); // Otomatik kaydet
        }
    }

    private void returnBook() {
        System.out.print("Üye ID: ");
        int id = readInt();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        double fee = manager.returnBook(id, isbn);
        if (fee >= 0) {
            System.out.println("Gecikme Ücreti: " + fee);
            manager.saveToFiles(); // Otomatik kaydet
        }
    }

    private void listBooks() {
        System.out.println("\n--- Kitap Listesi ---");
        if (manager.getBooks().isEmpty()) {
            System.out.println("Kayıtlı kitap yok.");
            return;
        }
        manager.getBooks().forEach(System.out::println);
    }

    private void listMembers() {
        System.out.println("\n--- Üye Listesi ---");
        if (manager.getMembers().isEmpty()) {
            System.out.println("Kayıtlı üye yok.");
            return;
        }
        manager.getMembers().forEach(m -> System.out.println("ID: " + m.getId() + " | İsim: " + m.getName()));
    }

    private void listActiveLoans() {
        System.out.println("\n--- Aktif Ödünçler ---");
        var activeLoans = manager.getActiveLoans();

        if (activeLoans.isEmpty()) {
            System.out.println("Aktif ödünç yok.");
            return;
        }

        activeLoans.forEach(l -> {
            System.out.println("Üye: " + l.getMember().getName()
                    + " | Kitap: " + l.getBook().getTitle()
                    + " | Ödünç Tarihi: " + l.getLoanDate());
        });
    }

    private void listOverdueLoans() {
        System.out.println("\n--- Gecikmiş Ödünçler ---");
        var overdueLoans = manager.getOverdueLoans();

        if (overdueLoans.isEmpty()) {
            System.out.println("Gecikmiş ödünç yok.");
            return;
        }

        overdueLoans.forEach(l -> {
            int overdueDays = l.calculateOverdueDays();
            double fee = l.calculateLateFee();
            System.out.println("Üye: " + l.getMember().getName()
                    + " | Kitap: " + l.getBook().getTitle()
                    + " | Gecikme Günü: " + overdueDays
                    + " | Ücret: " + fee);
        });
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
            manager.saveToFiles(); // Otomatik kaydet
        } else {
            System.out.println("Kitap bulunamadı (silinemedi).");
        }
    }

    private void removeMember() {
        if (!authService.isLoggedIn()) {
            System.out.println("Bu işlem için kütüphaneci girişi gereklidir!");
            return;
        }

        System.out.print("Silinecek üyenin ID'si: ");
        int memberId = readInt();

        boolean removed = manager.removeMember(memberId);
        if (removed) {
            System.out.println("Üye silindi.");
            manager.saveToFiles(); // Otomatik kaydet
        } else {
            System.out.println("Üye bulunamadı (silinemedi).");
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
