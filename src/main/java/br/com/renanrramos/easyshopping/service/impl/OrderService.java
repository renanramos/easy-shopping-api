/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 24/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.OrderEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.OrderRepository;
import br.com.renanrramos.easyshopping.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author renan.ramos
 */
@Service
public class OrderService implements CommonService<OrderEntity> {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderEntity save(OrderEntity order) {
        return orderRepository.save(order);
    }

    @Override
    public List<OrderEntity> findAll(Pageable page) {
        Page<OrderEntity> pagedResult = orderRepository.findAll(page);
        return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>();
    }

    @Override
    public Optional<OrderEntity> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public OrderEntity update(OrderEntity order) {
        return orderRepository.save(order);
    }

    @Override
    public void remove(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderEntity> findAllPageable(Pageable page, Long id) {
        return null;
    }

    public List<OrderEntity> findCustomerOrders(String customerid) {
//        return orderRepository.getCustomerOrders(customerid);
        return null;
    }
}
