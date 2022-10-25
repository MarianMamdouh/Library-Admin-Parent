package com.example.libraryadminapp.core.domain.paymentinfo.repository;

import com.example.libraryadminapp.core.domain.paymentinfo.entity.PaymentInfo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PaymentInfoRepository {

    PaymentInfo save(PaymentInfo course);

    Page<PaymentInfo> findAllByCourseIsNotNull();

    Page<PaymentInfo> findAllByCoursePaperIsNotNull();

    Page<PaymentInfo> findAllByDeliveryAddressIsNotNullAndDeliveryAddressNotEquals();

    Optional<PaymentInfo> findByPaymentNumber(Integer paymentNumber);

    void deleteByPaymentNumber(Integer paymentNumber);

    Optional<PaymentInfo> findByCourseNameAndMobileNumber(String courseName, String mobileNumber) ;

    List<PaymentInfo> findAllByCoursePaperNameAndMobileNumber(String coursePaperName, String mobileNumber);
    }
