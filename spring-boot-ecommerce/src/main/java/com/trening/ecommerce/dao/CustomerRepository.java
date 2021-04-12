package com.trening.ecommerce.dao;

import com.trening.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByEmail(String email);

}
