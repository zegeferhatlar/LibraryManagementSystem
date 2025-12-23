# Library Management System - Proje Raporu

## 1. Amaç

Bu proje, Java ile nesne tabanlı prensipleri uygulayarak kitap, üye ve ödünç işlemlerini yöneten basit bir kütüphane yönetim sistemi geliştirmeyi amaçlar.

## 2. Kullanılan Teknolojiler

- Java
- JUnit 5 (unit test)
- Git & GitHub
- GitHub Projects (Kanban)
- CSV tabanlı dosya sistemi (persistence)

## 3. Sistem Tasarımı

### 3.1 Paket Yapısı

- `model`: Book, Member, StudentMember, Loan
- `service`: LibraryManager, FileStorage, Searchable
- `ui`: LibraryConsoleUI

### 3.2 UML Diyagramları

- Class diagram: `docs/class-diagram.png`
- Use-case diagram: `docs/usecase_diagram.png`

## 4. OOP Prensipleri

- Encapsulation: Sınıflarda private alanlar ve getter/setter kullanımı
- Inheritance: `StudentMember extends Member`
- Polymorphism: `calculateFee()` metodu override edilmiştir
- Interface: `Searchable` arayüzü ile arama davranışı soyutlanmıştır
- Composition: `Loan` sınıfı `Book` ve `Member` nesnelerini içerir

## 5. Persistence (Dosya Kayıt/Yükleme)

Sistem CSV tabanlı dosya saklama yaklaşımını kullanır:

- `data/books.csv`
- `data/members.csv`
- `data/loans.csv`

Uygulama açılışta verileri dosyadan yükler, kapanışta otomatik kaydeder.

## 6. Unit Test Stratejisi

Business logic odaklı testler:

- BookTest
- StudentMemberTest
- LoanTest
- LibraryManagerTest

Testler, temel akışlar ve ücret/overdue hesaplamalarını doğrular.

## 7. Git & Kanban

- En az 3 haftaya yayılmış commit geçmişi
- GitHub Projects üzerinde Kanban board ile görev takibi

## 8. Sonuç

Proje; OOP prensiplerini, UML tasarımını, unit test yaklaşımını ve Git tabanlı geliştirme sürecini bütünleşik şekilde uygulayan çalışır bir kütüphane yönetim sistemidir.
