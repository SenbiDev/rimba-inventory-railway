package com.rimba.inventory.service;

import com.rimba.inventory.models.Sales;
import com.rimba.inventory.payload.request.SalesRequest;

import java.util.List;

public interface SalesService {
    List<Sales> getAll();
    Sales get(Long id);
    Sales create(SalesRequest salesRequest);
    Sales update(Long id, SalesRequest salesRequest);
    String delete(Long id);
}
