package com.zegeferhatlar.library.service;

import com.zegeferhatlar.library.model.Book;
import java.util.List;

public interface Searchable {

    List<Book> searchByTitle(String title);

    List<Book> searchByAuthor(String author);
}
