package com.example.Aura.repository;

import com.example.Aura.entity.OrderEntity;
import com.example.Aura.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemEntityRepository extends JpaRepository<OrderItemEntity , Long> {
}
