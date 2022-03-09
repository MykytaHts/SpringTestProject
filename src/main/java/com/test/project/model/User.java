package com.test.project.model;

import com.test.project.util.RoleAttributeConverter;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@EqualsAndHashCode(of = {"firstName", "lastName", "password", "email"})
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth", nullable = false, length = 20)
    private LocalDate birthday;

    @Column(name = "date_of_registration", nullable = false, length = 20)
    private final LocalDate registrationDate = LocalDate.now();

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Convert(converter = RoleAttributeConverter.class)
    private Role role = Role.USER;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    public Set<Order> getOrders() {
        return Collections.unmodifiableSet(orders);
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }

    public void removeOrder(Order order) {
        if (!order.isActive()) {
            orders.remove(order);
            order.setUser(null);
        }
    }
}
