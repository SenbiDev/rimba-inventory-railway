package com.rimba.inventory.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JwtResponse {

    Integer status;

    String message;

    JwtData data;
}
