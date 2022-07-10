package com.bristle.customerdetailservice.data.repository;

import com.bristle.customerdetailservice.model.CustomerEntity;
import org.springframework.data.jpa.domain.Specification;


public class CustomerEntitySpec {

    public static Specification<CustomerEntity> likeCustomerId(String customerId) {
        if (customerId == null) return null;

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("customerId"), "%" + customerId + "%"));
    }

    public static Specification<CustomerEntity> likeName(String customerName) {
        if (customerName == null) return null;

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + customerName + "%"));
    }

    public static Specification<CustomerEntity> likeContactName (String contactName) {
        if (contactName == null) return null;

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("contactName"), "%" + contactName + "%"));
    }

    public static Specification<CustomerEntity> likeContactNumber (String contactNumber) {
        if (contactNumber == null) return null;

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("contactNumber"), "%" + contactNumber + "%"));
    }

    public static Specification<CustomerEntity> likeAddress (String address) {
        if (address == null) return null;

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("address"), "%" + address + "%"));
    }


}
