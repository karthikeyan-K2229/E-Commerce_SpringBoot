package com.java.customer.service;

import com.java.customer.mapper.CustomerMapper;
import com.java.customer.repository.CustomerRepository;
import com.java.customer.response.CustomerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest request)
    {
        var customer=this.customerRepository.save(customerMapper.toCustomer(request));
        return customer.getId();
    }
}
