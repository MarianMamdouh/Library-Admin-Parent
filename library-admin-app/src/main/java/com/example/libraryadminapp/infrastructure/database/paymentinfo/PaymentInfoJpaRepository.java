package com.example.libraryadminapp.infrastructure.database.paymentinfo;

import com.example.libraryadminapp.core.domain.paymentinfo.entity.PaymentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PaymentInfoJpaRepository extends JpaRepository<PaymentInfo, Long> {


    Optional<PaymentInfo> findByPaymentNumber(@Param("paymentNumber") Integer paymentNumber);

    Optional<PaymentInfo> findByCourse_CourseNameAndStudent_MobileNumber(String courseName, String mobileNumber);

    List<PaymentInfo> findAllByCoursePaper_CoursePaperNameAndStudent_MobileNumber(String coursePaperName, String mobileNumber);

    Page<PaymentInfo> findAllByCourseIsNotNull(Pageable pageable);

    Page<PaymentInfo> findAllByDeliveryAddressIsNotNullAndDeliveryAddressNot(String deliveryAddress, Pageable pageable);
    Page<PaymentInfo> findAllByCoursePaperIsNotNull(Pageable pageable);

    void deleteByPaymentNumber(@Param("paymentNumber") Integer paymentNumber);


}
