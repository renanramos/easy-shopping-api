package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.infra.delegate.AdministratorDelegate;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AdministratorMapper;
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
import static org.instancio.Select.field;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdministratorControllerTest {

    @Mock
    private AdministratorDelegate administratorDelegate;

    @InjectMocks
    private AdministratorController administratorController;

    @Test
    void saveAdministrator_withAdministratorForm_shouldRunSuccessfully() {
        // Arrange
        final AdministratorForm administratorForm = Instancio.create(AdministratorForm.class);
        final AdministratorDTO expectedResponse = convertToAdministratorDTO(administratorForm);
        when(administratorDelegate.save(administratorForm)).thenReturn(expectedResponse);
        // Act
        final ResponseEntity<AdministratorDTO> responseEntity =
                administratorController.saveAdministrator(administratorForm);
        // Assert
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody())
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void getAdministrators_whenNameIsNull_shouldReturnPageResponse() {
        // Arrange
        final String sortBy = "asc";
        final int pageSize = 1;
        final int pageNumber = 1;
        final ParametersRequest parametersRequest = new ParametersRequest(pageNumber, pageSize, sortBy);
        final List<AdministratorDTO> administratorDTOS = Instancio.ofList(AdministratorDTO.class)
                .size(3)
                .create();
        final PageResponse<AdministratorDTO> expectedResponse = new PageResponse<>(3L, 1, administratorDTOS);
        when(administratorDelegate.findAdministrators(parametersRequest, null))
                .thenReturn(expectedResponse);
        // Act
        final ResponseEntity<PageResponse<AdministratorDTO>> responseEntity =
                administratorController.getAdministrators(null, pageNumber, pageSize, sortBy);
        // Assert
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void getAdministrators_withAdministratorName_shouldReturnPageResponse() {
        // Arrange
        final String administratorName = "administratorName";
        final String sortBy = "asc";
        final int pageSize = 1;
        final int pageNumber = 1;
        final ParametersRequest parametersRequest = new ParametersRequest(pageNumber, pageSize, sortBy);
        final List<AdministratorDTO> administratorDTOS = Instancio.ofList(AdministratorDTO.class)
                .size(3)
                .set(field(AdministratorDTO::getName), administratorName)
                .create();
        final PageResponse<AdministratorDTO> expectedResponse = new PageResponse<>(3L, 1, administratorDTOS);
        when(administratorDelegate.findAdministrators(parametersRequest, administratorName))
                .thenReturn(expectedResponse);
        // Act
        final ResponseEntity<PageResponse<AdministratorDTO>> responseEntity =
                administratorController.getAdministrators(administratorName, pageNumber, pageSize, sortBy);
        // Assert
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void getAdministratorById_withAdministratorId_shouldReturnResponseEntity() {
        // Arrange
        final  Long administratorId = 1L;
        final AdministratorDTO administratorDTO = Instancio.of(AdministratorDTO.class).create();
        when(administratorDelegate.findAdministratorById(administratorId))
                .thenReturn(administratorDTO);
        // Act
        final ResponseEntity<AdministratorDTO> responseEntity =
                administratorController.getAdministratorById(administratorId);
        // Assert
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(administratorDTO);
    }

    @Test
    void updateAdministrator_withAdministratorFormAndAdministratorId_shouldReturnResponseEntity() {
        // Arrange
        final Long administratorId = 1L;
        final AdministratorForm administratorForm = Instancio.of(AdministratorForm.class).create();
        final AdministratorDTO expectedResponse = convertToAdministratorDTO(administratorForm);
        when(administratorDelegate.updateAdministrator(administratorForm, administratorId))
                .thenReturn(expectedResponse);
        // Act
        final ResponseEntity<AdministratorDTO> responseEntity =
                administratorController.updateAdministrator(administratorId, administratorForm);
        // Assert
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void removeAdministrator_withAdministratorId_shouldRunSuccessfully() {
        // Arrange
        final Long administratorId = 1L;
        // Act
        administratorController.removeAdministrator(administratorId);
        // Assert
        verify(administratorDelegate).removeAdministrator(administratorId);
    }

    private static AdministratorDTO convertToAdministratorDTO(AdministratorForm administratorForm) {
        final Administrator administrator = AdministratorMapper.INSTANCE
                .mapAdministratorFormToAdministrator(administratorForm);
        return AdministratorMapper.INSTANCE
                .mapAdministratorToAdministratorDTO(administrator);
    }
}