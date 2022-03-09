package com.test.project.dao.custom;

import com.test.project.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomUserRepository {

    List<User> findAllDebtors();
}
