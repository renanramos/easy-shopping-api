package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.Customer;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;

public interface CustomerGateway {

    Customer save(final Customer customer);

    PageResponse<Customer> findAllPageable(final Integer pageNumber,
                                           final Integer pageSize,
                                           final String sortBy,
                                           final String searchKey);

    Customer findById(final Long customerId);

    Customer update(final Customer customer, final String customerId);

    void removeCustomer(final Long customerId);
}
