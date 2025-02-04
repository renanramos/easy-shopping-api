/// **
// * ------------------------------------------------------------
// * Project: easy-shopping
// * <p>
// * Creator: renan.ramos - 24/11/2020
// * ------------------------------------------------------------
// */
//package br.com.renanrramos.easyshopping.service.impl;
//
//import br.com.renanrramos.easyshopping.interfaceadapter.entity.OrderItemEntity;
//import br.com.renanrramos.easyshopping.interfaceadapter.repository.OrderItemRepository;
//import br.com.renanrramos.easyshopping.service.CommonService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
///**
// * @author renan.ramos
// *
// */
//@Service
//public class OrderItemService implements CommonService<OrderItemEntity> {
//
//    @Autowired
//    private OrderItemRepository orderItemRepository;
//
//    @Override
//    public OrderItemEntity save(OrderItemEntity orderItemEntity) {
//        return orderItemRepository.save(orderItemEntity);
//    }
//
//    @Override
//    public List<OrderItemEntity> findAll(Pageable page) {
//        Page<OrderItemEntity> pagedResult = orderItemRepository.findAll(page);
//        return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>();
//    }
//
//    @Override
//    public Optional<OrderItemEntity> findById(Long orderItemId) {
//        return orderItemRepository.findById(orderItemId);
//    }
//
//    @Override
//    public OrderItemEntity update(OrderItemEntity orderItemEntity) {
//        return orderItemRepository.save(orderItemEntity);
//    }
//
//    @Override
//    public void remove(Long orderItemId) {
//        orderItemRepository.deleteById(orderItemId);
//    }
//
//    @Override
//    public List<OrderItemEntity> findAllPageable(Pageable page, Long id) {
//        return null;
//    }
//
//    public List<OrderItemEntity> findOrderItemByOrderId(Long orderId) {
//        return orderItemRepository.getOrderItemByOrderId(orderId);
//    }
//
//    public List<OrderItemEntity> orderItemStatistic() {
//        return orderItemRepository.getOrderItemStatistic();
//    }
//}
