package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.CreditCard;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import org.springframework.data.domain.Page;

public interface CreditCardGateway {

    Page<CreditCard> findCreditCardByCustomerId(final ParametersRequest parametersRequest,
                                                final String customerId);

    CreditCard saveCreditCard(final CreditCard creditCard);

    CreditCard findCreditCardById(final Long creditCardId);

    CreditCard updateCreditCard(CreditCard creditCard);

    void remove(final Long creditCardId);
}
