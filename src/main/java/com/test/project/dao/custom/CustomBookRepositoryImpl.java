package com.test.project.dao.custom;

import com.test.project.model.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public class CustomBookRepositoryImpl implements CustomBookRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public String getTheMostPopularBookBetween(LocalDate f, LocalDate l) {
        String sql = "select b.book_title\n" +
                "   from orders o join books b on o.book_id = b.id" +
                "   where o.order_date between :start and :end" +
                "   group by b.book_title" +
                "   order by count (b.book_title) DESC";

        Query query = entityManager.createNativeQuery(sql);
        query.setMaxResults(1);
        query.setFirstResult(0);
        query.setParameter("start", f);
        query.setParameter("end", l);

        return (String) query.getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public String getTheMostUnpopularBookBetween(LocalDate f, LocalDate l) {
        String sql = "select b.book_title\n" +
                "   from orders o join books b on o.book_id = b.id" +
                "   where o.order_date between :start and :end" +
                "   group by b.book_title" +
                "   order by count (b.book_title) ASC";
        Query query = entityManager.createNativeQuery(sql);
        query.setMaxResults(1);
        query.setFirstResult(0);
        query.setParameter("start", f);
        query.setParameter("end", l);

        return (String) query.getSingleResult();
    }

    @Override
    public void merge(Book book) {
        entityManager.merge(book);
    }

    @Override
    public List<String> findAllTitlesAvailable() {
        String jpql = "select distinct b.title from Book b where b.active=false";
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);

        return query.getResultList();
    }

    @Override
    public Book findAvailableBookByTitle(String string) {
        String jpql = "select b from Book b where b.active=false";
        TypedQuery<Book> query = entityManager.createQuery(jpql, Book.class);
        query.setMaxResults(1);
        query.setFirstResult(0);

        return query.getSingleResult();
    }

    @Override
    public void deleteAmountBooksWithTitle(String title, Long amount) {
        String jpql = "select b.id from Book b where b.title = :title and b.active = false";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("title", title);
        List<Long> ids = query.getResultList();

        Query deleteOrderQuery = entityManager.createQuery("delete from Order where book.id = :id");
        Query deleteBookQuery = entityManager.createQuery("delete from Book where id = :id");

        for (int i = 0; i < amount; i++) {
            deleteOrderQuery.setParameter("id", ids.get(i));
            deleteOrderQuery.executeUpdate();
            deleteBookQuery.setParameter("id", ids.get(i));
            deleteBookQuery.executeUpdate();
        }
    }
}

