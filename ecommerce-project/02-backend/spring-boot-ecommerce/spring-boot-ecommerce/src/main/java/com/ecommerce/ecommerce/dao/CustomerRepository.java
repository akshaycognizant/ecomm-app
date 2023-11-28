package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    public  Customer findByEmail(String email);
}
