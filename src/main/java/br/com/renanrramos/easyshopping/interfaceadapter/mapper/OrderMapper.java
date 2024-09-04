package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.model.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
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
}
