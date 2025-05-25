package com.example.Aura.io;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboradResponse {

    private Double todaySales;
    private Long todayOrderCount;
    private List<OrderResponse> recentOrders;

}
