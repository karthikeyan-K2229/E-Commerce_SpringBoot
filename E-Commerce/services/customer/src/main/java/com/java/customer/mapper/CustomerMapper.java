package com.java.customer.mapper;

import com.java.customer.entity.Customer;
import com.java.customer.response.CustomerRequest;
import com.java.customer.response.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest request)
    {
        if(request==null)
        {
            return null;
        }
        return Customer.builder().
                id(request.id())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .address(request.address()).build();
    }

    public CustomerResponse fromCustomer(Customer customer) {
        if(customer==null) return  null;
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstname(),
                customer.getEmail(),
                customer.getLastname(),
                customer.getAddress()
        );
    }
}
