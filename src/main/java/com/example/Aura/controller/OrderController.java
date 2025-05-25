package com.example.Aura.controller;


import com.example.Aura.io.OrderRequest;
import com.example.Aura.io.OrderResponse;
import com.example.Aura.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {


private final OrderService orderService;


@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public OrderResponse createOrder(@RequestBody OrderRequest orderRequest){
   return  orderService.createOrder(orderRequest);
}

@DeleteMapping("/{orderId}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteOrder(@PathVariable String orderId){
    orderService.deleteOrder(orderId);
}


@GetMapping("/latest")
public List<OrderResponse> getLatestOrders(){
    return orderService.getLatestOrder();
}


}
