package com.bristle.customerdetailservice.controller;

import com.bristle.customerdetailservice.model.CustomerEntity;
import com.bristle.customerdetailservice.service.CustomerDetailService;
import com.bristle.customerdetailservice.util.ProtoUtil;
import com.bristle.proto.common.ApiError;
import com.bristle.proto.common.ResponseContext;
import com.bristle.proto.customer_detail.CustomerDetail;
import com.bristle.proto.customer_detail.CustomerDetailGrpcService;
import com.bristle.proto.customer_detail.CustomerDetailServiceGrpc;
import com.bristle.proto.customer_detail.GetAllCustomersRequest;
import com.bristle.proto.customer_detail.GetAllCustomersResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
        log.info(request.toString());
//        if (requestId.equals("")) {
//            responseObserver.onNext(
//                    GetAllCustomersResponse.newBuilder().setResponseContext(
//                         ResponseContext.newBuilder().setError(ApiError.newBuilder()
//                                 .setErrorMessage("Unknown request, missing request id"))
//                    ).build());
//            responseObserver.onCompleted();
//            return;
//        }
        ResponseContext.Builder responseBuilder = ResponseContext.newBuilder();
        responseBuilder.setRequestId(requestId);
        List<CustomerEntity> result;
        try {
            result = m_customerDetailService.getAllCustomers();
            responseObserver.onNext(
                    GetAllCustomersResponse.newBuilder()
                            .addAllCustomer(ProtoUtil.customerEntityListToProtoList(result))
                            .setResponseContext(responseBuilder.build()).build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseBuilder.setError(ApiError.newBuilder()
                    .setErrorMessage(e.getMessage())
                    .setExceptionName(e.getClass().getName()));

            responseObserver.onNext(GetAllCustomersResponse.newBuilder()
                    .setResponseContext(responseBuilder.build()).build());
            responseObserver.onCompleted();
        }
    }
}
