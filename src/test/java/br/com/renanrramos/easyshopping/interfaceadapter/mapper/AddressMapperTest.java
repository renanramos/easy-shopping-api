package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.Address;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static br.com.renanrramos.easyshopping.interfaceadapter.mapper.util.TestUtils.*;
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

    @Test
    void mapAddressFormToAddress_withAddressForm_shouldMapToAddress() {
        final AddressForm addressForm = Instancio.of(AddressForm.class).create();

        final Address address = AddressMapper.INSTANCE.mapAddressFormToAddress(addressForm);

        assertAddress(address, addressForm);
    }

    @Test
    void mapAddressFormToUpdateAddress_whenAddressFormUpdateOperation_shouldMapToAddressOnlyDifferentFields() {
        // Arrange
        final AddressForm addressFormUpdate = Instancio.of(AddressForm.class)
                .create();
        final Address address = AddressMapper.INSTANCE.mapAddressFormToAddress(addressFormUpdate);
        addressFormUpdate.setCep("37536152");
        addressFormUpdate.setNumber(387L);

        // Act
        AddressMapper.INSTANCE
                .mapAddressFormToUpdateAddress(address, addressFormUpdate);

        // Assert
        assertThat(address).isNotNull();
        assertThat(address.getState()).isEqualTo(addressFormUpdate.getState());
        assertThat(address.getCep()).isEqualTo(addressFormUpdate.getCep());
        assertThat(address.getNumber()).isEqualTo(addressFormUpdate.getNumber());
    }
}