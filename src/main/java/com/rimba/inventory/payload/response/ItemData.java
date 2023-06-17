package com.rimba.inventory.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rimba.inventory.enums.ItemEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ItemData {
    private Long id;

    @JsonProperty(value = "item_name")
    String itemName;

    ItemEnum unit;

    Integer stock;

    @JsonProperty(value = "unit_price")
    Integer unitPrice;

    @JsonProperty(value = "item_image")
    String itemImage;
}
