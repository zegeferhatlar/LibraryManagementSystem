package com.zegeferhatlar.library.service;

import com.zegeferhatlar.library.model.Book;
import java.util.List;

/**
 * Arama yeteneği sağlayan arayüz.
 */
public interface Searchable {

    /**
     * Başlığa göre kitap arar.
     * @param title aranan başlık (tam/partial)
     * @return bulunan kitap listesi
     */
    List<Book> searchByTitle(String title);

    /**
     * Yazara göre kitap arar.
     * @param author aranan yazar
     * @return bulunan kitap listesi
     */
    List<Book> searchByAuthor(String author);
}

