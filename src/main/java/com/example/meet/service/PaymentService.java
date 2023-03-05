package com.example.meet.service;

import com.example.meet.dto.PaymentDto;
import com.example.meet.entity.Payment;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    Payment savePayment(PaymentDto paymentDto);
}
