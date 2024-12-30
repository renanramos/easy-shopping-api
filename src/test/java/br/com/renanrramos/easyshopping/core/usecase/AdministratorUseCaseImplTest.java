package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.core.gateway.AdministratorGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AdministratorMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdministratorUseCaseImplTest {

    @Mock
    private AdministratorGateway administratorGateway;

    @InjectMocks
    private AdministratorUseCaseImpl administratorUseCase;

    @Test
    void save_withAdministratorForm_shouldRunSuccessfully() {
        // Arrange
        final AdministratorForm administratorForm = Instancio.of(AdministratorForm.class).create();
        final Administrator administrator =
                AdministratorMapper.INSTANCE.mapAdministratorFormToAdministrator(administratorForm);
        final AdministratorDTO expectedResponse =
                AdministratorMapper.INSTANCE.mapAdministratorToAdministratorDTO(administrator);
        when(administratorGateway.save(any(Administrator.class))).thenReturn(administrator);
        // Act
        final AdministratorDTO administratorDTO = administratorUseCase.save(administratorForm);
        // Assert
        assertThat(administratorDTO)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void findAllAdministrators_withParameters_shouldReturnPageResponse() {
        // Arrange
        final ParametersRequest parametersRequest = new ParametersRequest();
        final PageResponse<Administrator> expectedPageResponse = new PageResponse<>(3L, 1,
                Collections.nCopies(3, Instancio.of(Administrator.class).create()));
        final List<AdministratorDTO> expectedAdministratorDTOList =
                AdministratorMapper.INSTANCE.mapAdministratorListToAdministratorDTOList(expectedPageResponse.getResponseItems());
        when(administratorGateway.findAllAdministrators(parametersRequest))
                .thenReturn(expectedPageResponse);
        // Act
        final PageResponse<AdministratorDTO> pageResponse =
                administratorUseCase.findAllAdministrators(parametersRequest);
        // Assert
        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getTotalPages()).isEqualTo(expectedPageResponse.getTotalPages());
        assertThat(pageResponse.getTotalElements()).isEqualTo(expectedPageResponse.getTotalElements());
        assertThat(pageResponse.getResponseItems())
                .isNotNull()
                .usingDefaultElementComparator()
                .isEqualTo(expectedAdministratorDTOList);
    }

    @Test
    void findAdministratorById_withValidAdministratorId_shouldRunSuccessfully() {
        // Arrange
        final Long administratorId = 1L;
        final Administrator administrator = Instancio.of(Administrator.class)
                .set(field(Administrator::getId), administratorId)
                .create();
        final AdministratorDTO expectedAdministratorDTO =
                AdministratorMapper.INSTANCE.mapAdministratorToAdministratorDTO(administrator);
        when(administratorGateway.findAdministratorById(administratorId))
                .thenReturn(administrator);
        // Act
        final AdministratorDTO response = administratorUseCase.findAdministratorById(administratorId);
        // Assert
        assertThat(response)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedAdministratorDTO);
    }

    @Test
    void updateAdministrator_withValidAdministratorId_shouldRunSuccessfully() {
        // Arrange
        final Long administratorId = 1L;
        final AdministratorForm administratorForm = Instancio.of(AdministratorForm.class).create();
        final Administrator administrator =
                AdministratorMapper.INSTANCE.mapAdministratorFormToAdministrator(administratorForm);
        final AdministratorDTO expectedAdministratorDTO =
                AdministratorMapper.INSTANCE.mapAdministratorToAdministratorDTO(administrator);
        when(administratorGateway.updateAdministrator(any(Administrator.class), eq(administratorId)))
                .thenReturn(administrator);
        // Act
        final AdministratorDTO administratorDTO =
                administratorUseCase.updateAdministrator(administratorForm, administratorId);
        // Assert
        assertThat(administratorDTO)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedAdministratorDTO);
    }

    @Test
    void removeAdministrator_withValidAdministratorId_shouldRunSuccessfully() {
        // Arrange
        final Long administratorId = 1L;
        // Act - Assert
        assertDoesNotThrow(() -> administratorUseCase.removeAdministrator(administratorId));
    }

    @Test
    void searchAdministratorByName_withAdministratorName_shouldRunSuccessfully() {
        // Arrange
        final int pageNumber = 1;
        final int pageSize = 1;
        final String sortBy = "asc";
        final String administratorName = "name";
        final List<Administrator> administrators = Instancio.ofList(Administrator.class)
                .size(3)
                .create();
        final PageResponse<Administrator> expectedPageResponse = new PageResponse<>(3L, 1,
                administrators);
        final List<AdministratorDTO> expectedResponse =
                AdministratorMapper.INSTANCE.mapAdministratorListToAdministratorDTOList(administrators);
        final ParametersRequest parametersRequest = new ParametersRequest(pageNumber, pageSize, sortBy);
        when(administratorGateway.searchAdministratorByName(parametersRequest, administratorName))
                .thenReturn(expectedPageResponse);
        // Act
        final PageResponse<AdministratorDTO> response =
                administratorUseCase.searchAdministratorByName(parametersRequest, administratorName);
        // Assert
        assertThat(response.getTotalPages()).isEqualTo(expectedPageResponse.getTotalPages());
        assertThat(response.getTotalElements()).isEqualTo(expectedPageResponse.getTotalElements());
        assertThat(response.getResponseItems())
                .isNotNull()
                .isEqualTo(expectedResponse);
    }
}