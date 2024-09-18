package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.CreditCard;
import br.com.renanrramos.easyshopping.core.gateway.CreditCardGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CreditCardMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.CreditCardRepositoy;
import br.com.renanrramos.easyshopping.model.CreditCardEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
public class CreditCardGatewayImpl implements CreditCardGateway {

    private final CreditCardRepositoy creditCardRepositoy;

    @Override
    public PageResponse<CreditCard> findCreditCardByCustomerId(final Integer pageNumber,
                                                               final Integer pageSize,
                                                               final String sortBy,
                                                               final String customerId) {
        final Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();

        final Page<CreditCardEntity> pagedCreditCard =
                creditCardRepositoy.findCreditCardByCustomerId(page, customerId);

        return new PageResponse<>(pagedCreditCard.getTotalElements(), pagedCreditCard.getTotalPages(),
                CreditCardMapper.INSTANCE.mapCreditCardEntityListToCreditCardList(pagedCreditCard.getContent()));
    }

    @Override
    public CreditCard saveCreditCard(final CreditCard creditCard) {

        final CreditCardEntity creditCardEntity =
                creditCardRepositoy.save(CreditCardMapper.INSTANCE.mapCreditCardToCreditCardEntity(creditCard));
        return CreditCardMapper.INSTANCE.mapCreditCardEntityToCreditCard(creditCardEntity);
    }

    @Override
    public CreditCard findCreditCardById(final Long creditCardId) {
        final CreditCardEntity creditCardEntity =
                creditCardRepositoy.findById(creditCardId)
                        .orElseThrow(() -> new EntityNotFoundException(ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND));
        return CreditCardMapper.INSTANCE.mapCreditCardEntityToCreditCard(creditCardEntity);
    }

    @Override
    public CreditCard updateCreditCard(final CreditCard creditCard) {
        final CreditCardEntity creditCardEntity = creditCardRepositoy
                .findById(creditCard.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException(ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND));
        CreditCardMapper.INSTANCE.mapCreditCardToUpdateCreditCardEntity(creditCardEntity, creditCard);
        return CreditCardMapper.INSTANCE.mapCreditCardEntityToCreditCard(creditCardRepositoy.save(creditCardEntity));
    }

    @Override
    public void remove(final Long creditCardId) {
        final CreditCardEntity creditCardEntity = creditCardRepositoy.findById(creditCardId)
                .orElseThrow(() ->
                        new EntityNotFoundException((ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND)));
        creditCardRepositoy.removeById(creditCardEntity.getId());
    }
}
