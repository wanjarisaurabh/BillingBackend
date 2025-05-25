package com.example.Aura.entity;

import com.example.Aura.io.PaymentDetails;
import com.example.Aura.io.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name="tbl_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {

    @Embedded
    private PaymentDetails paymentDetails;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @JoinColumn(name = "order_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private String customerName;
    private String phoneNumber;
    private Double subtotal;
    private Double tax ;
    private Double grandTotal;
    private LocalDateTime createdAt;




    //called before the save automatically
    @PrePersist
    protected void onCreate() {
        this.orderId = "ORD"+System.currentTimeMillis();
        this.createdAt = LocalDateTime.now();



    }

}
