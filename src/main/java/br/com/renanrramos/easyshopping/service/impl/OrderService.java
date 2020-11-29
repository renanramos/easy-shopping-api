/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 24/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.repository.OrderRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class OrderService implements CommonService<Order> {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Order save(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public List<Order> findAll(Pageable page) {
		Page<Order> pagedResult = orderRepository.findAll(page);
		return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>();
	}

	@Override
	public Optional<Order> findById(Long orderId) {
		return orderRepository.findById(orderId);
	}

	@Override
	public Order update(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public void remove(Long orderId) {
		orderRepository.deleteById(orderId);
	}

	@Override
	public List<Order> findAllPageable(Pageable page, Long id) {
		return null;
	}

	public List<Order> findCustomerOrders(String customerid) {
		return orderRepository.getCustomerOrders(customerid);
	}
}
