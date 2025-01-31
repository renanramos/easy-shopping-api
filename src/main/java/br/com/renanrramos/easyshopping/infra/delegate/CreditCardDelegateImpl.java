package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.core.domain.CreditCard;
import br.com.renanrramos.easyshopping.core.usecase.CreditCardUseCase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CreditCardDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CreditCardForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CreditCardMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreditCardDelegateImpl implements CreditCardDelegate {

    private final CreditCardUseCase creditCardUseCase;

    @Override
    public PageResponse<CreditCardDTO> findCreditCardByCustomerId(final ParametersRequest parametersRequest,
                                                                  final String customerId) {
        return creditCardUseCase.findCreditCardByCustomerId(parametersRequest, customerId);
    }

    @Override
    public CreditCardDTO saveCreditCard(final CreditCardForm creditCardForm) {
        final CreditCard creditCard = CreditCardMapper.INSTANCE.mapCreditCardFormToCreditCard(creditCardForm);
        return creditCardUseCase.saveCreditCard(creditCard);
    }

    @Override
    public CreditCardDTO findCreditCardById(final Long creditCardId) {
        return creditCardUseCase.findCreditCardById(creditCardId);
    }

    @Override
    public CreditCardDTO updateCreditCard(final CreditCardForm creditCardForm, final Long creditCardId) {
        final CreditCard creditCard = CreditCardMapper.INSTANCE.mapCreditCardFormToCreditCard(creditCardForm);
        creditCard.setId(creditCardId);
        return creditCardUseCase.updateCreditCard(creditCard);
    }

    @Override
    public void remove(final Long creditCardId) {
        creditCardUseCase.remove(creditCardId);
    }
}
