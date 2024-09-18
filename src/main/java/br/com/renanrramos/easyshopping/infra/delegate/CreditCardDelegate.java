package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CreditCardDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CreditCardForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;

public interface CreditCardDelegate {
    PageResponse<CreditCardDTO> findCreditCardByCustomerId(final Integer pageNumber,
                                                           final Integer pageSize,
                                                           final String sortBy,
                                                           final String customerId);

    CreditCardDTO saveCreditCard(final CreditCardForm creditCardForm);

    CreditCardDTO findCreditCardById(final Long creditCardId);

    CreditCardDTO updateCreditCard(final CreditCardForm creditCardForm, final Long creditCardId);

    void remove(final Long creditCardId);
}
