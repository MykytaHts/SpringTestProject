package com.test.project.dao.custom;

import com.test.project.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAllDebtors() {
        String sql = "SELECT DISTINCT u.id, u.first_name, u.last_name, u.email, u.password, u.role, " +
                "u.date_of_birth, u.date_of_registration " +
                "FROM orders o JOIN users u ON u.id = o.user_id " +
                "WHERE o.return_date > o.required_date OR " +
                "(o.return_date IS NULL AND current_date > o.required_date)";
        Query query = entityManager.createNativeQuery(sql, User.class);

        return (List<User>) query.getResultList();
    }
}
