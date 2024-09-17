package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Named("mapAddressToAddressDTO")
    AddressDTO mapAddressToAddressDTO(final Address address);

    @Named("mapAddressListTOAddressDTOList")
    default List<AddressDTO> mapAddressListTOAddressDTOList(final List<Address> addressList) {
        return addressList
                .stream()
                .map(AddressMapper.INSTANCE::mapAddressToAddressDTO)
                .collect(Collectors.toList());
    }

    @Named("mapAddressFormToAddress")
    Address mapAddressFormToAddress(final AddressForm addressForm);

    @Named("mapAddressFormToUpdateAddress")
    void mapAddressFormToUpdateAddress(@MappingTarget Address address, final AddressForm addressForm);
}
