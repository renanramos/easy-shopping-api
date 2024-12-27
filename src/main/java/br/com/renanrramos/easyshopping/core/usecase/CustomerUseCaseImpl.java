package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.Customer;
import br.com.renanrramos.easyshopping.core.gateway.CustomerGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerUseCaseImpl implements CustomerUseCase{

    private final CustomerGateway customerGateway;

    @Override
    public CustomerDTO save(final Customer customer) {
        return CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(customerGateway.save(customer));
    }

    @Override
    public PageResponse<CustomerDTO> findAllPageable(final Integer pageNumber,
                                                     final Integer pageSize,
                                                     final String sortBy,
                                                     final String searchKey) {

        final PageResponse<Customer> allCustomerPageable =
                customerGateway.findAllPageable(pageNumber, pageSize, sortBy, searchKey);

        return new PageResponse<>(allCustomerPageable.getTotalElements(), allCustomerPageable.getTotalPages(),
                CustomerMapper.INSTANCE.mapCustomerListToCustomerDTOList(allCustomerPageable.getResponseItems()));
    }

    @Override
    public CustomerDTO findById(final Long customerId) {
        final Customer customer = customerGateway.findById(customerId);
        return CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(customer);
    }

    @Override
    public CustomerDTO update(final Customer customer, final String customerId) {
        final Customer customerUpdated = customerGateway.update(customer, customerId);
        return CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(customerUpdated);
    }

    @Override
    public void removeCustomer(final Long customerId) {
        customerGateway.removeCustomer(customerId);
    }
}
