package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.model.dto.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
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
}
