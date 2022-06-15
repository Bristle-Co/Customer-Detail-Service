package com.bristle.customerdetailservice.util;

import com.bristle.customerdetailservice.model.CustomerEntity;
import com.bristle.proto.customer_detail.Customer;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

public class ProtoUtil {

    public static Customer customerEntityToProto(CustomerEntity customer) {
        return Customer.newBuilder()
                .setCustomerId(customer.getCustomerId())
                .setName(customer.getName())
                .setContactName(customer.getContactName())
                .setContactNumber(customer.getContactNumber())
                .setContactMobileNumber(customer.getContactMobileNumber())
                .setFaxNumber(customer.getFaxNumber())
                .setPostalCode(customer.getPostalCode())
                .setAddress(customer.getAddress())
                .setTaxId(customer.getTaxId())
                .setReceiver(customer.getReceiver())
                .setNote(customer.getNote())
                .build();
    }

    public static List<Customer> customerEntityListToProtoList(List<CustomerEntity> entityList) {
        List<Customer> resultList = new ArrayList<>();
        for (CustomerEntity customer : entityList) {
            resultList.add(Customer.newBuilder()
                    .setCustomerId(customer.getCustomerId())
                    .setName(customer.getName())
                    .setContactName(customer.getContactName())
                    .setContactNumber(customer.getContactNumber())
                    .setContactMobileNumber(customer.getContactMobileNumber())
                    .setFaxNumber(customer.getFaxNumber())
                    .setPostalCode(customer.getPostalCode())
                    .setAddress(customer.getAddress())
                    .setTaxId(customer.getTaxId())
                    .setReceiver(customer.getReceiver())
                    .setNote(customer.getNote())
                    .build());
        }

        return resultList;
    }
}
