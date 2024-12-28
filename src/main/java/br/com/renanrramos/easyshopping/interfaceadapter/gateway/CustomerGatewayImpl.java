package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.Customer;
import br.com.renanrramos.easyshopping.core.gateway.CustomerGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.CustomerEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CustomerMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
public class CustomerGatewayImpl implements CustomerGateway {

    private final CustomerRepository customerRepository;

    @Override
    public Customer save(final Customer customer) {
        final CustomerEntity customerEntity =
                customerRepository.save(CustomerMapper.INSTANCE.mapCustomerToCustomerEntity(customer));
        return CustomerMapper.INSTANCE.mapCustomerEntityToCustomer(customerEntity);
    }

    @Override
    public PageResponse<Customer> findAllPageable(final Integer pageNumber, final Integer pageSize,
                                                  final String sortBy, final String searchKey) {
        final Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();

        final Page<CustomerEntity> customerEntityPage = StringUtils.isEmpty(searchKey) ?
                customerRepository.findAll(page) :
                customerRepository.getCustomerByNameCPFOrEmail(page, searchKey);

        return PageResponse.buildPageResponse(customerEntityPage,
                CustomerMapper.INSTANCE.mapCustomerEntityListToCustomerList(customerEntityPage.getContent()));
    }

    @Override
    public Customer findById(final Long customerId) {
        final CustomerEntity customerById = customerRepository.findById(customerId)
                .orElse(null);
        final CustomerEntity customer = validateCustomer(customerById);
        return CustomerMapper.INSTANCE.mapCustomerEntityToCustomer(customer);
    }

    @Override
    public Customer update(final Customer customer, final String customerId) {
        if (CollectionUtils.isEmpty(customerRepository.findCustomerByCpf(customer.getCpf()))) {
            throw new EntityNotFoundException(ExceptionMessagesConstants.CPF_ALREADY_EXIST);
        }

        final CustomerEntity customerEntity = validateCustomer(customerRepository.findCustomerByTokenId(customerId));
        CustomerMapper.INSTANCE.mapCustomerToUpdateCustomerEntity(customerEntity, customer);
        // TODO: validar a necessidade destes campos serem configurados individualmente.
        customerEntity.setId(customerEntity.getId());
        customerEntity.setTokenId(customerId);
        customerEntity.setSync(true);

        return CustomerMapper.INSTANCE.mapCustomerEntityToCustomer(customerRepository.save(customerEntity));
    }

    @Override
    public void removeCustomer(final Long customerId) {

        validateCustomer(customerRepository
                .findById(customerId)
                .orElse(null));

        customerRepository.deleteById(customerId);
    }

    private CustomerEntity validateCustomer(final CustomerEntity customerEntity) {
        if (customerEntity == null) {
            throw new EntityNotFoundException(ExceptionMessagesConstants.CUSTOMER_NOT_FOUND);
        }
        return customerEntity;
    }
}
