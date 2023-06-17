package com.rimba.inventory.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
public class Sales implements Serializable {

    private static final long serialVersionUID = -3729325249054365078L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_code")
    String transactionCode;

    public Sales(
            ZonedDateTime transactionDate,
            Customer customer,
            LinkedList<Item> item,
            Integer qty,
            Integer totalDiscount,
            Integer totalPay,
            Integer totalPrice
    ) {
        this.transactionCode = UUID.randomUUID().toString();
        this.transactionDate = transactionDate;
        this.customer = customer;
        this.item = item;
        this.qty = qty;
        this.totalDiscount = totalDiscount;
        this.totalPay = totalPay;
        this.totalPrice = totalPrice;
    }

    @Column(name = "transaction_date")
    ZonedDateTime transactionDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer", nullable = false)
    Customer customer;

    @ManyToMany
    @JoinTable(
            name = "items_sales",
            joinColumns = { @JoinColumn(name="sales_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name="item_id", referencedColumnName = "id") }
    )
    List<Item> item = new LinkedList<>();

    Integer qty;

    @Column(name = "total_discount")
    Integer totalDiscount;

    @Column(name = "total_pay")
    Integer totalPay;

    @Column(name = "total_price")
    Integer totalPrice;
}
