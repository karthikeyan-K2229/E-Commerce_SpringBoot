package com.java.customer.repository;

import com.java.customer.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository  extends MongoRepository<Customer,String> {
}
