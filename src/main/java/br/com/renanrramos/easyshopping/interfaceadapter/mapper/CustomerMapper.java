package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
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
}
