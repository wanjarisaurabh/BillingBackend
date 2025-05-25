package com.example.Aura.service;

import com.example.Aura.entity.OrderItemEntity;
import com.example.Aura.io.OrderRequest;
import com.example.Aura.io.OrderResponse;
import com.example.Aura.io.PaymentVerificationRequest;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.*;


public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    void deleteOrder(String orderId);

    List<OrderResponse> getLatestOrder();



    OrderResponse verifyPayment(PaymentVerificationRequest request);

    Double sumSalesByDate(LocalDate date);

    Long countByOrderDate(LocalDate date);

    List<OrderResponse> findRecentOrders();



}
