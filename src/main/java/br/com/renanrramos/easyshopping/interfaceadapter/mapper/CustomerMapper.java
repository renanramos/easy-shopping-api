package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.interfaceadapter.domain.Customer;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CustomerForm;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Named("mapCustomerToCustomerDTO")
    CustomerDTO mapCustomerToCustomerDTO(final Customer customer);

    @Named("mapCustomerListToCustomerDTOList")
    default List<CustomerDTO> mapCustomerListToCustomerDTOList(final List<Customer> customerList) {
        return customerList
                .stream()
                .map(CustomerMapper.INSTANCE::mapCustomerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Named("mapCustomerFormToCustomer")
    Customer mapCustomerFormToCustomer(final CustomerForm customerForm);

    @Named("mapCustomerFormToUpdateCustomer")
    void mapCustomerFormToUpdateCustomer(@MappingTarget Customer customer, final CustomerForm customerForm);
}
