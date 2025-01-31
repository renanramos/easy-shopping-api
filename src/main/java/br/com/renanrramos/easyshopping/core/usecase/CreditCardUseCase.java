package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.CreditCard;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CreditCardDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;

public interface CreditCardUseCase {

    PageResponse<CreditCardDTO> findCreditCardByCustomerId(final ParametersRequest parametersRequest,
                                                           final String customerId);

    CreditCardDTO saveCreditCard(final CreditCard creditCard);

    CreditCardDTO findCreditCardById(final Long creditCardId);

    CreditCardDTO updateCreditCard(final CreditCard creditCard);

    void remove(final Long creditCardId);
}
