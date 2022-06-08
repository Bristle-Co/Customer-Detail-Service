package com.bristle.customerdetailservice.data.repository;

import com.bristle.customerdetailservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDetailRepository extends JpaRepository<Customer, Long> {

    void addCustomer();
}
