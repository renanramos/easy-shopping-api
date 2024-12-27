package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.Customer;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;

public interface CustomerUseCase {
    CustomerDTO save(final Customer customer);

    PageResponse<CustomerDTO> findAllPageable(final Integer pageNumber,
                                              final Integer pageSize,
                                              final String sortBy,
                                              final String searchKey);

    CustomerDTO findById(final Long customerId);

    CustomerDTO update(final Customer customer, final String customerId);

    void removeCustomer(final Long customerId);
}
