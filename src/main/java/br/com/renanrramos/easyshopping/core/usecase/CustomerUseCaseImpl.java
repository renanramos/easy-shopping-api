package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.Customer;
import br.com.renanrramos.easyshopping.core.gateway.CustomerGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CustomerForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class CustomerUseCaseImpl implements CustomerUseCase {

    private final CustomerGateway customerGateway;

    @Override
    public CustomerDTO save(final CustomerForm customerForm) {
        final Customer customer = CustomerMapper.INSTANCE.mapCustomerFormToCustomer(customerForm);
        return CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(customerGateway.save(customer));
    }

    @Override
    public PageResponse<CustomerDTO> findAllPageable(final ParametersRequest parametersRequest,
                                                     final String searchKey) {

        final Page<Customer> allCustomerPageable =
                customerGateway.findAllPageable(parametersRequest, searchKey);

        return PageResponse.buildPageResponse(allCustomerPageable,
                CustomerMapper.INSTANCE.mapCustomerListToCustomerDTOList(allCustomerPageable.getContent()));
    }

    @Override
    public CustomerDTO findById(final Long customerId) {
        final Customer customer = customerGateway.findById(customerId);
        return CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(customer);
    }

    @Override
    public CustomerDTO update(final CustomerForm customerForm, final String customerId) {
        final Customer customerUpdated =
                customerGateway.update(CustomerMapper.INSTANCE.mapCustomerFormToCustomer(customerForm), customerId);
        return CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(customerUpdated);
    }

    @Override
    public void removeCustomer(final Long customerId) {
        customerGateway.removeCustomer(customerId);
    }

    @Override
    public CustomerDTO findByTokenId(final String token) {
        return CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(customerGateway.findByToken(token));
    }
}
