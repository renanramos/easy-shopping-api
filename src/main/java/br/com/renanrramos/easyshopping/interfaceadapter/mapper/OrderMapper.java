package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.Order;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.OrderDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.OrderForm;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.OrderEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Named("mapOrderToOrderDTO")
    OrderDTO mapOrderToOrderDTO(final Order order);

    @Named("mapOrderListToOrderDTOList")
    default List<OrderDTO> mapOrderListToOrderDTOList(final List<Order> orders) {
        return orders.stream()
                .map(OrderMapper.INSTANCE::mapOrderToOrderDTO)
                .collect(Collectors.toList());
    }

    @Named("mapOrderFormToOrder")
    Order mapOrderFormToOrder(final OrderForm orderForm);

    @Named("mapOrderFormToUpdateOrder")
    void mapOrderFormToUpdateOrder(@MappingTarget OrderEntity order, final OrderForm orderForm);

    @Named("mapOrderToOrderEntity")
    OrderEntity mapOrderToOrderEntity(final Order order);

    @Named("mapOrderEntityToOrder")
    Order mapOrderEntityToOrder(final OrderEntity orderEntity);

    @Named("mapOrderEntityListToOrderList")
    default List<Order> mapOrderEntityListToOrderList(final List<OrderEntity> orderEntities) {
        return orderEntities
                .stream()
                .map(this::mapOrderEntityToOrder)
                .collect(Collectors.toList());
    }

    @Named("mapOrderToUpdateOrder")
    void mapOrderToUpdateOrder(@MappingTarget OrderEntity orderEntity, final Order order);
}
