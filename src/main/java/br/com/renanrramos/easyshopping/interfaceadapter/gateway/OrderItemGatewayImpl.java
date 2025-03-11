package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.core.domain.OrderItem;
import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import br.com.renanrramos.easyshopping.core.gateway.OrderItemGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.OrderItemEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.OrderItemMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.OrderItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class OrderItemGatewayImpl implements OrderItemGateway {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem save(final OrderItem orderItem) {
        final OrderItemEntity orderItemEntity = orderItemRepository.save(
                OrderItemMapper.INSTANCE.mapOrderItemToOrderItemEntity(orderItem));
        return OrderItemMapper.INSTANCE.mapOrderItemEntityToOrderItem(orderItemEntity);
    }

    @Override
    public Page<OrderItem> findAll(final ParametersRequest parametersRequest,
                                   final String searchKey) {

        final Pageable pageable = new PageableFactory()
                .buildPageable(parametersRequest);

        Page<OrderItemEntity> orderItemEntityPage = orderItemRepository.findAll(pageable);

        return new PageImpl<>(OrderItemMapper
                .INSTANCE.mapOrderItemEntityListToOrderItemList(orderItemEntityPage.getContent()));
    }

    @Override
    public OrderItem findById(final Long orderItemId) {
        return OrderItemMapper.INSTANCE
                .mapOrderItemEntityToOrderItem(getOrderItemEntityByIdOrThrow(orderItemId));
    }

    @Override
    public OrderItem update(final OrderItem orderItem, final Long orderItemId) {
        final OrderItemEntity orderItemEntity = getOrderItemEntityByIdOrThrow(orderItemId);
        OrderItemMapper.INSTANCE.mapOrderItemToUpdateOrderItemEntity(orderItemEntity, orderItem);
        return OrderItemMapper.INSTANCE
                .mapOrderItemEntityToOrderItem(orderItemRepository.save(orderItemEntity));
    }

    @Override
    public void remove(final Long orderItemId) {
        final OrderItemEntity orderItemEntityById = getOrderItemEntityByIdOrThrow(orderItemId);
        orderItemRepository.delete(orderItemEntityById);
    }

    @Override
    public Page<OrderItem> findOrderItemByOrderId(final Long orderId) {
        final List<OrderItemEntity> orderItemByOrderId =
                orderItemRepository.getOrderItemByOrderId(orderId);
        return new PageImpl<>(OrderItemMapper.INSTANCE.mapOrderItemEntityListToOrderItemList(orderItemByOrderId));
    }

    @Override
    public Page<OrderItem> getOrderItemStatistic() {
        final List<OrderItemEntity> orderItemStatistic = orderItemRepository.getOrderItemStatistic();
        return new PageImpl<>(OrderItemMapper.INSTANCE.mapOrderItemEntityListToOrderItemList(orderItemStatistic));
    }

    private OrderItemEntity getOrderItemEntityByIdOrThrow(Long orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() ->
                        new EntityNotFoundException(ExceptionConstantMessages.ORDER_ITEM_NOT_FOUND));
    }
}
