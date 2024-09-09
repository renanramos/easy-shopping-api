package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.OrderItem;
import br.com.renanrramos.easyshopping.model.dto.OrderItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
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
}

