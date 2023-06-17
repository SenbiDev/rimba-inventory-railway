package com.rimba.inventory.service;

import com.rimba.inventory.models.Customer;
import com.rimba.inventory.payload.request.CustomerRequest;

import java.io.IOException;
import java.util.List;

public interface CustomerService {

    List<Customer> getAll();
    Customer get(Long id);
    Customer create(CustomerRequest customerRequest) throws IOException;
    Customer update(Long id, CustomerRequest customerRequest) throws IOException;
    String delete(Long id);
    List<Customer> saveAll(Iterable<Customer> customers);
}
