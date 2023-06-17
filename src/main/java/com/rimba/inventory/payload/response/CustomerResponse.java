package com.rimba.inventory.payload.response;

import com.rimba.inventory.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class CustomerResponse<T> implements Serializable {

    private static final long serialVersionUID = -7125859384844185149L;

    Integer status;

    String message;

    T data;
}
