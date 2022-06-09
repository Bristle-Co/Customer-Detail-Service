package com.bristle.customerdetailservice.controller;

import com.bristle.customerdetailservice.model.CustomerEntity;
import com.bristle.customerdetailservice.service.CustomerDetailService;
import com.bristle.proto.Customer;
import com.sun.jdi.event.ExceptionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import java.util.List;

@RequestMapping(path = "api/v1/customer-detail")
@RestController
public class CustomerDetailController {

    CustomerDetailService m_customerDetailService;

    @Autowired
    public CustomerDetailController(CustomerDetailService m_customerDetailService) {
        this.m_customerDetailService = m_customerDetailService;
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<CustomerEntity>> getAllCustomers() {
        com.bristle.proto.Customer n = Customer.newBuilder().build();
        try {
            return new ResponseEntity<List<CustomerEntity>>(
                    m_customerDetailService.getAllCustomers(), HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<List<CustomerEntity>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<?> addCustomer(
            @RequestParam(name = "customerId", required = true) String customerId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "contactName", required = false) String contactName,
            @RequestParam(name = "contactNumber", required = false) String contactNumber,
            @RequestParam(name = "contactMobileNumber", required = false) String contactMobileNumber,
            @RequestParam(name = "faxNumber", required = false) String faxNumber,
            @RequestParam(name = "postalCode", required = false) String postalCode,
            @RequestParam(name = "address", required = false) String address,
            @RequestParam(name = "taxId", required = false) String taxId,
            @RequestParam(name = "receiver", required = false) String receiver,
            @RequestParam(name = "note", required = false) String note
        )
    {
        try {
            m_customerDetailService.addCustomer(new CustomerEntity(
                    customerId,
                    name,
                    contactName,
                    contactNumber,
                    contactMobileNumber,
                    faxNumber,
                    postalCode,
                    address,
                    taxId,
                    receiver,
                    note
            ));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
