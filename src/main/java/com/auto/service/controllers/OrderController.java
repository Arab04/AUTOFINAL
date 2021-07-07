package com.auto.service.controllers;


import com.auto.service.entity.Order;
import com.auto.service.entity.User;
import com.auto.service.firebase.MessageSender;
import com.auto.service.payloads.ApiResponse;
import com.auto.service.payloads.OrderPayload;
import com.auto.service.security.CurrentUser;
import com.auto.service.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/setOrder")
    public ResponseEntity<?> setOrder(@RequestBody OrderPayload order, @CurrentUser User user) {
        orderService.setOrder(order,user);
        return ResponseEntity.ok().body(new ApiResponse("Order Saved",true));
    }

    @GetMapping("/getOrders")
    public ResponseEntity<?> getOrders(@CurrentUser User user) {
       List<Order> orders = orderService.getOrders(user);
       if(orders.isEmpty()) {
           return ResponseEntity.ok().body(new ApiResponse("No orders yet",true));
       }

       else{
           return ResponseEntity.ok().body(orders);
       }
    }

    @PutMapping("/cancelOrder")
    public ResponseEntity<?> cancelOrder(@CurrentUser User user) {
        orderService.cancelOrder(user);
        return ResponseEntity.ok().body(new ApiResponse("Order Cancelled",true));
    }
}
