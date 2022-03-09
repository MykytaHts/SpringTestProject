package com.test.project.model;


import com.test.project.util.LocalDateAttributeConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Convert(converter = LocalDateAttributeConverter.class)
    @Column(name = "order_date", nullable = false)
    private final LocalDate orderDate;

    @Convert(converter = LocalDateAttributeConverter.class)
    @Column(name = "required_date", nullable = false)
    private final LocalDate requiredDate;

    @Setter
    @Convert(converter = LocalDateAttributeConverter.class)
    @Column(name = "return_date")
    private LocalDate returnDate;

    @Setter
    private boolean active;

    {
        orderDate = LocalDate.now();
        requiredDate = orderDate.plusMonths(3);
    }

    @Setter(AccessLevel.PACKAGE)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "book_id")
    private Book book;

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Order)) return false;

        Order order = (Order) object;
        return id != null && id.equals(order.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
