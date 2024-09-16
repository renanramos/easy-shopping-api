package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.OrderItem;
import br.com.renanrramos.easyshopping.infra.controller.rest.dto.PurchaseStatisticDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PurchaseStatisticMapper {

    PurchaseStatisticMapper INSTANCE = Mappers.getMapper(PurchaseStatisticMapper.class);


    @Named("mapOrderItemToPurchaseStatisticDTO")
    @Mapping(target = "purchase", source = "order.purchase")
    PurchaseStatisticDTO mapOrderItemToPurchaseStatisticDTO(final OrderItem orderItem);

    @Named("mapOrderItemListToPurchaseStatisticDTOList")
    default List<PurchaseStatisticDTO> mapOrderItemListToPurchaseStatisticDTOList(final List<OrderItem> orderItemList) {
        return orderItemList
                .stream()
                .map(PurchaseStatisticMapper.INSTANCE::mapOrderItemToPurchaseStatisticDTO)
                .collect(Collectors.toList());
    }
}
