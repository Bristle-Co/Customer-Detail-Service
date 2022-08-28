package com.bristle.customerdetailservice.controller;

import com.bristle.customerdetailservice.service.CustomerDetailService;
import com.bristle.proto.common.ApiError;
import com.bristle.proto.common.ResponseContext;
import com.bristle.proto.customer_detail.Customer;
import com.bristle.proto.customer_detail.CustomerDetailServiceGrpc;
import com.bristle.proto.customer_detail.DeleteCustomerRequest;
import com.bristle.proto.customer_detail.DeleteCustomerResponse;
import com.bristle.proto.customer_detail.GetCustomersRequest;
import com.bristle.proto.customer_detail.GetCustomersResponse;
import com.bristle.proto.customer_detail.UpsertCustomerRequest;
import com.bristle.proto.customer_detail.UpsertCustomerResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@GrpcService
public class CustomerDetailServiceGrpcController extends CustomerDetailServiceGrpc.CustomerDetailServiceImplBase {

    public final CustomerDetailService m_customerDetailService;

    Logger log = LoggerFactory.getLogger(CustomerDetailServiceGrpcController.class);


    CustomerDetailServiceGrpcController(CustomerDetailService customerDetailService) {
        this.m_customerDetailService = customerDetailService;
    }

    @Override
    public void getCustomers(GetCustomersRequest request, StreamObserver<GetCustomersResponse> responseObserver) {
        String requestId = request.getRequestContext().getRequestId();
        log.info("Request id: " + requestId + " ,getCustomers grpc request received");
        ResponseContext.Builder responseContextBuilder = ResponseContext.newBuilder();
        responseContextBuilder.setRequestId(requestId);

        try {
            List<Customer> customerList = m_customerDetailService.getCustomers(request.getFilter(),request.getPageIndex(),request.getPageSize());
            responseObserver.onNext(
                    GetCustomersResponse.newBuilder()
                            .addAllCustomer(customerList)
                            .setResponseContext(responseContextBuilder.build()).build());

        } catch (Exception e) {
            log.error("Request id: " + requestId + " " + e.getMessage());
            responseContextBuilder.setError(ApiError.newBuilder()
                    .setErrorMessage(e.getMessage())
                    .setExceptionName(e.getClass().getName()));

            responseObserver.onNext(GetCustomersResponse.newBuilder()
                    .setResponseContext(responseContextBuilder.build()).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void upsertCustomer(UpsertCustomerRequest request, StreamObserver<UpsertCustomerResponse> responseObserver) {
        String requestId = request.getRequestContext().getRequestId();
        log.info("Request id: " + requestId + " , upsertCustomer grpc request received. \n" + request.getCustomer());
        ResponseContext.Builder responseContextBuilder = ResponseContext.newBuilder();
        responseContextBuilder.setRequestId(requestId);
        Customer toBeUpserted = request.getCustomer();

        try {
            Customer addedCustomer = m_customerDetailService.upsertCustomer(toBeUpserted);
            responseObserver.onNext(
                    UpsertCustomerResponse.newBuilder()
                            .setCustomer(addedCustomer)
                            .setResponseContext(responseContextBuilder).build());

        } catch (Exception e) {
            log.error("Request id: " + requestId + " " + e.getMessage());
            responseContextBuilder.setError(ApiError.newBuilder()
                    .setErrorMessage(e.getMessage())
                    .setExceptionName(e.getClass().getName()));

            responseObserver.onNext(UpsertCustomerResponse.newBuilder()
                    .setResponseContext(responseContextBuilder.build()).build());
        }
        responseObserver.onCompleted();

    }

    @Override
    public void deleteCustomer(DeleteCustomerRequest request, StreamObserver<DeleteCustomerResponse> responseObserver) {
        String requestId = request.getRequestContext().getRequestId();
        log.info("Request id: " + requestId + " , deleteCustomer grpc request received. " + request.getCustomerId());
        ResponseContext.Builder responseContextBuilder = ResponseContext.newBuilder();
        responseContextBuilder.setRequestId(requestId);

        try {
            Customer deletedCustomer = m_customerDetailService.deleteCustomer(request.getCustomerId());

            if (deletedCustomer == null) {
                responseObserver.onNext(
                        DeleteCustomerResponse.newBuilder()
                                .setResponseContext(responseContextBuilder).build());
                responseObserver.onCompleted();
                // cut execute here
                return;
            }

            responseObserver.onNext(
                    DeleteCustomerResponse.newBuilder()
                            .setDeletedCustomer(deletedCustomer)
                            .setResponseContext(responseContextBuilder).build());

        } catch (Exception e) {
            log.error("Request id: " + requestId + " " + e.getMessage());
            responseContextBuilder.setError(ApiError.newBuilder()
                    .setErrorMessage(e.getMessage())
                    .setExceptionName(e.getClass().getName()));

            responseObserver.onNext(DeleteCustomerResponse.newBuilder()
                    .setResponseContext(responseContextBuilder.build()).build());
        }
        responseObserver.onCompleted();
    }
}
