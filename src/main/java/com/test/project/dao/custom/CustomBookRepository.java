package com.test.project.dao.custom;

import com.test.project.model.Book;

import java.time.LocalDate;
import java.util.List;

public interface CustomBookRepository {

    String getTheMostPopularBookBetween(LocalDate f, LocalDate l);
    String getTheMostUnpopularBookBetween(LocalDate f, LocalDate l);
    Book findAvailableBookByTitle(String string);
    List<String> findAllTitlesAvailable();
    void deleteAmountBooksWithTitle(String title, Long amount);
    void merge(Book book);
}
