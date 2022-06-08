package com.bristle.customerdetailservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bristle.customerdetailservice.data.repository.CustomerDetailRepository;

import javax.transaction.Transactional;

@Service
public class CustomerDetailService {

    private final CustomerDetailRepository m_customerDetailRepository;

    @Autowired
    public CustomerDetailService(CustomerDetailRepository customerDetailRepository){
        this.m_customerDetailRepository = customerDetailRepository;
    }

    @Transactional
    public void addCustomer(String order_id, String order_pr) {
        m_customerDetailRepository.addCustomer();
    }
}
