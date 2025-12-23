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
- `abstract double calculateFee(Loan loan)`

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
- `calculateFee(Loan loan)` → override
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
- `private LocalDate dueDate`
- `private LocalDate returnedDate` (null olabilir)

### Metotlar
- Constructor: `Loan(Book book, Member member, LocalDate loanDate, LocalDate dueDate)`
- Getter metotları
- `isOverdue()`
- `getOverdueDays()`

### Notlar
- Composition: Loan → Book, Member
- overdue hesaplaması burada yapılır.

---

## 5. Searchable Arayüzü

### Amaç
Kütüphane içinde arama fonksiyonlarını tanımlar.

### Metotlar
- `List<Book> searchByTitle(String title)`
- `List<Book> searchByAuthor(String author)`

---

## 6. LibraryManager Sınıfı

### Amaç
Sistemin işletme mantığını (business logic) içerir.

### Alanlar
- `private List<Book> books`
- `private List<Member> members`
- `private List<Loan> loans`

### Metotlar
#### Kitap Yönetimi
- `void addBook(Book book)`
- `boolean removeBook(String isbn)`
- `Book findBookByIsbn(String isbn)`

#### Üye Yönetimi
- `void addMember(Member member)`
- `Member findMemberById(int id)`

#### Arama (Searchable interface)
- `List<Book> searchByTitle(String title)`
- `List<Book> searchByAuthor(String author)`

#### Ödünç Alma / İade
- `Loan borrowBook(int memberId, String isbn)`
- `double returnBook(int memberId, String isbn)`

### Notlar
- Composition: LibraryManager → Book, Member, Loan koleksiyonları
- Interface Implementation: implements Searchable

---

## 7. İlişki Özeti

- `Member` → abstract base class
- `StudentMember extends Member`
- `Loan has Book`
- `Loan has Member`
- `LibraryManager has * Book`
- `LibraryManager has * Member`
- `LibraryManager has * Loan`
- `LibraryManager implements Searchable`

---

Bu sınıf tasarımı, OOP gereksinimlerini tamamen karşılar:
- Encapsulation
- Inheritance
- Polymorphism
- Interface
- Composition

