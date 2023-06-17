package com.rimba.inventory.controller;

import com.rimba.inventory.models.Sales;
import com.rimba.inventory.payload.request.SalesRequest;
import com.rimba.inventory.payload.response.SalesResponse;
import com.rimba.inventory.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/api/sales")
@PreAuthorize("hasRole('ADMIN')")
public class SalesController {

    @Autowired
    SalesService salesService;

    @GetMapping("")
    ResponseEntity<SalesResponse> getAll() {
        List<Sales> allSales = salesService.getAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new SalesResponse(HttpStatus.OK.value(), "success", allSales)
                );
    }

    @GetMapping("/{id}")
    ResponseEntity<SalesResponse> get(@PathVariable Long id) {
        Sales sales = salesService.get(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new SalesResponse(HttpStatus.OK.value(), "success", sales)
                );
    }

    @RequestMapping(path = "", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    ResponseEntity<SalesResponse> create(@ModelAttribute SalesRequest salesRequest) {
        Sales sales = salesService.create(salesRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SalesResponse(
                        HttpStatus.CREATED.value(),
                        "success",
                        sales
                ));
    }

    @RequestMapping(path = "/{id}", method = PUT, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    ResponseEntity<SalesResponse> update(@PathVariable Long id, @ModelAttribute SalesRequest salesRequest) {
        Sales sales = salesService.update(id, salesRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SalesResponse(
                        HttpStatus.OK.value(),
                        "success",
                        sales
                ));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<SalesResponse> delete(@PathVariable Long id) {
        String message = salesService.delete(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SalesResponse(
                        HttpStatus.OK.value(),
                        message,
                        null
                ));
    }
}
