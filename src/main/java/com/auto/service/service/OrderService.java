package com.auto.service.service;


import com.auto.service.entity.FirebaseToken;
import com.auto.service.entity.Order;
import com.auto.service.entity.ServiceEntityProvider;
import com.auto.service.entity.User;
import com.auto.service.entity.enums.OrderStatus;
import com.auto.service.firebase.MessageSender;
import com.auto.service.payloads.OrderPayload;
import com.auto.service.repository.OrderRepository;
import com.auto.service.repository.ServiceEntityRepository;
import com.auto.service.repository.UserRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Getter
@Setter
@NoArgsConstructor
public class OrderService {

    private Order temporaryOrder;

    private OrderRepository orderRepository;
    private UserRepository userRepo;
    private MessageSender messageSender;
    private ServiceEntityRepository serviceRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,ServiceEntityRepository serviceRepository, UserRepository userRepo, MessageSender messageSender) {
        this.orderRepository = orderRepository;
        this.userRepo = userRepo;
        this.messageSender = messageSender;
        this.serviceRepository = serviceRepository;
    }

    public boolean setOrder(OrderPayload order, User user) {
//        Order order1 = new Order();
//        order1.setOrderStatus(OrderStatus.ORDERED);
//        order1.setDescription(order.getDesc());
//        order1.setLat(order.getLat());
//        order1.setLan(order.getLan());
//        order1.setUser(user);
//        order1.setServiceToken(order.getServiceLocation());
//        userRepo.save(user);
//        orderRepository.save(order1);
//        setTemporaryOrder(order1);
//        List<ServiceEntityProvider> company = serviceRepository.findAllById(Collections.singleton(order.getCompanyId()));
//        for (ServiceEntityProvider oo: company) {
//            List<FirebaseToken> tokens = oo.getTokenList();
//            for (FirebaseToken token : tokens) {
//                messageSender.notification(token.getFirebaseKey(), user.getPhoneNumber(), order.getDesc());
//            }
//
//        }
        return true;
    }

    public List<Order> getOrders(User user) {
      List<Order> findOrders = orderRepository.findAllByUser(user);
      return findOrders;
    }


    public Order cancelOrder(User user) {
        Order order = getTemporaryOrder();
        order.setOrderStatus(OrderStatus.CANCELLED);
        userRepo.save(user);
        orderRepository.save(order);
        return order;
    }
}
