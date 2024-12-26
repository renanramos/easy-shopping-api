package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.Address;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AddressMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.AddressRepository;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.AddressEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static br.com.renanrramos.easyshopping.interfaceadapter.mapper.util.TestUtils.assertAddress;
import static br.com.renanrramos.easyshopping.interfaceadapter.mapper.util.TestUtils.assertAddressDTOList;
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
        final AddressEntity addressEntity = Instancio.create(AddressEntity.class);
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);
        final Address address = AddressMapper.INSTANCE.mapAddressEntityToAddress(addressEntity);
        // Act
        final Address response = addressGateway.save(address);
        // Assert
        assertAddress(response, addressEntity);
    }

    @Test
    void findAllAddress_withParameters_shouldReturnAddressList() {
        // Arrange
        final Page<AddressEntity> addressList = getAddressList();
        when(addressRepository.findAll(any(Pageable.class)))
                .thenReturn(addressList);
        // Act
        final PageResponse<Address> addressPageResponse = addressGateway.findAllAddress(PAGE_NUMBER, PAGE_SIZE, ASC);
        // Assert
        assertAddressDTOList(addressPageResponse, addressList);
    }

    @Test
    void updateAddress_withAddressForm_shouldRunSuccessfully() {
        // Arrange
        final Long addressId = 1L;
        final AddressEntity currentAddress = Instancio.of(AddressEntity.class)
                .set(field("id"), addressId)
                .set(field("streetName"), "Rua lateral")
                .create();
        final Address address = Instancio.of(Address.class)
                .set(field("streetName"), "Avenida Principal")
                .create();
        final AddressEntity addressEntity = AddressMapper.INSTANCE.mapAddressToAddressEntity(address);
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(currentAddress));
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);
        // Act
        final Address updatedAddress = addressGateway.updateAddress(address, addressId);
        // Assert
        assertAddress(updatedAddress, addressEntity);
    }

    @Test
    void updateAddress_whenAddressIdNotFound_shouldThrowException() {
        // Arrange
        final Address address = Instancio.of(Address.class).create();
        when(addressRepository.findById(any()))
                .thenThrow(new EntityNotFoundException(ExceptionMessagesConstants.ADDRESS_NOT_FOUND));
        // Act/Assert
        final EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class,
                () -> addressGateway.updateAddress(address, 1L));
        assertThat(entityNotFoundException).isNotNull();
        assertThat(entityNotFoundException.getMessage()).isEqualTo(ExceptionMessagesConstants.ADDRESS_NOT_FOUND);
        verify(addressRepository, times(1)).findById(any());
        verify(addressRepository, never()).save(any());
    }

    @Test
    void removeAddress_withValidAddressId_shouldRunSuccessfully() {
        // Arrange
        final Long addressId = 1L;
        final AddressEntity addressEntity = Instancio.of(AddressEntity.class).create();
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(addressEntity));
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

    private Page<AddressEntity> getAddressList() {
        final List<AddressEntity> addresses = Instancio
                .ofList(AddressEntity.class)
                .size(PAGE_SIZE)
                .create();
        return new PageImpl<>(addresses);
    }
}