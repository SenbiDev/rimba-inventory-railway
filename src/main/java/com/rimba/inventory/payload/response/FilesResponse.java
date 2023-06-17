package com.rimba.inventory.payload.response;

import com.rimba.inventory.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@Data
public class FilesResponse {

    HttpStatus status;

    String message;

    List<Customer> data;
}
