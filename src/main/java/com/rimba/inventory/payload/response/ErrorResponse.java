package com.rimba.inventory.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class ErrorResponse {
    Integer status;

    String error;

    String message;
}
