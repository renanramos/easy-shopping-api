package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.CreditCard;
import br.com.renanrramos.easyshopping.core.gateway.CreditCardGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CreditCardDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CreditCardMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreditCardUseCaseImpl implements CreditCardUseCase {

    private final CreditCardGateway creditCardGateway;

    @Override
    public PageResponse<CreditCardDTO> findCreditCardByCustomerId(final Integer pageNumber, final Integer pageSize,
                                                                  final String sortBy, final String customerId) {
        final PageResponse<CreditCard> creditCardByCustomer =
                creditCardGateway.findCreditCardByCustomerId(pageNumber, pageSize, sortBy, customerId);
        return new PageResponse<>(creditCardByCustomer.getTotalElements(), creditCardByCustomer.getTotalPages(),
                CreditCardMapper.INSTANCE.mapCreditCardListToCreditCardDTOList(creditCardByCustomer.getResponseItems()));
    }

    @Override
    public CreditCardDTO saveCreditCard(final CreditCard creditCard) {
        return CreditCardMapper.INSTANCE
                .mapCreditCardToCreditCardDTO(creditCardGateway.saveCreditCard(creditCard));
    }

    @Override
    public CreditCardDTO findCreditCardById(final Long creditCardId) {
        return CreditCardMapper.INSTANCE
                .mapCreditCardToCreditCardDTO(creditCardGateway.findCreditCardById(creditCardId));
    }

    @Override
    public CreditCardDTO updateCreditCard(final CreditCard creditCard) {
        return CreditCardMapper.INSTANCE
                .mapCreditCardToCreditCardDTO(creditCardGateway.updateCreditCard(creditCard));
    }

    @Override
    public void remove(final Long creditCardId) {
        creditCardGateway.remove(creditCardId);
    }
}
