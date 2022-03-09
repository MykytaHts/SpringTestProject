package com.test.project.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;


@Getter
@EqualsAndHashCode(of = {"title"})
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Setter
    @Column(name = "book_title", nullable = false, length = 40)
    private String title;

    @Setter
    private boolean active;

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PACKAGE)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name="book_id"),
            inverseJoinColumns = @JoinColumn(name="author_id")
    )
    private Set<Author> coAuthors = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
                fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "main_author_id")
    private Author mainAuthor;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "book", orphanRemoval = true)
    Set<Order> orders = new HashSet<>();

    public void setMainAuthor(Author author) {
        this.mainAuthor = author;
        author.getBooks().add(this);
    }

    public void addCoauthors(List<Author> list) {
        for (Author author : list) {
            coAuthors.add(author);
            author.getBooks().add(this);
        }
    }
}
