package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.model.dto.AddressDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AddressMapperTest {

    @Test
    void mapAddressToAddressDTO_withAddress_shouldMapToAddressDTO() {
        final Address address = Instancio.create(Address.class);

        final AddressDTO addressDTO = AddressMapper.INSTANCE.mapAddressToAddressDTO(address);

        assertAddressDTO(addressDTO, address);
    }

    @Test
    void mapAddressListTOAddressDTOList_withAddressList_shouldMapToAddressDTOList() {

        final List<Address> addressList = Instancio.ofList(Address.class)
                .size(10)
                .create();

        final List<AddressDTO> addressDTOS = AddressMapper.INSTANCE.mapAddressListTOAddressDTOList(addressList);

        assertAddressDTOList(addressDTOS, addressList);
    }

    private void assertAddressDTOList(final List<AddressDTO> addressDTOS, final List<Address> addressList) {

        assertThat(addressDTOS).hasSize(addressList.size());
        int index = 0;
        for (final AddressDTO addressDTO: addressDTOS) {
            assertAddressDTO(addressDTO, addressList.get(index));
            index++;
        }
    }

    private static void assertAddressDTO(final AddressDTO addressDTO, final Address address) {
        assertThat(addressDTO).isNotNull();
        assertThat(addressDTO.getId()).isEqualTo(address.getId());
        assertThat(addressDTO.getNumber()).isEqualTo(address.getNumber());
        assertThat(addressDTO.getDistrict()).isEqualTo(address.getDistrict());
        assertThat(addressDTO.getCity()).isEqualTo(address.getCity());
        assertThat(addressDTO.getCep()).isEqualTo(address.getCep());
        assertThat(addressDTO.getCustomerId()).isEqualTo(address.getCustomerId());
        assertThat(addressDTO.getState()).isEqualTo(address.getState());
        assertThat(addressDTO.getStreetName()).isEqualTo(address.getStreetName());
    }
}