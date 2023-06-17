package com.rimba.inventory.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rimba.inventory.enums.CustomerEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerData {
    Long id;

    String name;

    String contact;

    String email;

    String address;

    Integer discount;

    @JsonProperty(value = "discount_type")
    CustomerEnum discountType;

    @JsonProperty(value = "id_card")
    String idCard;
}
