package com.test.project.service;

import com.test.project.dao.AuthorRepository;
import com.test.project.dao.BookRepository;
import com.test.project.model.Author;
import com.test.project.model.Book;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<String> findAllTitlesAvailable() {
        return bookRepository.findAllTitlesAvailable();
    }

    @Transactional(readOnly = true)
    public List<String> findAllDistinctByTitle() {
        return bookRepository.findAllDistinctTitle();
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void save(Book book, Integer amount, Long id, @Nullable List<Long> ids){
        if (ids !=null && ids.contains(id))
            throw new IllegalArgumentException("One person in two states in the same time");

        if (ids != null) {
            List<Author> authors = authorRepository.findAllById(ids);
            book.addCoauthors(authors);
        }

        Author author = authorRepository.findById(id).orElseThrow(NoSuchElementException::new);
        book.setMainAuthor(author);

        for (int i = 0; i < amount; i++) {
            bookRepository.merge(book);
        }
    }

    @Transactional(readOnly = true)
    public Book findById(Long id) {
        if (bookRepository.findById(id).isEmpty()){
            System.out.println("Empty with Id: " + id);
        }

        return bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public Double averageBookOrders(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) return null;

        return bookRepository.averageBookOrders(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public Integer averageAge() {
        return bookRepository.averageAgeDays() / 365;
    }

    @Transactional(readOnly = true)
    public Integer averageDays() {
        return bookRepository.averageDays();
    }

    public void updateBooksTitle(String def, String fin) {
        bookRepository.updateBooksTitle(def, fin);
    }

    @Transactional(readOnly = true)
    public Long amountWithTitle(String title) {
        return bookRepository.amountWithTitle(title);
    }

    public void deleteAmountBooksWithTitle(String title, Long amount) {
        bookRepository.deleteAmountBooksWithTitle(title, amount);
    }

    public String getTheMostPopularBookBetween (LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) return null;

        return bookRepository.getTheMostPopularBookBetween(startDate, endDate);
    }

    public String getTheMostUnpopularBookBetween (LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) return null;

        return bookRepository.getTheMostUnpopularBookBetween(startDate, endDate);
    }
}
