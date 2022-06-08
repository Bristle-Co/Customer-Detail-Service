package com.bristle.customerdetailservice.controller;

import com.bristle.customerdetailservice.service.CustomerDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class CustomerDetailController {

    CustomerDetailService m_customerDetailService = new

    @Transactional
    ResponseEntity<?> addCustomer(){
        try{

            return new
        }catch( Exception e ){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
