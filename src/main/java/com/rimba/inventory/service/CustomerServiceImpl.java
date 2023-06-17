package com.rimba.inventory.service;

import com.rimba.inventory.exception.ResourceNotFoundException;
import com.rimba.inventory.models.Customer;
import com.rimba.inventory.enums.CustomerEnum;
import com.rimba.inventory.payload.request.CustomerRequest;
import com.rimba.inventory.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer get(Long id) {
        return customerRepository.getReferenceById(id);
    }

    @Override
    public Customer create(CustomerRequest customerRequest) throws IOException {
        CustomerEnum customerEnum;

        if (customerRequest.getTipeDiskon().equals("persentase")) {
            customerEnum = CustomerEnum.PERSENTASE;
        } else {
            customerEnum = CustomerEnum.FIX_DISKON;
        }

        Customer newCustomer = new Customer(
                customerRequest.getNama(),
                customerRequest.getContact(),
                customerRequest.getEmail(),
                customerRequest.getAlamat(),
                customerRequest.getDiskon(),
                customerEnum,
                customerRequest.getKtp().getBytes()
        );

        return customerRepository.save(newCustomer);
    }

    @Override
    public Customer update(Long id, CustomerRequest customerRequest) throws IOException {
        Customer existingCustomer = customerRepository
                .findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(String.format("customer with id %s not found", id))
                );

        CustomerEnum customerEnum;

        if (customerRequest.getTipeDiskon() != null && customerRequest.getTipeDiskon().equals("persentase")) {
            customerEnum = CustomerEnum.PERSENTASE;
        } else {
            customerEnum = CustomerEnum.FIX_DISKON;
        }

        existingCustomer.setName(customerRequest.getNama() == null ? existingCustomer.getName() : customerRequest.getNama());
        existingCustomer.setContact(customerRequest.getContact() == null ? existingCustomer.getContact() : customerRequest.getContact());
        existingCustomer.setEmail(customerRequest.getEmail() == null ? existingCustomer.getEmail() : customerRequest.getEmail());
        existingCustomer.setAddress(customerRequest.getAlamat() == null ? existingCustomer.getAddress() : customerRequest.getAlamat());
        existingCustomer.setDiscount(customerRequest.getDiskon() == null ? existingCustomer.getDiscount() : customerRequest.getDiskon());
        existingCustomer.setDiscountType(customerRequest.getTipeDiskon() == null ? existingCustomer.getDiscountType() : customerEnum);
        existingCustomer.setIdCard(customerRequest.getKtp() == null ? existingCustomer.getIdCard() : customerRequest.getKtp().getBytes());

        return customerRepository.save(existingCustomer);
    }

    @Override
    public String delete(Long id) {
        customerRepository.deleteById(id);

        return "deleted";
    }

    @Override
    public List<Customer> saveAll(Iterable<Customer> customers) {
        return customerRepository.saveAll(customers);
    }
}
