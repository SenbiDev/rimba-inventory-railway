package com.rimba.inventory.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class SalesResponse<T> implements Serializable {

    private static final long serialVersionUID = 8122060456683784892L;

    Integer status;

    String message;

    T data;
}
