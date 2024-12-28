package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.Order;
import br.com.renanrramos.easyshopping.core.gateway.OrderGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.OrderEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.OrderMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderGatewayImpl implements OrderGateway {

    private final OrderRepository orderRepository;

    @Override
    public Order save(final Order order) {
        final OrderEntity orderEntity = OrderMapper.INSTANCE.mapOrderToOrderEntity(order);
        return OrderMapper.INSTANCE.mapOrderEntityToOrder(orderRepository.save(orderEntity));
    }

    @Override
    public PageResponse<Order> findAllPageable(final Integer pageNumber,
                                               final Integer pageSize,
                                               final String sortBy,
                                               final String searchKey) {
        final Pageable pageable = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();
        final Page<OrderEntity> orderEntities = orderRepository.findAll(pageable);
        return PageResponse.buildPageResponse(orderEntities,
                OrderMapper.INSTANCE.mapOrderEntityListToOrderList(orderEntities.getContent()));
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
        return OrderMapper.INSTANCE.mapOrderEntityToOrder(orderRepository.save(orderEntity));
    }

    @Override
    public void remove(final Long orderId) {
        getOrderEntityByIdOrThrow(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public PageResponse<Order> findCustomerOrders(final String orderCustomerId) {
        final Page<OrderEntity> pageOrders = orderRepository.getCustomerOrders(orderCustomerId);

        return PageResponse.buildPageResponse(pageOrders,
                OrderMapper.INSTANCE.mapOrderEntityListToOrderList(pageOrders.getContent()));
    }

    private OrderEntity getOrderEntityByIdOrThrow(final Long orderId) {
        final Optional<OrderEntity> orderById = orderRepository.findById(orderId);
        if (orderById.isEmpty())
            throw new EntityNotFoundException(ExceptionMessagesConstants.ORDER_NOT_FOUND);
        return orderById.get();
    }
}
