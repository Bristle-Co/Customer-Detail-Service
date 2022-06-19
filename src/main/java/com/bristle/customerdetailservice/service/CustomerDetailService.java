package com.bristle.customerdetailservice.service;

import com.bristle.customerdetailservice.converter.CustomerDetailEntityConverter;
import com.bristle.customerdetailservice.model.CustomerEntity;
import com.bristle.proto.customer_detail.Customer;
import org.springframework.stereotype.Service;
import com.bristle.customerdetailservice.data.repository.CustomerDetailRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerDetailService {

    private final CustomerDetailRepository m_customerDetailRepository;

    private final CustomerDetailEntityConverter m_converter;
    public CustomerDetailService(CustomerDetailRepository customerDetailRepository, CustomerDetailEntityConverter converter) {
        this.m_customerDetailRepository = customerDetailRepository;
        this.m_converter = converter;
    }

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() throws Exception {
        List<CustomerEntity> entityList = m_customerDetailRepository.getAllCustomers();
        return entityList.stream().map(m_converter::entityToProto).collect(Collectors.toList());
    }

    @Transactional
    public Customer upsertCustomer(Customer customer) throws Exception {
        if ( customer.getCustomerId().equals("")){
            throw new IllegalArgumentException("customerId can not be null or empty string");
        }

        String name = customer.getName();
        String contactName = customer.getContactName();
        String contactNumber = customer.getContactNumber();
        String contactMobileNumber = customer.getContactMobileNumber();
        String faxNumber = customer.getFaxNumber();
        String postalCode = customer.getPostalCode();
        String address = customer.getAddress();
        String taxId = customer.getTaxId();
        String receiver = customer.getReceiver();
        String note = customer.getNote();

        // Named parameters are not supported by JPA in native queries, only for JPQL.
        // Must use positional parameters,
        // which is why we have to do a bunch of null check explicit here
        m_customerDetailRepository.upsertCustomer(
                customer.getCustomerId(),
                name.equals("") ? null : name,
                contactName.equals("") ? null : contactName,
                contactNumber.equals("") ? null : contactNumber,
                contactMobileNumber.equals("") ? null : contactMobileNumber,
                faxNumber.equals("") ? null : faxNumber,
                postalCode.equals("") ? null : postalCode,
                address.equals("") ? null : address,
                taxId.equals("") ? null : taxId,
                receiver.equals("") ? null : receiver,
                note.equals("") ? null : note);

        return customer;
    }
}
