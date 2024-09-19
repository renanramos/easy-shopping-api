package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.core.gateway.AdministratorGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AdministratorMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
        when(administratorGateway.save(administrator)).thenReturn(administrator);
        // Act
        final AdministratorDTO administratorDTO = administratorUseCase.save(administratorForm);
        // Assert
        assertThat(administratorDTO)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void findAllAdministrators() {
    }

    @Test
    void findAdministratorById() {
    }

    @Test
    void updateAdministrator() {
    }

    @Test
    void removeAdministrator() {
    }

    @Test
    void searchAdministratorByName() {
    }
}