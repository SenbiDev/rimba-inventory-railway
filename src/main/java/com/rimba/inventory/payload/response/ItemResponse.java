package com.rimba.inventory.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class ItemResponse<T> implements Serializable {

    private static final long serialVersionUID = 6307169583788939793L;


    Integer status;

    String message;

    T data;
}
