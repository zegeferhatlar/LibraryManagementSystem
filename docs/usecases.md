# Library Management System - Use-Case Analizi

## 1. Aktörler

1. **Üye (Member)**
2. **Öğrenci Üye (StudentMember)** – Member'ın özel bir türü
3. **Kütüphaneci (Librarian)**

## 2. Use-Case Listesi (Özet)

1. Kitap Ara
2. Kitap Ödünç Al
3. Kitap İade Et
4. Kitap Müsaitlik Durumunu Görüntüle
5. Kitap Ekle (Kütüphaneci)
6. Kitap Sil (Kütüphaneci)
7. Üye Ekle (Kütüphaneci)
8. (Opsiyonel) Gecikme Ücretini Hesapla
9. (Opsiyonel) Kütüphaneci Girişi
10. (Opsiyonel) Kütüphaneci Çıkışı
11. (Opsiyonel) Kitapları Listele
12. (Opsiyonel) Üyeleri Listele
13. (Opsiyonel) Aktif Loans Listele
14. (Opsiyonel) Overdue Loans Listele

## 3. Use-Case Açıklamaları

### 3.1. Kitap Ara

- **Amaç:** Kullanıcıların sistemdeki kitaplar arasında başlık veya yazara göre arama yapabilmesi.
- **Ana Aktör:** Üye (Member) / Öğrenci Üye (StudentMember)
- **Önyargılar (Preconditions):**
  - Sistem çalışır durumda olmalıdır.
  - Kütüphanede en az bir kitap kayıtlı olmalıdır.
- **Ana Senaryo:**
  1. Kullanıcı “Kitap Ara” ekranına gelir veya menüden "Kitap Ara" seçeneğini seçer.
  2. Kullanıcı aramak istediği kitap başlığını veya yazar adını girer.
  3. Sistem, girilen kritere uygun kitapları listeler.
  4. Kullanıcı sonuç listesini görüntüler.
- **Sonuç (Postcondition):**
  - Kullanıcı arama sonuçlarını görmüştür; istediği kitabın var olup olmadığını öğrenir.

### 3.2. Kitap Ödünç Al

- **Amaç:** Bir üyenin kütüphanedeki uygun/boşta olan bir kitabı ödünç alabilmesi.
- **Ana Aktör:** Üye (Member) / Öğrenci Üye (StudentMember)
- **Önyargılar:**
  - Kullanıcı sisteme üye olarak kayıtlı olmalıdır.
  - Kitap sistemde kayıtlı ve `müsait` durumda olmalıdır.
  - Üyenin mevcut ödünç kitap sayısı, maksimum limitini (maxBooks) aşmamış olmalıdır.
- **Ana Senaryo:**
  1. Üye, ödünç almak istediği kitabın detaylarına/sonuç listesine gelir.
  2. Üye, “Ödünç Al” komutunu verir.
  3. Sistem, üyenin limitini ve kitabın müsaitlik durumunu kontrol eder.
  4. Şartlar sağlanıyorsa sistem yeni bir ödünç kaydı (Loan) oluşturur.
  5. Kitabın durumu **“müsait değil”** olarak güncellenir.
- **Sonuç:**
  - Kitap ilgili üyeye ödünç verilmiştir; Loan kaydı oluşturulmuştur.

### 3.3. Kitap İade Et

- **Amaç:** Üyenin daha önce ödünç aldığı kitabı sisteme iade etmesi.
- **Ana Aktör:** Üye (Member) / Öğrenci Üye (StudentMember)
- **Önyargılar:**
  - Üyenin üzerinde iade etmek istediği kitabın aktif bir Loan kaydı olmalıdır.
- **Ana Senaryo:**
  1. Üye “Kitap İade Et” seçeneğini seçer.
  2. Üye, iade edeceği kitabı (ISBN veya listeden) belirtir.
  3. Sistem, ilgili Loan kaydını bulur ve iade tarihini günceller.
  4. Sistem, kitabın durumunu tekrar **“müsait”** olarak günceller.
  5. Sistem, gecikme varsa `calculateFee()` üzerinden gecikme ücretini hesaplar.
  6. Gecikme ücreti kullanıcıya gösterilir.
- **Sonuç:**
  - Kitap iade edilmiştir, Loan kaydı güncellenmiştir, gecikme ücreti hesaplanmıştır (varsa).

### 3.4. Kitap Müsaitlik Durumunu Görüntüle

- **Amaç:** Bir kitabın şu anda ödünçte mi yoksa müsait mi olduğunu göstermek.
- **Ana Aktör:** Üye / Kütüphaneci
- **Ana Senaryo:**
  1. Kullanıcı bir kitabın detay ekranına gelir veya arama sonucunda kitabı seçer.
  2. Sistem, kitabın `available` alanına bakarak “Müsait” veya “Ödünçte” bilgisini gösterir.

### 3.5. Kitap Ekle (Kütüphaneci)

- **Amaç:** Kütüphaneci’nin sisteme yeni bir kitap kaydı ekleyebilmesi.
- **Ana Aktör:** Kütüphaneci (Librarian)
- **Ana Senaryo:**
  1. Kütüphaneci “Kitap Ekle” ekranını açar.
  2. ISBN, başlık, yazar bilgilerini girer.
  3. Sistem, yeni kitabı kitap listesine ekler ve `available = true` olarak işaretler.

### 3.6. Kitap Sil (Kütüphaneci)

- **Amaç:** Kütüphaneci'nin, artık kullanılmayan/iptal edilen kitapları sistemden kaldırabilmesi.
- **Önyargılar:**
  - Kütüphaneci giriş yapmış olmalıdır.
  - Kitap şu anda ödünçte olmamalıdır (aktif loan kaydı olmamalı).
- **Ana Senaryo:**
  1. Kütüphaneci, silmek istediği kitabı seçer (ISBN veya listeden).
  2. Sistem, kitabın aktif loan'ı olup olmadığını kontrol eder.
  3. Eğer yoksa, kitabı kayıtlı listeden siler.

### 3.7. Üye Ekle (Kütüphaneci)

- **Amaç:** Kütüphaneci'nin sisteme yeni bir üye kaydı ekleyebilmesi.
- **Ana Aktör:** Kütüphaneci (Librarian)
- **Önyargılar:**
  - Kütüphaneci giriş yapmış olmalıdır.
- **Ana Senaryo:**
  1. Kütüphaneci "Üye Ekle" ekranını açar.
  2. Üye ID ve isim bilgilerini girer.
  3. Sistem, yeni üyeyi (StudentMember) üye listesine ekler.

### 3.8. (Opsiyonel) Gecikme Ücretini Hesapla

- **Amaç:** Gecikmeli iade edilen kitaplar için ücret hesaplamak.
- **Not:** Bu use-case aslında “Kitap İade Et” use-case’inin bir parçası olarak da düşünülebilir.

### 3.9. (Opsiyonel) Kütüphaneci Girişi

- **Amaç:** Sadece kütüphaneci rolüne sahip kullanıcıların kitap ekleme/silme gibi işlemler yapabilmesini sağlamak.
- **Ana Aktör:** Kütüphaneci (Librarian)
- **Ana Senaryo:**
  1. Kullanıcı "Kütüphaneci Girişi" seçeneğini seçer.
  2. Kullanıcı adı ve şifre bilgilerini girer.
  3. Sistem, bilgileri doğrular.
  4. Başarılı olursa, kütüphaneci oturum açmış olur.

### 3.10. (Opsiyonel) Kütüphaneci Çıkışı

- **Amaç:** Kütüphaneci'nin oturumunu kapatması.
- **Ana Aktör:** Kütüphaneci (Librarian)
- **Ana Senaryo:**
  1. Kütüphaneci "Kütüphaneci Çıkışı" seçeneğini seçer.
  2. Sistem, oturumu kapatır.

### 3.11. (Opsiyonel) Kitapları Listele

- **Amaç:** Sistemdeki tüm kitapların listesini görüntülemek.
- **Ana Aktör:** Üye / Kütüphaneci
- **Ana Senaryo:**
  1. Kullanıcı "Kitapları Listele" seçeneğini seçer.
  2. Sistem, tüm kitapları listeler.

### 3.12. (Opsiyonel) Üyeleri Listele

- **Amaç:** Sistemdeki tüm üyelerin listesini görüntülemek.
- **Ana Aktör:** Kütüphaneci
- **Ana Senaryo:**
  1. Kullanıcı "Üyeleri Listele" seçeneğini seçer.
  2. Sistem, tüm üyeleri listeler.

### 3.13. (Opsiyonel) Aktif Loans Listele

- **Amaç:** Henüz iade edilmemiş tüm ödünç kayıtlarını görüntülemek.
- **Ana Aktör:** Kütüphaneci
- **Ana Senaryo:**
  1. Kullanıcı "Aktif Loans Listele" seçeneğini seçer.
  2. Sistem, returnDate'i null olan tüm loan kayıtlarını listeler.

### 3.14. (Opsiyonel) Overdue Loans Listele

- **Amaç:** Gecikmiş (overdue) ödünç kayıtlarını görüntülemek.
- **Ana Aktör:** Kütüphaneci
- **Ana Senaryo:**
  1. Kullanıcı "Overdue Loans Listele" seçeneğini seçer.
  2. Sistem, gecikmiş loan kayıtlarını ve gecikme ücretlerini listeler.
