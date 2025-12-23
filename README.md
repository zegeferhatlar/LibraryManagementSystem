# Library Management System

## Açıklama
Bu proje, Java ile geliştirilmiş nesne tabanlı bir kütüphane yönetim sistemidir.
Sistem kitapların, üyelerin ve ödünç alma işlemlerinin yönetimini sağlar.

## Kullanılan Teknolojiler
- Java
- JUnit 5
- Git & GitHub
- CSV tabanlı dosya sistemi

## OOP Prensipleri
- Encapsulation (private fields, getter/setter)
- Inheritance (Member → StudentMember)
- Polymorphism (calculateFee method override)
- Interface (Searchable)

## Proje Yapısı
- model: Temel domain sınıfları (Book, Member, Loan)
- service: İş mantığı ve persistence (LibraryManager, FileStorage)
- ui: Console tabanlı kullanıcı arayüzü

## Çalışma Mantığı
- Uygulama açıldığında veriler otomatik olarak dosyadan yüklenir
- Kullanıcı console menü üzerinden işlemleri yapar
- Program kapanırken veriler otomatik olarak kaydedilir

## UML Diyagramları
- Class Diagram
- Use Case Diagram

## Testler
- BookTest
- StudentMemberTest
- LoanTest
- LibraryManagerTest

## Nasıl Çalıştırılır?
1. Projeyi klonlayın
2. Main.java dosyasını çalıştırın
3. Console menüyü kullanarak işlemleri gerçekleştirin
