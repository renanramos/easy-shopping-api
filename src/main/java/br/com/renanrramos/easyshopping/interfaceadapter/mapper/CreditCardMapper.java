package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.CreditCard;
import br.com.renanrramos.easyshopping.model.dto.CreditCardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreditCardMapper {

    CreditCardMapper INSTANCE = Mappers.getMapper(CreditCardMapper.class);

    @Named("mapCreditCardToCreditCardDTO")
    CreditCardDTO mapCreditCardToCreditCardDTO(final CreditCard creditCard);

    @Named("mapCreditCardListToCreditCardDTOList")
    default List<CreditCardDTO> mapCreditCardListToCreditCardDTOList(final List<CreditCard> creditCardList) {
        return creditCardList.stream()
                .map(CreditCardMapper.INSTANCE::mapCreditCardToCreditCardDTO)
                .collect(Collectors.toList());
    }
}
