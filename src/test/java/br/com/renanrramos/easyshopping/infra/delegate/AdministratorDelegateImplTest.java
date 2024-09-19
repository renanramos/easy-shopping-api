package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.core.usecase.AdministratorUseCase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
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
class AdministratorDelegateImplTest {

    @Mock
    private AdministratorUseCase administratorUseCase;

    @InjectMocks
    private AdministratorDelegateImpl administratorDelegate;

    @Test
    void save_withAdministratorForm_shouldRunSuccessfully() {
        // Arrange
        final AdministratorForm administratorForm = Instancio.of(AdministratorForm.class).create();
        final Administrator administrator =
                AdministratorMapper.INSTANCE.mapAdministratorFormToAdministrator(administratorForm);
        final AdministratorDTO expectedResponse =
                AdministratorMapper.INSTANCE.mapAdministratorToAdministratorDTO(administrator);
        when(administratorUseCase.save(administratorForm)).thenReturn(expectedResponse);
        // Act
        final AdministratorDTO administratorDTO = administratorDelegate.save(administratorForm);
        // Assert
        assertThat(administratorDTO)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void findAllAdministrators_withParameters_shouldReturnPageResponse() {
        // Arrange
        final Integer pageNumber = 1;
        final Integer pageSize = 1;
        final String sortBy = "asc";
        final PageResponse<AdministratorDTO> expectedPageResponse = new PageResponse<>(3L, 1,
                Collections.nCopies(3, Instancio.of(AdministratorDTO.class).create()));
        when(administratorUseCase.findAllAdministrators(pageNumber, pageSize, sortBy))
                .thenReturn(expectedPageResponse);
        // Act
        final PageResponse<AdministratorDTO> pageResponse =
                administratorDelegate.findAllAdministrators(pageNumber, pageSize, sortBy);
        // Assert
        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getTotalPages()).isEqualTo(expectedPageResponse.getTotalPages());
        assertThat(pageResponse.getTotalElements()).isEqualTo(expectedPageResponse.getTotalElements());
        assertThat(pageResponse.getResponseItems())
                .isNotNull()
                .usingDefaultElementComparator()
                .isEqualTo(expectedPageResponse.getResponseItems());
    }

    @Test
    void findAdministratorById_withValidAdministratorId_shouldRunSuccessfully() {
        // Arrange
        final Long administratorId = 1L;
        final AdministratorDTO expectedAdministratorDTO =
                AdministratorMapper.INSTANCE.mapAdministratorToAdministratorDTO(Instancio.of(Administrator.class)
                        .set(field(Administrator::getId), administratorId)
                        .create());
        when(administratorUseCase.findAdministratorById(administratorId))
                .thenReturn(expectedAdministratorDTO);
        // Act
        final AdministratorDTO response = administratorDelegate.findAdministratorById(administratorId);
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
        when(administratorUseCase.updateAdministrator(any(AdministratorForm.class), eq(administratorId)))
                .thenReturn(expectedAdministratorDTO);
        // Act
        final AdministratorDTO administratorDTO =
                administratorDelegate.updateAdministrator(administratorForm, administratorId);
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
        assertDoesNotThrow(() -> administratorDelegate.removeAdministrator(administratorId));
    }

    @Test
    void searchAdministratorByName_withAdministratorName_shouldRunSuccessfully() {
        // Arrange
        final String administratorName = "name";
        final List<Administrator> administrators = Instancio.ofList(Administrator.class)
                .size(3)
                .create();
        final List<AdministratorDTO> expectedResponse =
                AdministratorMapper.INSTANCE.mapAdministratorListToAdministratorDTOList(administrators);
        when(administratorUseCase.searchAdministratorByName(administratorName)).thenReturn(expectedResponse);
        // Act
        final List<AdministratorDTO> response = administratorDelegate.searchAdministratorByName(administratorName);
        // Assert
        assertThat(response)
                .isNotNull()
                .isEqualTo(expectedResponse);
    }
}