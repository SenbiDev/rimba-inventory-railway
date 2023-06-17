package com.rimba.inventory.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rimba.inventory.enums.CustomerEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
public class Customer implements Serializable {

    private static final long serialVersionUID = -493967282312085855L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Customer(String name, String contact, String email, String address, Integer discount, CustomerEnum discountType, byte[] idCard) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.discount = discount;
        this.discountType = discountType;
        this.idCard = idCard;
    }

    String name;

    String contact;

    String email;

    String address;

    Integer discount;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    CustomerEnum discountType;

    @Lob()
    @JsonIgnore
    @Column(name = "id_card")
    byte[] idCard;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Sales> salesList = new LinkedList<>();
}
