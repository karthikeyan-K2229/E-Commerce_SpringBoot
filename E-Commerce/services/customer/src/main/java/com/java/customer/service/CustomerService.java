package com.java.customer.service;

import com.java.customer.entity.Customer;
import com.java.customer.exception.CustomerNotFoundExceptipn;
import com.java.customer.mapper.CustomerMapper;
import com.java.customer.repository.CustomerRepository;
import com.java.customer.response.CustomerRequest;
import com.java.customer.response.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public void updateCustomer(CustomerRequest request) {
        var customer=this.customerRepository.findById(request.id()).orElseThrow(()-> new CustomerNotFoundExceptipn
                (String.format("cannot update Customer::customer not found with this ID: %s",request.id())));
        mergerCustomer(customer,request);
        this.customerRepository.save(customer);

    }

    private void  mergerCustomer(Customer customer,CustomerRequest  request)
    {
        if(StringUtils.isNotBlank(request.firstname()))
        {
            customer.setFirstname(request.firstname());
        }
        if(StringUtils.isNotBlank(request.lastname()))
        {
            customer.setLastname(request.lastname());
        }
        if(request.address()!=null)
        {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return this.customerRepository.findAll().stream()
                .map(this.customerMapper::fromCustomer).collect(Collectors.toList());
    }


    public Boolean findByCustomerId(String customerId) {
        return customerRepository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return this.customerRepository.
                findById(customerId)
                .map(customerMapper::fromCustomer).
                orElseThrow(()-> new CustomerNotFoundExceptipn
                        (String.format("No customer found with the provided ID: %s", customerId)));
    }

    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
