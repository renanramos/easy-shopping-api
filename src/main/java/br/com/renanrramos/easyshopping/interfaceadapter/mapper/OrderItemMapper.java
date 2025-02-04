package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.OrderItem;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.OrderItemDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.OrderItemForm;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.OrderItemEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Named("mapOrderItemToOrderItemDTO")
    @Mapping(target = "orderId", source = "order.id")
    OrderItemDTO mapOrderItemToOrderItemDTO(final OrderItem orderItem);

    @Named("mapOrderItemListToOrderItemDTOList")
    default List<OrderItemDTO> mapOrderItemListToOrderItemDTOList(final List<OrderItem> orderItemList) {
        return orderItemList
                .stream()
                .map(OrderItemMapper.INSTANCE::mapOrderItemToOrderItemDTO)
                .collect(Collectors.toList());
    }

    @Named("mapOrderItemFormToOrderItem")
    @Mapping(target = "order.id", source = "orderId")
    OrderItem mapOrderItemFormToOrderItem(final OrderItemForm orderItemForm);

    @Named("mapOrderItemToOrderItemEntity")
    OrderItemEntity mapOrderItemToOrderItemEntity(final OrderItem orderItem);

    @Named("mapOrderItemEntityToOrderItem")
    OrderItem mapOrderItemEntityToOrderItem(final OrderItemEntity orderItemEntity);

    @Named("mapOrderItemEntityListToOrderItemList")
    default List<OrderItem> mapOrderItemEntityListToOrderItemList(final List<OrderItemEntity> orderItemEntities) {
        return orderItemEntities
                .stream()
                .map(OrderItemMapper.INSTANCE::mapOrderItemEntityToOrderItem)
                .collect(Collectors.toList());
    }

    @Named("mapOrderItemToUpdateOrderItemEntity")
    void mapOrderItemToUpdateOrderItemEntity(@MappingTarget OrderItemEntity orderItemEntity, final OrderItem orderItem);
}

