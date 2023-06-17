package com.rimba.inventory.controller;

import com.rimba.inventory.dummy.DummyCustomer;
import com.rimba.inventory.models.Customer;
import com.rimba.inventory.payload.request.CustomerRequest;
import com.rimba.inventory.payload.response.CustomerData;
import com.rimba.inventory.payload.response.CustomerResponse;
import com.rimba.inventory.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/api/customers")
@PreAuthorize("hasRole('ADMIN')")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    DummyCustomer dummyCustomer;

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Customer customer = customerService.get(id);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(customer.getIdCard());
    }

    @GetMapping("")
    ResponseEntity<CustomerResponse> getAll() {
        List<Customer> getAllCustomer = customerService.getAll();
        List<CustomerData> customerDataList = getAllCustomer.stream().map((data) -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/")
                    .path("customers/")
                    .path("image/")
                    .path(String.valueOf(data.getId()))
                    .toUriString();

            CustomerData customerData = new CustomerData(
                    data.getId(),
                    data.getName(),
                    data.getContact(),
                    data.getEmail(),
                    data.getAddress(),
                    data.getDiscount(),
                    data.getDiscountType(),
                    fileDownloadUri
            );

            return customerData;
        }).toList();


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new CustomerResponse(HttpStatus.OK.value(), "success", customerDataList)
                );
    }

    @RequestMapping(path = "", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    ResponseEntity<CustomerResponse> create(@ModelAttribute CustomerRequest customerRequest) throws IOException {
        Customer customer = customerService.create(customerRequest);

    String fileDownloadUri = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/api/")
            .path("customers/")
            .path("image/")
            .path(String.valueOf(customer.getId()))
            .toUriString();

    CustomerData customerData = new CustomerData(
            customer.getId(),
            customer.getName(),
            customer.getContact(),
            customer.getEmail(),
            customer.getAddress(),
            customer.getDiscount(),
            customer.getDiscountType(),
            fileDownloadUri
    );

    return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CustomerResponse(
                        HttpStatus.CREATED.value(),
                        "success",
                        customerData
                ));
    }

    @RequestMapping(path = "/{id}", method = PUT, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    ResponseEntity<CustomerResponse> update(
            @PathVariable Long id,
            @ModelAttribute CustomerRequest customerRequest
    ) throws IOException {
        Customer customer = customerService.update(id, customerRequest);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/")
                .path("customers/")
                .path("image/")
                .path(String.valueOf(customer.getId()))
                .toUriString();

        CustomerData customerData = new CustomerData(
                customer.getId(),
                customer.getName(),
                customer.getContact(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getDiscount(),
                customer.getDiscountType(),
                fileDownloadUri
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CustomerResponse(
                        HttpStatus.OK.value(),
                        "success",
                        customerData
                ));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<CustomerResponse> delete(@PathVariable Long id) {
        String message = customerService.delete(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CustomerResponse(
                        HttpStatus.OK.value(),
                        message,
                        null
                ));
    }

    @PostMapping("/save-all")
    ResponseEntity<CustomerResponse> saveAll() throws IOException {
        List<Customer> customerList = dummyCustomer.get();
        List<Customer> customers = customerService.saveAll(customerList);
        List<CustomerData> customerDataList = customers.stream().map((data) -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/")
                    .path("customers/")
                    .path("image/")
                    .path(String.valueOf(data.getId()))
                    .toUriString();

            CustomerData customerData = new CustomerData(
                    data.getId(),
                    data.getName(),
                    data.getContact(),
                    data.getEmail(),
                    data.getAddress(),
                    data.getDiscount(),
                    data.getDiscountType(),
                    fileDownloadUri
            );

            return customerData;
        }).toList();


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new CustomerResponse(HttpStatus.CREATED.value(), "success", customerDataList)
                );
    }
}
