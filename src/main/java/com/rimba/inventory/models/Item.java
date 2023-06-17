package com.rimba.inventory.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rimba.inventory.enums.ItemEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
public class Item implements Serializable {

    private static final long serialVersionUID = -7125859384844185149L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Item(String itemName, ItemEnum unit, Integer stock, Integer unitPrice, byte[] itemImage) {
        this.itemName = itemName;
        this.unit = unit;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.itemImage = itemImage;
    }

    @Column(name = "item_name")
    String itemName;

    @Enumerated(EnumType.STRING)
    ItemEnum unit;

    Integer stock;

    @Column(name = "unit_price")
    Integer unitPrice;

    @Lob()
    @JsonIgnore
    @Column(name = "item_image")
    byte[] itemImage;

    @JsonIgnore
    @ManyToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Sales> sales = new LinkedList<>();
}
