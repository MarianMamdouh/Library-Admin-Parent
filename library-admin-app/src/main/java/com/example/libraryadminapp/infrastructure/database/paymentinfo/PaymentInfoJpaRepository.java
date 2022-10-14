package com.example.libraryadminapp.infrastructure.database.paymentinfo;

import com.example.libraryadminapp.core.domain.paymentinfo.entity.PaymentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface PaymentInfoJpaRepository extends JpaRepository<PaymentInfo, Long> {

    Page<PaymentInfo> findAllByPaymentNumber(@Param("paymentNumber") Integer paymentNumber, Pageable pageable);

    Page<PaymentInfo> findAllByCourseIsNotNull(Pageable pageable);

    Page<PaymentInfo> findAllByCoursePaperIsNotNull(Pageable pageable);

}
