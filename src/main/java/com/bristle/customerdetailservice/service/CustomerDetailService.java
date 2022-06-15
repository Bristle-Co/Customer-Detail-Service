package com.bristle.customerdetailservice.service;

import com.bristle.customerdetailservice.model.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bristle.customerdetailservice.data.repository.CustomerDetailRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerDetailService {

    private final CustomerDetailRepository m_customerDetailRepository;

    public CustomerDetailService(CustomerDetailRepository customerDetailRepository) {
        this.m_customerDetailRepository = customerDetailRepository;
    }

    @Transactional(readOnly = true)
    public List<CustomerEntity> getAllCustomers() throws Exception {
        return m_customerDetailRepository.getAllCustomers();
    }

    @Transactional
    public void addCustomer(CustomerEntity customerEntity) throws Exception {
        System.out.println("anderson:  "+ customerEntity.toString());
        m_customerDetailRepository.save(customerEntity);
    }
}
