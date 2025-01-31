package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.core.usecase.CustomerUseCase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CustomerForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerDelegateImpl implements CustomerDelegate {

    private final CustomerUseCase customerUseCase;

    @Override
    public CustomerDTO save(final CustomerForm customer) {
        return customerUseCase.save(customer);
    }

    @Override
    public PageResponse<CustomerDTO> findAllPageable(final ParametersRequest parametersRequest,
                                                     final String searchKey) {
        return customerUseCase.findAllPageable(parametersRequest, searchKey);
    }

    @Override
    public CustomerDTO findById(final Long customerId) {
        return customerUseCase.findById(customerId);
    }

    @Override
    public CustomerDTO findByTokenId(String token) {
        return customerUseCase.findByTokenId(token);
    }

    @Override
    public CustomerDTO update(final CustomerForm customer, final String customerId) {
        return customerUseCase.update(customer, customerId);
    }

    @Override
    public void removeCustomer(final Long customerId) {
        customerUseCase.removeCustomer(customerId);
    }
}
