package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.core.domain.Order;
import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import br.com.renanrramos.easyshopping.core.gateway.OrderGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.OrderEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.OrderMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.OrderRepository;
import br.com.renanrramos.easyshopping.service.BaseAuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.StringUtils;

import java.util.Optional;

@RequiredArgsConstructor
public class OrderGatewayImpl implements OrderGateway {

    private final OrderRepository orderRepository;

    private final BaseAuthenticationService baseAuthenticationService;

    @Override
    public Order save(final Order order) {
        final OrderEntity orderEntity = OrderMapper.INSTANCE.mapOrderToOrderEntity(order);
        orderEntity.setCustomerId(baseAuthenticationService.getName());
        return OrderMapper.INSTANCE.mapOrderEntityToOrder(orderRepository.save(orderEntity));
    }

    @Override
    public Page<Order> findAllPageable(final ParametersRequest parametersRequest,
                                       final String searchKey) {
        final Page<OrderEntity> orderEntities = StringUtils.isEmpty(searchKey) ?
                orderRepository.findAll(new PageableFactory().buildPageable(parametersRequest)) :
                orderRepository.findOrderByOrderNumber(searchKey);
        return new PageImpl<>(OrderMapper.INSTANCE.mapOrderEntityListToOrderList(orderEntities.getContent()));
    }

    @Override
    public Order findById(final Long orderId) {
        final OrderEntity orderEntity = getOrderEntityByIdOrThrow(orderId);
        return OrderMapper.INSTANCE.mapOrderEntityToOrder(orderEntity);
    }

    @Override
    public Order update(final Order order, final Long orderId) {
        OrderEntity orderEntity = getOrderEntityByIdOrThrow(orderId);
        OrderMapper.INSTANCE.mapOrderToUpdateOrder(orderEntity, order);
        orderEntity.setFinished(true);
        return OrderMapper.INSTANCE.mapOrderEntityToOrder(orderRepository.save(orderEntity));
    }

    @Override
    public void remove(final Long orderId) {
        getOrderEntityByIdOrThrow(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public Page<Order> findCustomerOrders(final String orderCustomerId) {
        final Page<OrderEntity> pageOrders = orderRepository.getCustomerOrders(orderCustomerId);
        return new PageImpl<>(OrderMapper.INSTANCE.mapOrderEntityListToOrderList(pageOrders.getContent()));
    }

    public void validateOrderId(final Long orderId) {
        getOrderEntityByIdOrThrow(orderId);
    }

    private OrderEntity getOrderEntityByIdOrThrow(final Long orderId) {
        final Optional<OrderEntity> orderById = orderRepository.findById(orderId);
        if (orderById.isEmpty())
            throw new EntityNotFoundException(ExceptionConstantMessages.ORDER_NOT_FOUND);
        return orderById.get();
    }
}
