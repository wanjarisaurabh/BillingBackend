package com.example.Aura.service.impl;

import com.example.Aura.entity.OrderEntity;
import com.example.Aura.entity.OrderItemEntity;
import com.example.Aura.io.*;
import com.example.Aura.repository.OrderEntityRepository;
import com.example.Aura.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImple implements OrderService {

    private final OrderEntityRepository orderEntityRepository;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        OrderEntity newOrder = convertToOrderEntity(request);

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setStatus(
                newOrder.getPaymentMethod() == PaymentMethod.CASH
                        ? PaymentDetails.PaymentStatus.COMPLETED
                        : PaymentDetails.PaymentStatus.PENDING
        );
        newOrder.setPaymentDetails(paymentDetails);

        List<OrderItemEntity> orderItems = request.getCartItems().stream()
                .map(this::convertToOrderItemEntity)
                .collect(Collectors.toList());

        newOrder.setItems(orderItems);

        orderEntityRepository.save(newOrder);

        System.out.println(newOrder.getPhoneNumber());
        System.out.println(request.getPhoneNumber());

        return convertToResponse(newOrder);
    }

    private OrderItemEntity convertToOrderItemEntity(OrderRequest.OrderItemRequest orderItemRequest) {
        return OrderItemEntity.builder()
                .itemId(orderItemRequest.getItemId())
                .name(orderItemRequest.getName())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity()) // Assuming typo is in the request object too
                .build();
    }

    private OrderEntity convertToOrderEntity(OrderRequest request) {
        return OrderEntity.builder()
                .customerName(request.getCustomerName()) // fixed typo
                .phoneNumber(request.getPhoneNumber()) // fixed typo
                .subtotal(request.getSubtotal())
                .tax(request.getTax())
                .grandTotal(request.getGrandTotal())
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
                .build();
    }

    private OrderResponse.OrderItemResponse convertToItemResponse(OrderItemEntity orderItemEntity) {
        return OrderResponse.OrderItemResponse.builder()
                .itemId(orderItemEntity.getItemId())
                .name(orderItemEntity.getName()) // fixed mistake (was getting itemId again)
                .price(orderItemEntity.getPrice())
                .quntity(orderItemEntity.getQuantity()) // Assuming typo in response class
                .build();
    }

    private OrderResponse convertToResponse(OrderEntity entity) {
        return OrderResponse.builder()
                .orderId(entity.getOrderId())
                .customerName(entity.getCustomerName()) // fixed typo
                .phoneNumber(entity.getPhoneNumber()) // fixed typo
                .subtotal(entity.getSubtotal())
                .tax(entity.getTax())
                .grandTotal(entity.getGrandTotal())
                .paymentMethod(entity.getPaymentMethod())
                .items(entity.getItems().stream()
                        .map(this::convertToItemResponse)
                        .collect(Collectors.toList()))
                .paymentDetails(entity.getPaymentDetails())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    public void deleteOrder(String orderId) {
        OrderEntity existingOrder = orderEntityRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));

        orderEntityRepository.delete(existingOrder);
    }

    @Override
    public List<OrderResponse> getLatestOrder() {
        return orderEntityRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse verifyPayment(PaymentVerificationRequest request) {
        OrderEntity existingOrder = orderEntityRepository.findByOrderId(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));


        if (!verifyRazorpaySignature(request.getRazorpayOrderId(), request.getRazorpayPamentId(), request.getRazorpaySignaturee())) {


            throw new RuntimeException("Payment verification Fialed");

        }

        PaymentDetails paymentDetails = existingOrder.getPaymentDetails();
        paymentDetails.setRazorpayPaymentId(request.getRazorpayOrderId());
        paymentDetails.setRazorpayPaymentId(request.getRazorpayPamentId());
        paymentDetails.setRazorpaySignature(request.getRazorpaySignaturee());

        paymentDetails.setStatus(PaymentDetails.PaymentStatus.COMPLETED);

        existingOrder.setPaymentDetails(paymentDetails);
        existingOrder = orderEntityRepository.save(existingOrder);


        return convertToResponse(existingOrder);

    }



    @Override
    public Double sumSalesByDate(LocalDate date) {
        return orderEntityRepository.sumSalesByDate(date);
    }

    @Override
    public Long countByOrderDate(LocalDate date) {
        return orderEntityRepository.countByOrderDate(date);
    }

    @Override
    public List<OrderResponse> findRecentOrders() {
        return orderEntityRepository.findRecentOrders(PageRequest.of(0, 5))
                .stream()
                .map(orderEntity -> convertToResponse(orderEntity))
                .collect(Collectors.toList());

    }


    private boolean verifyRazorpaySignature(String razorpayOrderId, String razorpayPamentId, String razorpaySignaturee) {

        //for temprary
        return true;
    }

}
