package com.example.Aura.service.impl;

import com.example.Aura.io.OrderResponse;
import com.example.Aura.io.RazorpayOrderResponse;
import com.example.Aura.service.RazorPaySerevice;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RazorPayServiceImpl implements RazorPaySerevice {

    @Value("${razorpay.key.id}")
    private String razorpaykeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;



    private RazorpayOrderResponse convertToResponse(Order order) {
        return RazorpayOrderResponse.builder()
                .id(order.get("id"))
                .entity(order.get("entity"))
                .amount(order.get("amount"))
                .currency(order.get("currency"))
                .status(order.get("status"))
                .createdAt(order.get("created_at"))
                .receipt(order.get("receipt"))
                .build();
    }

    @Override
    public RazorpayOrderResponse creatOrder(Double amount, String currency) throws RazorpayException {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(razorpaykeyId, razorpayKeySecret);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100); // amount in paisa
            orderRequest.put("currency", currency);
            orderRequest.put("receipt", "order_rcptid_" + System.currentTimeMillis());
            orderRequest.put("payment_capture", 1);

            Order order = razorpayClient.orders.create(orderRequest);
            return convertToResponse(order);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create Razorpay order: " + e.getMessage(), e);
        }
    }


}
