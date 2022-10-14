package com.example.libraryadminapp.infrastructure.database.paymentinfo.impl;

import com.example.libraryadminapp.core.domain.paymentinfo.entity.PaymentInfo;
import com.example.libraryadminapp.core.domain.paymentinfo.repository.PaymentInfoRepository;
import com.example.libraryadminapp.infrastructure.database.paymentinfo.PaymentInfoJpaRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PaymentInfoRepositoryImpl implements PaymentInfoRepository {

    private final PaymentInfoJpaRepository paymentInfoJpaRepository;


    @Override
    public PaymentInfo save(final PaymentInfo paymentInfo) {

        return paymentInfoJpaRepository.save(paymentInfo);
    }

    @Override
    public Page<PaymentInfo> findAllByCourseIsNotNull() {

        return paymentInfoJpaRepository.findAllByCourseIsNotNull(Pageable.unpaged());
    }

    @Override
    public Page<PaymentInfo> findAllByCoursePaperIsNotNull() {

        return paymentInfoJpaRepository.findAllByCoursePaperIsNotNull(Pageable.unpaged());
    }

    @Override
    public Page<PaymentInfo> findByPaymentNumber(final Integer paymentNumber) {

        return paymentInfoJpaRepository.findAllByPaymentNumber(paymentNumber, Pageable.unpaged());
    }
}
