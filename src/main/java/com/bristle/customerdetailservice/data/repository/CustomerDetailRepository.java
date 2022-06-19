package com.bristle.customerdetailservice.data.repository;

import com.bristle.customerdetailservice.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bristle.customerdetailservice.model.CustomerEntity.TABLE_NAME;
import static com.bristle.customerdetailservice.model.CustomerEntity.COLM_CUSTOMER_ID;
import static com.bristle.customerdetailservice.model.CustomerEntity.COLM_NAME;
import static com.bristle.customerdetailservice.model.CustomerEntity.COLM_CONTACT_NAME;
import static com.bristle.customerdetailservice.model.CustomerEntity.COLM_CONTACT_NUMBER;
import static com.bristle.customerdetailservice.model.CustomerEntity.COLM_CONTACT_MOBILE_NUMBER;
import static com.bristle.customerdetailservice.model.CustomerEntity.COLM_FAX_NUMBER;
import static com.bristle.customerdetailservice.model.CustomerEntity.COLM_POSTAL_CODE;
import static com.bristle.customerdetailservice.model.CustomerEntity.COLM_ADDRESS;
import static com.bristle.customerdetailservice.model.CustomerEntity.COLM_TAX_ID;
import static com.bristle.customerdetailservice.model.CustomerEntity.COLM_RECEIVER;
import static com.bristle.customerdetailservice.model.CustomerEntity.COLM_NOTE;

@Repository
public interface CustomerDetailRepository extends JpaRepository<CustomerEntity, Long> {

    @Modifying
    @Query(value = "DELETE FROM " + TABLE_NAME + " WHERE "+COLM_CUSTOMER_ID+ " = ?1 ;", nativeQuery = true)
    void deleteCustomerById(String customerId);

    @Query(value = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLM_CUSTOMER_ID + " = ?1 ;"
            , nativeQuery = true)
    CustomerEntity getCustomerByCustomerId(String customerId);

    // This result is ordered alphabetically
    @Query(value = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLM_CUSTOMER_ID + ";"
            , nativeQuery = true)
    List<CustomerEntity> getAllCustomers();

    @Modifying
    @Query(value = "REPLACE INTO " + TABLE_NAME
            + " ( " + COLM_CUSTOMER_ID + ", "
            + COLM_NAME + ", "
            + COLM_CONTACT_NAME + ", "
            + COLM_CONTACT_NUMBER + ", "
            + COLM_CONTACT_MOBILE_NUMBER + ", "
            + COLM_FAX_NUMBER + ", "
            + COLM_POSTAL_CODE + ", "
            + COLM_ADDRESS + ", "
            + COLM_TAX_ID + ", "
            + COLM_RECEIVER + ", "
            + COLM_NOTE + ") VALUES ( ?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11 )"
            , nativeQuery = true)
    void upsertCustomer(String customerId,
                        String name,
                        String contactName,
                        String contactNumber,
                        String contactMobileNumber,
                        String faxNumber,
                        String postalCode,
                        String address,
                        String taxId,
                        String receiver,
                        String note);


}
