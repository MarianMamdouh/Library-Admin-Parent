package com.example.libraryadminapp.core.domain.paymentinfo.repository;

import com.example.libraryadminapp.core.domain.paymentinfo.entity.PaymentInfo;
import org.springframework.data.domain.Page;

public interface PaymentInfoRepository {

    PaymentInfo save(PaymentInfo course);

    Page<PaymentInfo> findAllByCourseIsNotNull();

    Page<PaymentInfo> findAllByCoursePaperIsNotNull();

    Page<PaymentInfo> findByPaymentNumber(Integer paymentNumber);

}
