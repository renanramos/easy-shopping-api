package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CustomerForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;

public interface CustomerUseCase {
    CustomerDTO save(final CustomerForm customerForm);

    PageResponse<CustomerDTO> findAllPageable(final ParametersRequest parametersRequest,
                                              final String searchKey);

    CustomerDTO findById(final Long customerId);

    CustomerDTO update(final CustomerForm customerForm, final String customerId);

    void removeCustomer(final Long customerId);

    CustomerDTO findByTokenId(final String token);
}
