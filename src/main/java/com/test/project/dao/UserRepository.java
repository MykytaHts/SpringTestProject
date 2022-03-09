package com.test.project.dao;

import com.test.project.dao.custom.CustomUserRepository;
import com.test.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {

    @Query("select u from User u where u.role = 'U'")
    List<User> findAllReaders();

    @Query("select u from User u where u.role = 'M'")
    List<User> findAllManagers();
}
