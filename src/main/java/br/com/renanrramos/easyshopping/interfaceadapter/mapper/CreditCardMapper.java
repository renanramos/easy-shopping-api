package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.CreditCard;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.CreditCardEntity;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CreditCardDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CreditCardForm;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
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

    @Named("mapCreditCardFormToCreditCard")
    CreditCard mapCreditCardFormToCreditCard(final CreditCardForm creditCardForm);

    @Named("mapCreditCardFormToUpdateCreditCard")
    void mapCreditCardFormToUpdateCreditCard(@MappingTarget CreditCard creditCard, final CreditCardForm creditCardForm);

    @Named("mapCreditCardEntityToCreditCard")
    CreditCard mapCreditCardEntityToCreditCard(final CreditCardEntity creditCardEntity);

    @Named("mapCreditCardEntityListToCreditCardList")
    default List<CreditCard> mapCreditCardEntityListToCreditCardList(final List<CreditCardEntity> entities) {
        return entities
                .stream()
                .map(CreditCardMapper.INSTANCE::mapCreditCardEntityToCreditCard)
                .collect(Collectors.toList());
    }

    @Named("mapCreditCardToCreditCardEntity")
    CreditCardEntity mapCreditCardToCreditCardEntity(final CreditCard creditCard);

    @Named("mapCreditCardToUpdateCreditCardEntity")
    void mapCreditCardToUpdateCreditCardEntity(@MappingTarget CreditCardEntity creditCardEntity, final CreditCard creditCard);
}
