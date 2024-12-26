package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.interfaceadapter.domain.Purchase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.PurchaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PurchaseMapper {

    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    @Named("mapPurchaseToPurchaseDTO")
    @Mapping(target = "addressId", source = "address.id")
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "creditCardId", source = "creditCard.id")
    @Mapping(target = "date", source = "purchaseDate")
    PurchaseDTO mapPurchaseToPurchaseDTO(final Purchase purchase);

    @Named("mapPurchaseListToPurchaseDTOList")
    default List<PurchaseDTO> mapPurchaseListToPurchaseDTOList(List<Purchase> purchaseList) {
        return purchaseList
                .stream()
                .map(PurchaseMapper.INSTANCE::mapPurchaseToPurchaseDTO)
                .collect(Collectors.toList());
    }
}
