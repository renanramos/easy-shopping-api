package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CustomerForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;

public interface CustomerDelegate {
    CustomerDTO save(final CustomerForm customer);

    PageResponse<CustomerDTO> findAllPageable(final ParametersRequest parametersRequest,
                                              final String searchKey);

    CustomerDTO findById(final Long customerId);

    CustomerDTO findByTokenId(final String token);

    CustomerDTO update(final CustomerForm customer, final String customerId);

    void removeCustomer(final Long customerId);

}
