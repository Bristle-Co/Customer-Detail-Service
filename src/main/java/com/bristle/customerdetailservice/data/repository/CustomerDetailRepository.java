package com.bristle.customerdetailservice.data.repository;

import com.bristle.customerdetailservice.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDetailRepository extends JpaRepository<CustomerEntity, Long> {
    @Query(value = "SELECT * FROM "+ CustomerEntity.CUSTOMER_TABLE_NAME, nativeQuery = true)
    List<CustomerEntity> getAllCustomers();
}
