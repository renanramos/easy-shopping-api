package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.delegate.AddressDelegate;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AddressMapper;
import br.com.renanrramos.easyshopping.model.Address;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private AddressDelegate addressDelegate;
    @InjectMocks
    private AddressController addressController;

    @Test
    public void shouldCreateNewAddressSuccessfully() {
        // Arrange
        final AddressForm addressForm = Instancio.of(AddressForm.class).create();
        when(addressDelegate.saveAddress(addressForm))
                .thenReturn(buildAddressDTO(addressForm));
        // Act
        final ResponseEntity<AddressDTO> responseEntity = addressController.saveAddress(addressForm);
        // Assert
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(addressDelegate, times(1)).saveAddress(any(AddressForm.class));
    }

    @Test
	public void shouldReturnAListOfAddresses() throws Exception {
		// Arrange
        final List<Address> addresses = Instancio.ofList(Address.class).size(3).create();
        final List<AddressDTO> addressDTOS = AddressMapper.INSTANCE.mapAddressListTOAddressDTOList(addresses);
        final PageResponse<AddressDTO> pageResponse = new PageResponse<>(3L, 1, addressDTOS);
		when(addressDelegate.findAddresses(1, 1, "asc", ""))
                .thenReturn(pageResponse);
        // Act
        final ResponseEntity<PageResponse<AddressDTO>> responseEntity =
                addressController.getAddresses(null, "", 1, 1, "asc");
        // Assert
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        final PageResponse<AddressDTO> bodyResponse = responseEntity.getBody();
        assertThat(bodyResponse).isNotNull();
        assertThat(bodyResponse.getTotalPages()).isEqualTo(pageResponse.getTotalPages());
        assertThat(bodyResponse.getTotalElements()).isEqualTo(pageResponse.getTotalElements());
        assertThat(bodyResponse.getResponseItems()).hasSameElementsAs(pageResponse.getResponseItems());
        verify(addressDelegate, times(1))
                .findAddresses(1, 1, "asc", "");
	}

    private AddressDTO buildAddressDTO(final AddressForm addressForm) {
        final AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCep(addressForm.getCep());
        addressDTO.setCity(addressForm.getCity());
        addressDTO.setNumber(addressForm.getNumber());
        addressDTO.setDistrict(addressForm.getDistrict());
        addressDTO.setState(addressForm.getState());
        addressDTO.setStreetName(addressForm.getStreetName());
        return addressDTO;
    }
}