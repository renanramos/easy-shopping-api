package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.CreditCard;
import br.com.renanrramos.easyshopping.core.gateway.CreditCardGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.CreditCardEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CreditCardMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.CreditCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CreditCardGatewayImpl implements CreditCardGateway {

    private final CreditCardRepository creditCardRepository;

    @Override
    public Page<CreditCard> findCreditCardByCustomerId(final ParametersRequest parametersRequest,
                                                       final String customerId) {
        final Pageable page = new PageableFactory()
                .buildPageable(parametersRequest);
        final Page<CreditCardEntity> creditCardByCustomerId =
                creditCardRepository.findCreditCardByCustomerId(page, customerId);
        final List<CreditCard> creditCards =
                CreditCardMapper.INSTANCE.mapCreditCardEntityListToCreditCardList(creditCardByCustomerId.getContent());
        return new PageImpl<>(creditCards,
                creditCardByCustomerId.getPageable(),
                creditCardByCustomerId.getTotalElements());
    }

    @Override
    public CreditCard saveCreditCard(final CreditCard creditCard) {
        final CreditCardEntity creditCardEntity = CreditCardMapper.INSTANCE.mapCreditCardToCreditCardEntity(creditCard);
        return CreditCardMapper.INSTANCE.mapCreditCardEntityToCreditCard(creditCardRepository.save(creditCardEntity));
    }

    @Override
    public CreditCard findCreditCardById(final Long creditCardId) {
        final CreditCardEntity creditCardEntity = getCreditCardByIdOrThrow(creditCardId);
        return CreditCardMapper.INSTANCE.mapCreditCardEntityToCreditCard(creditCardEntity);
    }

    @Override
    public CreditCard updateCreditCard(final CreditCard creditCard) {
        final CreditCardEntity creditCardEntity = getCreditCardByIdOrThrow(creditCard.getId());
        CreditCardMapper.INSTANCE.mapCreditCardToUpdateCreditCardEntity(creditCardEntity, creditCard);
        return CreditCardMapper.INSTANCE.mapCreditCardEntityToCreditCard(creditCardRepository.save(creditCardEntity));
    }

    @Override
    public void remove(final Long creditCardId) {
        final CreditCardEntity creditCardEntity = getCreditCardByIdOrThrow(creditCardId);
        creditCardRepository.removeById(creditCardEntity.getId());
    }

    private CreditCardEntity getCreditCardByIdOrThrow(Long creditCardId) {
        final Optional<CreditCardEntity> creditCardEntity = creditCardRepository.findById(creditCardId);
        if (creditCardEntity.isEmpty())
            throw new EntityNotFoundException(ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND);
        return creditCardEntity.get();
    }
}
