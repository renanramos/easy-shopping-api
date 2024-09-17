package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AddressMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.AddressRepository;
import br.com.renanrramos.easyshopping.model.Address;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressGatewayImplTest {

    public static final int PAGE_NUMBER = 1;
    public static final int PAGE_SIZE = 10;
    public static final String ASC = "asc";

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressGatewayImpl addressGateway;

    @Test
    void save_withAddressFrom_shouldReturnSuccessfully() {
        // Arrange
        final AddressForm addressForm = Instancio.create(AddressForm.class);
        when(addressRepository.save(any(Address.class))).thenReturn(getAddress(addressForm));
        // Act
        final AddressDTO addressDTO = addressGateway.save(addressForm);
        // Assert
        assertAddressDTO(addressDTO, addressForm);
    }

    @Test
    void findAllAddress_withParameters_shouldReturnAddressList() {
        // Arrange
        final Page<Address> addressList = getAddressList();
        when(addressRepository.findAll(any(Pageable.class)))
                .thenReturn(addressList);
        // Act
        final PageResponse<AddressDTO> addressDTOPageResponse = addressGateway.findAllAddress(PAGE_NUMBER, PAGE_SIZE, ASC);
        // Assert
        assertAddressDTOList(addressDTOPageResponse, addressList);
    }

    @Test
    void updateAddress_withAddressForm_shouldRunSuccessfully() {
        // Arrange
        final Long addressId = 1L;
        final Address currentAddress = Instancio.of(Address.class)
                .set(field("id"), addressId)
                .set(field("streetName"), "Rua lateral")
                .create();
        final AddressForm addressForm = Instancio.of(AddressForm.class)
                .set(field("streetName"), "Avenida Principal")
                .create();
        final Address address = AddressMapper.INSTANCE.mapAddressFormToAddress(addressForm);
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(currentAddress));
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        // Act
        final AddressDTO updatedAddressDTO = addressGateway.updateAddress(addressForm, addressId);
        // Assert
        assertAddressDTO(updatedAddressDTO, addressForm);
    }

    @Test
    void updateAddress_whenAddressIdNotFound_shouldThrowException() {
        // Arrange
        final AddressForm addressForm = Instancio.of(AddressForm.class).create();
        when(addressRepository.findById(any()))
                .thenThrow(new EntityNotFoundException(ExceptionMessagesConstants.ADDRESS_NOT_FOUND));
        // Act/Assert
        final EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class,
                () -> addressGateway.updateAddress(addressForm, 1L));
        assertThat(entityNotFoundException).isNotNull();
        assertThat(entityNotFoundException.getMessage()).isEqualTo(ExceptionMessagesConstants.ADDRESS_NOT_FOUND);
        verify(addressRepository, times(1)).findById(any());
        verify(addressRepository, never()).save(any());
    }

    @Test
    void removeAddress_withValidAddressId_shouldRunSuccessfully() {
        // Arrange
        final Long addressId = 1L;
        final Address address = Instancio.of(Address.class).create();
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        // Act
        addressGateway.removeAddress(addressId);
        // Assert
        verify(addressRepository).findById(addressId);
        verify(addressRepository).removeById(addressId);
    }

    @Test
    void removeAddress_whenAddressNotFound_shouldThrowException() {
        // Arrange
        final Long addressId = 1L;
        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());
        // Act
        final EntityNotFoundException entityNotFoundException =
                assertThrows(EntityNotFoundException.class, () -> addressGateway.removeAddress(addressId));
        // Assert
        verify(addressRepository).findById(addressId);
        verify(addressRepository, never()).removeById(addressId);
        assertThat(entityNotFoundException).isNotNull();
        assertThat(entityNotFoundException.getMessage()).isEqualTo(ExceptionMessagesConstants.ADDRESS_NOT_FOUND);
    }

    private void assertAddressDTOList(final PageResponse<AddressDTO> addressDTOPageResponse,
                                      final Page<Address> addressPage) {
        assertThat(addressDTOPageResponse).isNotNull();
        assertThat(addressPage).isNotNull();
        assertThat(addressDTOPageResponse.getTotalElements()).isEqualTo(addressPage.getTotalElements());
        assertThat(addressDTOPageResponse.getTotalPages()).isEqualTo(addressPage.getTotalPages());

        assertAddressList(addressDTOPageResponse.getResponseItems(), addressPage.getContent());
    }

    private static void assertAddressList(final List<AddressDTO> addressDTOPageResponse,
                                          final List<Address> addresses) {
        assertThat(addressDTOPageResponse).hasSize(addresses.size());
        int index = 0;
        for(final AddressDTO addressDTO : addressDTOPageResponse) {
            final Address address = addresses.get(index);

            assertThat(addressDTO).isNotNull();
            assertThat(addressDTO.getCep()).isEqualTo(address.getCep());
            assertThat(addressDTO.getCity()).isEqualTo(address.getCity());
            assertThat(addressDTO.getDistrict()).isEqualTo(address.getDistrict());
            assertThat(addressDTO.getNumber()).isEqualTo(address.getNumber());
            assertThat(addressDTO.getState()).isEqualTo(address.getState());
            assertThat(addressDTO.getStreetName()).isEqualTo(address.getStreetName());
            index++;
        }
    }

    private Page<Address> getAddressList() {
        final List<Address> addresses = Instancio
                .ofList(Address.class)
                .size(PAGE_SIZE)
                .create();
        return new PageImpl<>(addresses);
    }

    private static void assertAddressDTO(final AddressDTO addressDTO, final AddressForm addressForm) {
        assertThat(addressDTO).isNotNull();
        assertThat(addressDTO.getCep()).isEqualTo(addressForm.getCep());
        assertThat(addressDTO.getCity()).isEqualTo(addressForm.getCity());
        assertThat(addressDTO.getDistrict()).isEqualTo(addressForm.getDistrict());
        assertThat(addressDTO.getNumber()).isEqualTo(addressForm.getNumber());
        assertThat(addressDTO.getState()).isEqualTo(addressForm.getState());
        assertThat(addressDTO.getStreetName()).isEqualTo(addressForm.getStreetName());
    }

    private Address getAddress(final AddressForm addressForm) {
        return AddressMapper.INSTANCE.mapAddressFormToAddress(addressForm);
    }
}