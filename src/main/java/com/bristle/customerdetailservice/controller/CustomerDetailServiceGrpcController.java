package com.bristle.customerdetailservice.controller;

import com.bristle.customerdetailservice.model.CustomerEntity;
import com.bristle.customerdetailservice.service.CustomerDetailService;
import com.bristle.proto.common.ApiError;
import com.bristle.proto.common.ResponseContext;
import com.bristle.proto.customer_detail.Customer;
import com.bristle.proto.customer_detail.CustomerDetailServiceGrpc;
import com.bristle.proto.customer_detail.GetAllCustomersRequest;
import com.bristle.proto.customer_detail.GetAllCustomersResponse;
import com.bristle.proto.customer_detail.UpsertCustomerRequest;
import com.bristle.proto.customer_detail.UpsertCustomerResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@GrpcService
public class CustomerDetailServiceGrpcController extends CustomerDetailServiceGrpc.CustomerDetailServiceImplBase {

    public CustomerDetailService m_customerDetailService;

    Logger log = LoggerFactory.getLogger( CustomerDetailServiceGrpcController.class);


    CustomerDetailServiceGrpcController(CustomerDetailService customerDetailService) {
        this.m_customerDetailService = customerDetailService;
    }

    @Override
    public void getAllCustomers(GetAllCustomersRequest request,
                                StreamObserver<GetAllCustomersResponse> responseObserver) {
        String requestId = request.getRequestContext().getRequestId();
        log.info("Request id: " + requestId + " ,getAllCustomers grpc request received");
        ResponseContext.Builder responseContextBuilder = ResponseContext.newBuilder();
        responseContextBuilder.setRequestId(requestId);

        try {
            List<Customer> customerList = m_customerDetailService.getAllCustomers();
            responseObserver.onNext(
                    GetAllCustomersResponse.newBuilder()
                            .addAllCustomer(customerList)
                            .setResponseContext(responseContextBuilder.build()).build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            log.error("Request id: " + requestId + " " + e.getMessage());
            responseContextBuilder.setError(ApiError.newBuilder()
                    .setErrorMessage("test")
                    .setExceptionName(e.getClass().getName()));

            responseObserver.onNext(GetAllCustomersResponse.newBuilder()
                    .setResponseContext(responseContextBuilder.build()).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void upsertCustomer(UpsertCustomerRequest request, StreamObserver<UpsertCustomerResponse> responseObserver) {
        String requestId = request.getRequestContext().getRequestId();
        log.info("Request id: "+requestId+" , getAllCustomers grpc request received");
        ResponseContext.Builder responseContextBuilder = ResponseContext.newBuilder();
        responseContextBuilder.setRequestId(requestId);
        Customer toBeUpserted = request.getCustomer();

        try {
            Customer addedCustomer = m_customerDetailService.upsertCustomer(toBeUpserted);
            responseObserver.onNext(
                    UpsertCustomerResponse.newBuilder()
                            .setCustomer(addedCustomer)
                            .setResponseContext(responseContextBuilder).build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            log.error("Request id: " + requestId + " " + e.getMessage());
            responseContextBuilder.setError(ApiError.newBuilder()
                    .setErrorMessage(e.getMessage())
                    .setExceptionName(e.getClass().getName()));

            responseObserver.onNext(UpsertCustomerResponse.newBuilder()
                    .setResponseContext(responseContextBuilder.build()).build());
            responseObserver.onCompleted();
        }
    }
}
