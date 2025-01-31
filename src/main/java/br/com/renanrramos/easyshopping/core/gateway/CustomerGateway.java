package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.Customer;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import org.springframework.data.domain.Page;

public interface CustomerGateway {

    Customer save(final Customer customer);

    Page<Customer> findAllPageable(final ParametersRequest parametersRequest,
                                   final String searchKey);

    Customer findById(final Long customerId);

    Customer findByToken(final String token);

    Customer update(final Customer customer, final String customerId);

    void removeCustomer(final Long customerId);

}
