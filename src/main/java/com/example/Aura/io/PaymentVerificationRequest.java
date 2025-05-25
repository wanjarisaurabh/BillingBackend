package com.example.Aura.io;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentVerificationRequest {

    private String razorpayOrderId;
    private String razorpayPamentId;
    private String razorpaySignaturee;
    private String orderId;

}
