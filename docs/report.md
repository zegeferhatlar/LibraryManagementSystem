# Library Management System - Proje Raporu

## 1. Amaç

Bu proje, Java ile nesne tabanlı prensipleri uygulayarak kitap, üye ve ödünç işlemlerini yöneten basit bir kütüphane yönetim sistemi geliştirmeyi amaçlar. Sistem, OOP prensiplerini (Encapsulation, Inheritance, Polymorphism, Interface) kullanarak modüler ve genişletilebilir bir yapı sunar.

## 2. Kullanılan Teknolojiler

- **Java**: Nesne tabanlı programlama dili
- **JUnit 5**: Unit test framework
- **Git & GitHub**: Versiyon kontrolü ve işbirliği
- **GitHub Projects**: Kanban board ile görev takibi
- **CSV**: Dosya tabanlı veri kalıcılığı (harici veritabanı kullanılmadan)

## 3. Sistem Tasarımı

### 3.1 Paket Yapısı

Proje, katmanlı mimari prensiplerine uygun olarak organize edilmiştir:

- **`model`**: Domain sınıfları (Book, Member, StudentMember, Loan, Librarian)
- **`service`**: İş mantığı ve servisler (LibraryManager, FileStorage, AuthenticationService, Searchable)
- **`ui`**: Kullanıcı arayüzü (LibraryConsoleUI)

### 3.2 UML Diyagramları

- **Class Diagram**: `docs/class.png` - Sistemdeki tüm sınıflar, ilişkiler ve metodlar
- **Use-Case Diagram**: `docs/usecase.png` - Sistem kullanım senaryoları ve aktörler

Detaylı açıklamalar:

- Class Design: `docs/class-design.md`
- Use-Case Analizi: `docs/usecases.md`

## 4. OOP Prensipleri

### 4.1 Encapsulation (Kapsülleme)

Tüm sınıflarda private alanlar kullanılmış ve erişim getter/setter metodları ile kontrol edilmiştir:

- `Book`: isbn, title, author, available (private)
- `Member`: id, name, maxBooks (private)
- `Loan`: book, member, loanDate, returnDate (private)

### 4.2 Inheritance (Kalıtım)

- `StudentMember extends Member`: Üye türlerinin ortak özelliklerini paylaşır
- `Member` abstract sınıf olarak tasarlanmış, alt sınıfların implement etmesi gereken `calculateFee()` metodunu tanımlar

### 4.3 Polymorphism (Çok Biçimlilik)

- `Member.calculateFee()` abstract metod olarak tanımlanmış
- `StudentMember.calculateFee()` override edilerek öğrenciler için özel ücret hesaplama mantığı uygulanmış
- Runtime'da doğru metod çağrılır (polymorphic behavior)

### 4.4 Interface (Arayüz)

- `Searchable` interface: Kitap arama davranışını soyutlar
- `LibraryManager implements Searchable`: Arama işlevselliğini sağlar
- Interface sayesinde farklı arama stratejileri kolayca değiştirilebilir

### 4.5 Composition (Bileşim)

- `Loan` sınıfı `Book` ve `Member` nesnelerini içerir (has-a ilişkisi)
- `LibraryManager` koleksiyonlar halinde `Book`, `Member`, `Loan` nesnelerini yönetir

## 5. Persistence (Dosya Kayıt/Yükleme)

Sistem CSV tabanlı dosya saklama yaklaşımını kullanır:

- `data/books.csv`: Kitap bilgileri (ISBN, başlık, yazar, müsaitlik durumu)
- `data/members.csv`: Üye bilgileri (tip, ID, isim, max kitap sayısı)
- `data/loans.csv`: Ödünç kayıtları (ISBN, üye ID, ödünç tarihi, iade tarihi)
- `data/librarians.csv`: Kütüphaneci bilgileri (kullanıcı adı, şifre, isim)

**Çalışma Mantığı:**

- Uygulama açılışta `loadFromFiles()` ile verileri yükler
- Kullanıcı çıkış yaptığında `saveToFiles()` ile verileri kaydeder
- `FileStorage` sınıfı CSV okuma/yazma işlemlerini yönetir

## 6. Unit Test Stratejisi

Business logic odaklı testler yazılmıştır:

### 6.1 Test Sınıfları

- **BookTest**: Kitap oluşturma ve müsaitlik durumu testleri
- **StudentMemberTest**: Üye oluşturma ve ücret hesaplama testleri
- **LoanTest**: Ödünç işlemleri ve gecikme hesaplama testleri
- **LibraryManagerTest**: Kitap ekleme, arama, ödünç alma/iade testleri
- **AuthenticationServiceTest**: Kütüphaneci giriş sistemi testleri
- **LibrarianTest**: Kütüphaneci model testleri

### 6.2 Test Kapsamı

- Temel CRUD işlemleri (ekleme, silme, arama)
- Ödünç alma/iade akışları
- Gecikme ücreti hesaplamaları
- Polimorfizm davranışları
- Sınır durumları (null kontrolü, limit aşımı)

**Test Sonuçları**: 32 test başarıyla geçmiştir (0 hata, 0 başarısız)

## 7. Git & GitHub

### 7.1 Commit Geçmişi

- **Toplam Commit Sayısı**: 32 commit
- **Gereksinim**: En az 20 commit ✅
- **Süre**: 3 haftaya yayılmış geliştirme süreci
- **Commit Mesajları**: Anlamlı ve açıklayıcı (feat, fix, test, docs, refactor prefix'leri kullanılmış)

### 7.2 Commit Örnekleri

- `feat: Book sınıfını encapsulation, constructor, Javadocs ile uygula`
- `feat: Member abstract class ve StudentMember class eklendi`
- `test: LibraryManager için basic JUnit test class eklendi`
- `docs: proje raporu eklendi`
- `feat: Kütüphaneci giriş sistemi için AuthenticationService eklendi`

### 7.3 GitHub Projects (Kanban Board)

- Görevler "To Do", "In Progress", "Done" kolonlarında takip edilmiştir
- Her özellik için ayrı kart oluşturulmuş ve commit'ler ile ilişkilendirilmiştir

## 8. Kod Kalitesi

### 8.1 Javadoc

- Tüm public sınıflar, metodlar ve önemli alanlar için Javadoc yorumları eklenmiştir
- 54+ Javadoc yorumu mevcuttur
- Parametreler, dönüş değerleri ve özel durumlar dokümante edilmiştir

### 8.2 Okunabilirlik

- Anlamlı sınıf ve metod isimleri
- Tutarlı kod formatı
- Açıklayıcı değişken isimleri
- Modüler yapı (her sınıf tek bir sorumluluğa sahip)

### 8.3 Maintainability (Bakım Yapılabilirlik)

- Katmanlı mimari (model, service, ui)
- Interface kullanımı ile bağımlılıklar azaltılmış
- Abstract sınıflar ile genişletilebilirlik sağlanmış
- Testler ile refactoring güvenliği artırılmış

## 9. Özellikler

### 9.1 Minimum Özellikler

- ✅ Kitap ekleme/çıkarma
- ✅ Kitap arama (başlık ve yazar)
- ✅ Kitap ödünç alma/iade
- ✅ Müsaitlik durumu gösterme

### 9.2 Opsiyonel Özellikler

- ✅ Dosya kaydı (CSV tabanlı persistence)
- ✅ Gecikme ücreti hesaplama (polymorphic)
- ✅ Kütüphaneci giriş sistemi (AuthenticationService)

## 10. Sonuç

Bu proje, nesne tabanlı programlama prensiplerini (Encapsulation, Inheritance, Polymorphism, Interface) başarıyla uygulayan, UML diyagramları ile tasarlanmış, kapsamlı unit testler ile doğrulanmış ve Git/GitHub ile versiyon kontrolü yapılmış profesyonel bir kütüphane yönetim sistemidir.

Sistem, modüler yapısı sayesinde kolayca genişletilebilir (örneğin yeni üye türleri, farklı arama stratejileri eklenebilir) ve bakımı kolaydır. Tüm kod Javadoc ile dokümante edilmiş ve okunabilirlik ön planda tutulmuştur.

**Proje Durumu**: ✅ Tüm gereksinimler karşılanmıştır.
