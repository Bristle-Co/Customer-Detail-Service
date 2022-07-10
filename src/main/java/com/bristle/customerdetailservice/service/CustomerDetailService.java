package com.bristle.customerdetailservice.service;

import com.bristle.customerdetailservice.converter.CustomerDetailEntityConverter;
import com.bristle.customerdetailservice.data.repository.CustomerEntitySpec;
import com.bristle.customerdetailservice.model.CustomerEntity;
import com.bristle.proto.customer_detail.Customer;
import com.bristle.proto.customer_detail.CustomerFilter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.bristle.customerdetailservice.data.repository.CustomerDetailRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
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
    public List<Customer> getCustomers(CustomerFilter filter, Integer pageIndex, Integer pageSize) throws Exception {

        if(filter == null ) return m_customerDetailRepository.getAllCustomers()
                .stream().map(m_converter::entityToProto).collect(Collectors.toList());
        Specification<CustomerEntity> spec = new Specification<CustomerEntity>() {
            @Override
            public Predicate toPredicate(Root<CustomerEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };

        if (!filter.getCustomerId().equals("")) {
            spec = spec.and(CustomerEntitySpec.likeCustomerId(filter.getCustomerId()));
        }

        if (!filter.getName().equals("")) {
            spec = spec.and(CustomerEntitySpec.likeName(filter.getName()));
        }

        if (!filter.getContactName().equals("")) {
            spec = spec.and(CustomerEntitySpec.likeContactName(filter.getContactName()));
        }

        if (!filter.getContactNumber().equals("")) {
            spec = spec.and(CustomerEntitySpec.likeContactNumber(filter.getContactNumber()));
        }

        if (!filter.getAddress().equals("")) {
            spec = spec.and(CustomerEntitySpec.likeAddress(filter.getAddress()));
        }

        Sort sort = Sort.by(Sort.Direction.ASC, "customerId");
        Pageable paging = PageRequest.of(pageIndex, pageSize, sort);

        List<CustomerEntity> rs = m_customerDetailRepository.findAll(Specification.where(spec), paging).toList();
        return rs.stream().map(m_converter::entityToProto).collect(Collectors.toList());
    }

    @Transactional
    public Customer upsertCustomer(Customer customer) throws Exception {
        if (customer.getCustomerId().equals("")) {
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

    @Transactional
    public Customer deleteCustomer(String customerId) throws Exception {
        CustomerEntity toBeDeleted = m_customerDetailRepository.getCustomerByCustomerId(customerId);
        if (toBeDeleted == null ){
            return null;
        }

        m_customerDetailRepository.deleteCustomerById(customerId);
        return m_converter.entityToProto(toBeDeleted);
    }
}
