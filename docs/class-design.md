# Library Management System - Class Design

Bu doküman, sistemde kullanılacak sınıfların özelliklerini, metotlarını ve ilişkilerini açıklar.

---

## 1. Book Sınıfı

### Amaç

Kütüphanedeki bir kitabı temsil eder.

### Alanlar

- `private String isbn`
- `private String title`
- `private String author`
- `private boolean available` (true = müsait)

### Metotlar

- Constructor: `Book(String isbn, String title, String author)`
- `getIsbn()`
- `getTitle()`
- `getAuthor()`
- `isAvailable()`
- `setAvailable(boolean available)`
- `toString()`

### Notlar

Encapsulation: tüm alanlar private + getter/setter.

---

## 2. Member (abstract) Sınıfı

### Amaç

Üyelerin ortak özelliklerini içerir. Polimorfizmin temelini oluşturur.

### Alanlar

- `private int id`
- `private String name`
- `private int maxBooks`

### Metotlar

- Constructor: `Member(int id, String name, int maxBooks)`
- Getter/Setter metotları
- `abstract double calculateFee(int overdueDays)`

### Notlar

- Polimorfizm: alt sınıflar `calculateFee` metodunu override eder.
- Encapsulation: tüm alanlar private.

---

## 3. StudentMember Sınıfı (Member’dan kalıtım alır)

### Amaç

Öğrenci türü üyeyi temsil eder. Gecikme ücretleri azaltılmış olabilir.

### Alanlar

Member’dan miras alır. Yeni alan yoktur.

### Metotlar

- Constructor: `StudentMember(int id, String name)`
  - `maxBooks = 5`
- `calculateFee(int overdueDays)` → override
  - Örn: `overdueDays * 0.5`

### Notlar

- Inheritance (Member → StudentMember)
- Polymorphism (override edilen metod)

---

## 4. Loan Sınıfı

### Amaç

Bir kitabın hangi üyeye, hangi tarihte ödünç verildiğini takip eder.

### Alanlar

- `private Book book`
- `private Member member`
- `private LocalDate loanDate`
- `private LocalDate returnDate` (null olabilir)

### Metotlar

- Constructor: `Loan(Book book, Member member, LocalDate loanDate)` veya `Loan(Book book, Member member)`
- Getter metotları
- `returnBook()` - kitabı iade eder
- `calculateOverdueDays()` - gecikme günlerini hesaplar
- `calculateLateFee()` - gecikme ücretini hesaplar

### Notlar

- Composition: Loan → Book, Member
- overdue hesaplaması burada yapılır.

---

## 5. Librarian Sınıfı

### Amaç

Kütüphaneci bilgilerini temsil eder. Giriş sistemi için kullanılır.

### Alanlar

- `private String username`
- `private String password`
- `private String name`

### Metotlar

- Constructor: `Librarian(String username, String password, String name)`
- Getter/Setter metotları
- `boolean verifyPassword(String inputPassword)`

### Notlar

- Encapsulation: tüm alanlar private.

---

## 6. Searchable Arayüzü

### Amaç

Kütüphane içinde arama fonksiyonlarını tanımlar.

### Metotlar

- `List<Book> searchByTitle(String title)`
- `List<Book> searchByAuthor(String author)`

---

## 7. LibraryManager Sınıfı

### Amaç

Sistemin işletme mantığını (business logic) içerir.

### Alanlar

- `private List<Book> books`
- `private List<Member> members`
- `private List<Loan> loans`
- `private List<Librarian> librarians`

### Metotlar

#### Kitap Yönetimi

- `void addBook(Book book)`
- `boolean removeBook(String isbn)`
- `Book findBookByIsbn(String isbn)`

#### Üye Yönetimi

- `void addMember(Member member)`
- `Member findMemberById(int id)`

#### Kütüphaneci Yönetimi

- `void addLibrarian(Librarian librarian)`
- `Librarian findLibrarianByUsername(String username)`

#### Arama (Searchable interface)

- `List<Book> searchByTitle(String title)`
- `List<Book> searchByAuthor(String author)`

#### Ödünç Alma / İade

- `Loan borrowBook(int memberId, String isbn)`
- `double returnBook(int memberId, String isbn)`
- `List<Loan> getActiveLoans()`
- `List<Loan> getOverdueLoans()`

#### Dosya İşlemleri

- `void saveToFiles()`
- `void loadFromFiles()`

### Notlar

- Composition: LibraryManager → Book, Member, Loan koleksiyonları
- Interface Implementation: implements Searchable

---

## 8. AuthenticationService Sınıfı

### Amaç

Kütüphaneci giriş/çıkış işlemlerini yönetir.

### Alanlar

- `private Librarian currentLibrarian`
- `private LibraryManager libraryManager`

### Metotlar

- Constructor: `AuthenticationService(LibraryManager libraryManager)`
- `boolean login(String username, String password)`
- `void logout()`
- `boolean isLoggedIn()`
- `Librarian getCurrentLibrarian()`
- `String getCurrentLibrarianName()`

---

## 9. FileStorage Sınıfı

### Amaç

CSV tabanlı dosya okuma/yazma işlemlerini yönetir.

### Metotlar

- `void saveBooks(List<Book> books)`
- `List<Book> loadBooks()`
- `void saveMembers(List<Member> members)`
- `List<Member> loadMembers()`
- `void saveLoans(List<Loan> loans)`
- `List<Loan> loadLoans(List<Book> books, List<Member> members)`
- `void saveLibrarians(List<Librarian> librarians)`
- `List<Librarian> loadLibrarians()`

---

## 10. İlişki Özeti

- `Member` → abstract base class
- `StudentMember extends Member`
- `Loan has Book`
- `Loan has Member`
- `LibraryManager has * Book`
- `LibraryManager has * Member`
- `LibraryManager has * Loan`
- `LibraryManager has * Librarian`
- `LibraryManager implements Searchable`
- `AuthenticationService uses LibraryManager`
- `FileStorage manages persistence for Book, Member, Loan, Librarian`

---

Bu sınıf tasarımı, OOP gereksinimlerini tamamen karşılar:

- Encapsulation
- Inheritance
- Polymorphism
- Interface
- Composition
