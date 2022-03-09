package com.test.project.dao;

import com.test.project.dao.custom.CustomBookRepository;
import com.test.project.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
public interface BookRepository extends JpaRepository<Book, Long>, CustomBookRepository {

/*    List<Book> findAllByTitle(String title);
    List<Book> findAllByMainAuthorAndCoAuthors(Author mainAuthor, Iterable<Author> coAuthors);*/

    @Modifying
    @Query("update Book b set b.title = :final where b.title = :default")
    void updateBooksTitle(@Param("default") String def, @Param("final") String fin);

    @Query("select distinct b.title from Book b")
    List<String> findAllDistinctTitle();

    @Query("select count(title) from Book where title = :title and active = false")
    Long amountWithTitle(@Param("title") String title);

    @Query(value = "SELECT AVG(col) " +
            " FROM (SELECT COUNT(*) AS col" +
            " FROM users u JOIN orders o ON o.user_id = u.id" +
            " WHERE o.order_date BETWEEN :startDate AND :endDate" +
            " GROUP BY u.id) AS tabl",
            nativeQuery = true)
    Double averageBookOrders(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT CAST(AVG(CURRENT_DATE - date_of_registration) AS INTEGER)  diff" +
            "   FROM users",
            nativeQuery = true)
    Integer averageDays();

    @Query(value = "SELECT CAST(AVG(CURRENT_DATE - date_of_birth) AS INTEGER)  diff" +
            "   FROM users",
            nativeQuery = true)
    Integer averageAgeDays();
}
