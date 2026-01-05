package com.zegeferhatlar.library;

import com.zegeferhatlar.library.ui.LibraryConsoleUI;

/**
 * Library Management System - Ana giriş noktası.
 * 
 * <p>
 * Bu sınıf, kütüphane yönetim sisteminin başlatılmasından sorumludur.
 * Uygulama başlatıldığında console tabanlı kullanıcı arayüzünü başlatır
 * ve kullanıcı etkileşimini sağlar.
 * </p>
 * 
 * <p>
 * Sistem özellikleri:
 * <ul>
 * <li>Kitap ve üye yönetimi</li>
 * <li>Ödünç alma ve iade işlemleri</li>
 * <li>Kitap arama (başlık ve yazar)</li>
 * <li>Gecikme ücreti hesaplama</li>
 * <li>Kütüphaneci giriş sistemi</li>
 * <li>Dosya tabanlı veri kalıcılığı</li>
 * </ul>
 * </p>
 * 
 * @author zegeferhatlar
 * @version 1.0.0
 */
public class Main {
    /**
     * Uygulamanın ana giriş noktası.
     * 
     * <p>
     * Console tabanlı kullanıcı arayüzünü başlatır ve kullanıcı
     * çıkış yapana kadar çalışmaya devam eder. Uygulama kapanırken
     * veriler otomatik olarak dosyaya kaydedilir.
     * </p>
     * 
     * @param args komut satırı argümanları (kullanılmıyor)
     */
    public static void main(String[] args) {
        LibraryConsoleUI ui = new LibraryConsoleUI();
        ui.start();
    }
}
