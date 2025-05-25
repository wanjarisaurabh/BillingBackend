package com.example.Aura.controller;

import com.example.Aura.io.DashboradResponse;
import com.example.Aura.io.OrderResponse;
import com.example.Aura.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final OrderService orderService;


    @GetMapping("/latest")
    public DashboradResponse getDashboardData() {
        LocalDate today = LocalDate.now();
        Double todaySales = orderService.sumSalesByDate(today);
        Long todayOrderCount = orderService.countByOrderDate(today);
        List<OrderResponse> recentOrders = orderService.findRecentOrders();
        return new DashboradResponse(
                todaySales != null ? todaySales : 0.0,
                todayOrderCount != null ? todayOrderCount : 0,
                recentOrders

        );


    }


}
