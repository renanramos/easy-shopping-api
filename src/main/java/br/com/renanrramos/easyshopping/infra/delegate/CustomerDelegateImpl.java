package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.core.domain.Customer;
import br.com.renanrramos.easyshopping.core.usecase.CustomerUseCase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CustomerForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CustomerMapper;
import br.com.renanrramos.easyshopping.service.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerDelegateImpl implements CustomerDelegate{

    private final CustomerUseCase customerUseCase;

    @Override
    public CustomerDTO save(final CustomerForm customer) {
        return customerUseCase.save(CustomerMapper
                .INSTANCE.mapCustomerFormToCustomer(customer));
    }

    @Override
    public PageResponse<CustomerDTO> findAllPageable(final Integer pageNumber,
                                                     final Integer pageSize,
                                                     final String sortBy,
                                                     final String searchKey) {
        return customerUseCase.findAllPageable(pageNumber, pageSize, sortBy, searchKey);
    }

    @Override
    public CustomerDTO findById(final Long customerId) {
        return customerUseCase.findById(customerId);
    }

    @Override
    public CustomerDTO update(final CustomerForm customer, final String customerId) {
        return customerUseCase.update(CustomerMapper
                .INSTANCE.mapCustomerFormToCustomer(customer), customerId);
    }

    @Override
    public void removeCustomer(final Long customerId) {
        customerUseCase.removeCustomer(customerId);
    }
}
