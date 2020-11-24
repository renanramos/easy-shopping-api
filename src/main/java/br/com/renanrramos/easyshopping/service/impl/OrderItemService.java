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

import br.com.renanrramos.easyshopping.model.OrderItem;
import br.com.renanrramos.easyshopping.repository.OrderItemRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class OrderItemService implements CommonService<OrderItem> {

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public OrderItem save(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}

	@Override
	public List<OrderItem> findAll(Pageable page) {
		Page<OrderItem> pagedResult = orderItemRepository.findAll(page);
		return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>();
	}

	@Override
	public Optional<OrderItem> findById(Long orderItemId) {
		return orderItemRepository.findById(orderItemId);
	}

	@Override
	public OrderItem update(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}

	@Override
	public void remove(Long orderItemId) {
		orderItemRepository.deleteById(orderItemId);
	}

	@Override
	public List<OrderItem> findAllPageable(Pageable page, Long id) {
		return null;
	}

}
