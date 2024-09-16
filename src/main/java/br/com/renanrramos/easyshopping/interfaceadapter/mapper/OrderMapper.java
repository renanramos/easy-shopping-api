package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.infra.controller.rest.dto.OrderDTO;
import br.com.renanrramos.easyshopping.infra.controller.rest.form.OrderForm;
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
    void mapOrderFormToUpdateOrder(@MappingTarget Order order, final OrderForm orderForm);
}
