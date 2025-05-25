package com.example.Aura.controller;


import com.example.Aura.io.OrderResponse;
import com.example.Aura.io.PaymentRequest;
import com.example.Aura.io.PaymentVerificationRequest;
import com.example.Aura.io.RazorpayOrderResponse;
import com.example.Aura.service.OrderService;
import com.example.Aura.service.RazorPaySerevice;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final RazorPaySerevice razorPaySerevice;

    private final OrderService orderService;


    @PostMapping("/create-order")
    @ResponseStatus(HttpStatus.CREATED)
    public RazorpayOrderResponse createRazorPayOrder(@RequestBody PaymentRequest request) throws RazorpayException {
        return razorPaySerevice.creatOrder(request.getAmount(), request.getCurrency());

    }




    @PostMapping("/verify")
    public OrderResponse verifyPayment(@RequestBody PaymentVerificationRequest request){

        return orderService.verifyPayment(request);

    }
}
