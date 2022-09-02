package com.bristle.customerdetailservice.service;

import com.bristle.customerdetailservice.converter.CustomerDetailEntityConverter;
import com.bristle.customerdetailservice.repository.CustomerEntitySpec;
import com.bristle.customerdetailservice.model.CustomerEntity;
import com.bristle.proto.customer_detail.Customer;
import com.bristle.proto.customer_detail.CustomerFilter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.bristle.customerdetailservice.repository.CustomerDetailRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
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

        if (filter == null) return m_customerDetailRepository.getAllCustomers()
                .stream().map(m_converter::entityToProto).collect(Collectors.toList());

        Specification<CustomerEntity> spec = mapCustomerFilterToSpec(filter);

        Sort sort = Sort.by(Sort.Direction.ASC, "customerId");
        Pageable paging = PageRequest.of(pageIndex, pageSize, sort);

        List<CustomerEntity> rs = m_customerDetailRepository.findAll(Specification.where(spec), paging).toList();
        return rs.stream().map(m_converter::entityToProto).collect(Collectors.toList());
    }

    @Transactional
    public Customer upsertCustomer(Customer customer) throws Exception {

        m_customerDetailRepository.save(m_converter.protoToEntity(customer));
        Optional<CustomerEntity> savedCustomer = m_customerDetailRepository.findById(customer.getCustomerId());
        if (!savedCustomer.isPresent()) {
            throw new Exception("unknown error, customer is not saved");
        } else {
            return m_converter.entityToProto(savedCustomer.get());
        }
    }

    @Transactional
    public Customer deleteCustomer(String customerId) throws Exception {
        CustomerEntity toBeDeleted = m_customerDetailRepository.getCustomerByCustomerId(customerId);
        if (toBeDeleted == null) {
            return null;
        }

        m_customerDetailRepository.deleteCustomerById(customerId);
        return m_converter.entityToProto(toBeDeleted);
    }

    private Specification<CustomerEntity> mapCustomerFilterToSpec(CustomerFilter filter){
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

        if(!filter.getMatchingCustomerId().equals("")) {
            spec = spec.and(CustomerEntitySpec.equalCustomerId(filter.getMatchingCustomerId()));
        }

        return spec;
    }
}
