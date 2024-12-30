package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.Address;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.AddressEntity;
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

    @Named("mapAddressToAddressEntity")
    AddressEntity mapAddressToAddressEntity(final Address address);

    @Named("mapAddressEntityToAddressDTO")
    AddressDTO mapAddressEntityToAddressDTO(final AddressEntity newAddressEntity);

    @Named("mapAddressEntityListToAddressList")
    default List<Address> mapAddressEntityListToAddressList(final List<AddressEntity> addressEntityList) {
        return addressEntityList
                .stream()
                .map(this::mapAddressEntityToAddress)
                .collect(Collectors.toList());
    }

    @Named("mapAddressFormToAddressEntity")
    AddressEntity mapAddressFormToAddressEntity(final AddressForm addressForm);

    @Named("mapAddressEntityToAddress")
    Address mapAddressEntityToAddress(final AddressEntity addressEntity);

    @Named("mapAddressListToAddressDTOList")
    default List<AddressDTO> mapAddressListToAddressDTOList(final List<Address> addresses) {
        return addresses
                .stream()
                .map(this::mapAddressToAddressDTO)
                .collect(Collectors.toList());
    }
}
