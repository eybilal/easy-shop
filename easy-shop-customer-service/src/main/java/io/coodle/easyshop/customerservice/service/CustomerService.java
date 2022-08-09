package io.coodle.easyshop.customerservice.service;

import io.coodle.easyshop.customerservice.model.entity.Customer;

import java.util.Collection;

public interface CustomerService {
    Customer findCustomerById(Long id);
    Collection<Customer> findAllCustomers(String name);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customer);

}
