package com.rimba.inventory.service;

import com.rimba.inventory.exception.ResourceNotFoundException;
import com.rimba.inventory.models.Customer;
import com.rimba.inventory.models.Item;
import com.rimba.inventory.models.Sales;
import com.rimba.inventory.enums.CustomerEnum;
import com.rimba.inventory.payload.request.SalesRequest;
import com.rimba.inventory.repository.CustomerRepository;
import com.rimba.inventory.repository.ItemRepository;
import com.rimba.inventory.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class SalesImpl implements SalesService {

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<Sales> getAll() {
        return salesRepository.findAll();
    }

    @Override
    public Sales get(Long id) {
        return salesRepository.getReferenceById(id);
    }

    @Override
    public Sales create(SalesRequest salesRequest) {
        Customer customer = customerRepository
                .findById(salesRequest.getCustomer())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format("customer with id %s not found", salesRequest.getCustomer())
                        ));

        LinkedList<Long> itemIds = new LinkedList<>(Arrays.asList(salesRequest.getItem()));
        LinkedList<Item> items = new LinkedList<>(itemRepository.findAllById(itemIds).stream().map((item) -> {
            item.setStock(item.getStock() - 1);
            return item;
        }).toList());

        itemRepository.saveAll(items);

        Double totalDiscount = customer.getDiscount().doubleValue();
        LinkedList<Integer> itemsPrice = new LinkedList<>(items.stream().map(Item::getUnitPrice).toList());
        Integer totalPrice = itemsPrice.stream().reduce(0, Integer::sum);

        Double totalPay;

        if (customer.getDiscountType() != null && customer.getDiscountType().equals(CustomerEnum.PERSENTASE)) {
            System.out.println("PERSENTASE DIPILIH");
            Double percentage = (totalDiscount / 100);
            Double discount = percentage * totalPrice;
            totalPay = totalPrice - discount;
            System.out.println("TOTAL BAYAR : " + discount);
        } else {
            System.out.println("FIX DISCOUNT DIPILIH");
            totalPay = totalPrice - totalDiscount;
            System.out.println("TOTAL BAYAR : " + totalPay);
        }

        Sales newSales = new Sales(
                ZonedDateTime.now(),
                customer,
                items,
                items.size(),
                totalDiscount.intValue(),
                totalPay.intValue(),
                totalPrice
        );

        return salesRepository.save(newSales);
    }

    @Override
    public Sales update(Long id, SalesRequest salesRequest) {
        Sales existingSales = salesRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format("sales with id %s not found", id)
                        ));

        Customer customer = salesRequest.getCustomer() == null
            ? existingSales.getCustomer()
            : customerRepository
                .findById(salesRequest.getCustomer())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format("customer with id %s not found", salesRequest.getCustomer())
                        ));

        LinkedList<Long> itemIds = salesRequest.getItem() == null ? new LinkedList<>() : new LinkedList<>(Arrays.asList(salesRequest.getItem()));

        List<Item> items;
        if (salesRequest.getItem() == null) {
            items = existingSales.getItem();
        } else {
            LinkedList<Item> existingItem = new LinkedList<>(existingSales.getItem().stream().map((existItem) -> {
                existItem.setStock(existItem.getStock() + 1);
                return existItem;
            }).toList());

            itemRepository.saveAll(existingItem);

            items = new LinkedList<>(itemRepository.findAllById(itemIds).stream().map((item) -> {
                item.setStock(item.getStock() - 1);
                return item;
            }).toList());

            itemRepository.saveAll(items);
        }

        Double totalDiscount = customer.getDiscount().doubleValue();
        LinkedList<Integer> itemsPrice = new LinkedList<>(items.stream().map(Item::getUnitPrice).toList());
        Integer totalPrice = itemsPrice.stream().reduce(0, Integer::sum);

        Double totalPay;

        if (customer.getDiscountType() != null && customer.getDiscountType().equals(CustomerEnum.PERSENTASE)) {
            System.out.println("PERSENTASE DIPILIH");
            Double percentage = (totalDiscount / 100);
            Double discount = percentage * totalPrice;
            totalPay = totalPrice - discount;
            System.out.println("TOTAL BAYAR : " + discount);
        } else {
            System.out.println("FIX DISCOUNT DIPILIH");
            totalPay = totalPrice - totalDiscount;
            System.out.println("TOTAL BAYAR : " + totalPay);
        }

        existingSales.setCustomer(customer);
        existingSales.setItem(items);
        existingSales.setQty(items.size());
        existingSales.setTotalDiscount(totalDiscount.intValue());
        existingSales.setTotalPay(totalPay.intValue());
        existingSales.setTotalPrice(totalPrice);

        return salesRepository.save(existingSales);
    }

    @Override
    public String delete(Long id) {
        salesRepository.deleteById(id);

        return "deleted";
    }
}
