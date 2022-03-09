package com.test.project.service;

import com.test.project.dao.UserRepository;
import com.test.project.model.Book;
import com.test.project.model.Order;
import com.test.project.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long save(User user) {
        return userRepository.save(user).getId();
    }

    public List<User> findAll() {
        return Collections.unmodifiableList(userRepository.findAll());
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Collection<Order> findUserOrdersById(Long id) {
        return findById(id).getOrders();
    }

    public List<Book> findUserBooksById(Long id) {
        List<Book> books = new ArrayList<>();
        findUserOrdersById(id).stream()
                .filter(x -> x.getBook() != null && x.isActive())
                .forEach(x -> books.add(x.getBook()));

        return books;
    }

    public List<User> findAllReaders() { return userRepository.findAllReaders(); }

    public List<User> findAllManagers() { return userRepository.findAllManagers(); }

    public List<User> findAllDebtors() { return userRepository.findAllDebtors(); }

}
