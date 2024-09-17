package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.gateway.AddressGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AddressMapper;
import br.com.renanrramos.easyshopping.model.Address;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressUseCaseImplTest {

    @Mock
    private AddressGateway addressGateway;

    @InjectMocks
    private AddressUseCaseImpl addressUseCase;

    @Test
    void save_withAddressForm_shouldReturnAddressDTO() {
        // Arrange
        final AddressForm addressForm = getAddressForm();
        final AddressDTO addressDTO = convertAddressFormToAddressDTO(addressForm);
        when(addressGateway.save(any(AddressForm.class))).thenReturn(addressDTO);
        // Act
        final AddressDTO addressDTOResponse = addressUseCase.save(addressForm);
        // Assert
        assertThat(addressDTOResponse).isNotNull();
        assertAddressDTO(addressDTOResponse, addressDTO);
    }

    @Test
    void findAllAddress_withParameters_shouldReturnAddressDTOPageResponse() {
        // Arrange
        final AddressForm addressForm = getAddressForm();
        final AddressDTO addressDTO = convertAddressFormToAddressDTO(addressForm);
        final PageResponse<AddressDTO> pageResponse = new PageResponse<>(3L, 1,
                Collections.nCopies(3, addressDTO));
        when(addressGateway.findAllAddress(any(), any(), any())).thenReturn(pageResponse);
        // Act
        final PageResponse<AddressDTO> addressDTOPageResponse =
                addressUseCase.findAllAddress(1, 10, "asc");
        // Assert
        assertThat(addressDTOPageResponse).isNotNull();
        assertThat(addressDTOPageResponse.getTotalPages()).isEqualTo(1L);
        assertThat(addressDTOPageResponse.getTotalElements()).isEqualTo(3);
    }

    private void assertAddressDTO(final AddressDTO addressDTOResponse,
                                  final AddressDTO addressDTO) {
        assertThat(addressDTOResponse.getStreetName()).isEqualTo(addressDTO.getStreetName());
        assertThat(addressDTOResponse.getId()).isEqualTo(addressDTO.getId());
        assertThat(addressDTOResponse.getCity()).isEqualTo(addressDTO.getCity());
        assertThat(addressDTOResponse.getCep()).isEqualTo(addressDTO.getCep());
        assertThat(addressDTOResponse.getNumber()).isEqualTo(addressDTO.getNumber());
        assertThat(addressDTOResponse.getState()).isEqualTo(addressDTO.getState());
        assertThat(addressDTOResponse.getDistrict()).isEqualTo(addressDTO.getDistrict());
        assertThat(addressDTOResponse.getCustomerId()).isEqualTo(addressDTO.getCustomerId());
    }

    private AddressDTO convertAddressFormToAddressDTO(final AddressForm addressForm) {
        final Address address = AddressMapper.INSTANCE.mapAddressFormToAddress(addressForm);
        return AddressMapper.INSTANCE.mapAddressToAddressDTO(address);
    }

    private AddressForm getAddressForm() {
        return Instancio.create(AddressForm.class);
    }

    @Test
    void findAllAddress() {
    }
}