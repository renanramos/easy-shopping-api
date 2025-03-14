package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.Customer;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CustomerForm;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.CustomerEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Named("mapCustomerToCustomerDTO")
    CustomerDTO mapCustomerToCustomerDTO(final CustomerEntity customer);

    @Named("mapCustomerToCustomerDTO")
    CustomerDTO mapCustomerToCustomerDTO(final Customer customer);

    @Named("mapCustomerListToCustomerDTOList")
    default List<CustomerDTO> mapCustomerListToCustomerDTOList(final List<Customer> customerList) {
        return customerList
                .stream()
                .map(CustomerMapper.INSTANCE::mapCustomerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Named("mapCustomerListToCustomerList")
    default List<Customer> mapCustomerEntityListToCustomerList(final List<CustomerEntity> customerEntityList) {
        return customerEntityList
                .stream()
                .map(CustomerMapper.INSTANCE::mapCustomerEntityToCustomer)
                .collect(Collectors.toList());
    }

    @Named("mapCustomerFormToCustomer")
    Customer mapCustomerFormToCustomer(final CustomerForm customerForm);

    @Named("mapCustomerFormToUpdateCustomer")
    void mapCustomerFormToUpdateCustomer(@MappingTarget Customer customer, final CustomerForm customerForm);

    @Named("mapCustomerToUpdateCustomerEntity")
    @Mapping(target = "sync", defaultValue = "true")
    void mapCustomerToUpdateCustomerEntity(@MappingTarget CustomerEntity customerEntity, final Customer customer);

    @Named("mapCustomerToCustomerEntity")
    CustomerEntity mapCustomerToCustomerEntity(final Customer customer);

    @Named("mapCustomerEntityToCustomer")
    Customer mapCustomerEntityToCustomer(final CustomerEntity customerEntity);
}
