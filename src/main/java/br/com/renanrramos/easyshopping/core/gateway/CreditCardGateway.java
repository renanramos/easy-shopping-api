package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.CreditCard;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;

public interface CreditCardGateway {

    PageResponse<CreditCard> findCreditCardByCustomerId(final Integer pageNumber,
                                                        final Integer pageSize,
                                                        final String sortBy,
                                                        final String customerId);

    CreditCard saveCreditCard(final CreditCard creditCard);

    CreditCard findCreditCardById(final Long creditCardId);

    CreditCard updateCreditCard(CreditCard creditCard);

    void remove(final Long creditCardId);
}
