package com.test.project.service;

import com.test.project.dao.AuthorRepository;
import com.test.project.model.Author;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public void save(Author author) {
        authorRepository.save(author);
    }

    @Transactional(readOnly = true)
    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
