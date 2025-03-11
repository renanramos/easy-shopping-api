package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.AdministratorEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AdministratorMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.AdministratorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdministratorGatewayImplTest {

    @Mock
    private AdministratorRepository administratorRepository;

    @InjectMocks
    private AdministratorGatewayImpl administratorGateway;

    private static Pageable getBuildPageable(final Integer pageNumber, final Integer pageSize, final String sortBy) {
        return new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();
    }

    private static PageImpl<AdministratorEntity> buildAdministratorEntitiesPageResponse() {
        final List<AdministratorEntity> administratorEntities = Instancio.ofList(AdministratorEntity.class)
                .size(3)
                .set(field(AdministratorEntity::getProfile), Profile.ADMINISTRATOR)
                .create();
        return new PageImpl<>(administratorEntities);
    }

    @Test
    void save_withAdministrator_shouldRunSuccessfully() {
        // Arrange
        final Administrator administrator = Instancio.of(Administrator.class).create();
        final AdministratorEntity administratorEntity = AdministratorMapper.INSTANCE
                .mapAdministratorToAdministratorEntity(administrator);
        when(administratorRepository.save(administratorEntity)).thenReturn(administratorEntity);
        // Act
        final Administrator response = administratorGateway.save(administrator);
        // Assert
        assertThat(response)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(administrator);
    }

    @Test
    void findAllAdministrators_withParameters_shouldReturnAListOfAdministrators() {
        // Arrange
        final ParametersRequest parametersRequest = new ParametersRequest();
        final Pageable page = new PageableFactory().buildPageable(parametersRequest);
        final PageImpl<AdministratorEntity> pageResponse = buildAdministratorEntitiesPageResponse();
        when(administratorRepository.findAll(page)).thenReturn(pageResponse);

        // Act
        final Page<Administrator> administratorPageResponse = administratorGateway
                .findAllAdministrators(parametersRequest);

        // Assert
        assertThat(administratorPageResponse).isNotNull();
        assertThat(administratorPageResponse.getTotalPages()).isEqualTo(pageResponse.getTotalPages());
        assertThat(administratorPageResponse.getTotalElements()).isEqualTo(pageResponse.getTotalElements());
        final List<Administrator> mappedAdministratorsResponse = AdministratorMapper.INSTANCE
                .mapAdministratorEntityListToAdministratorList(pageResponse.getContent());
        final List<Administrator> content = administratorPageResponse.getContent();
        assertThat(content)
                .isNotNull()
                .hasSameSizeAs(mappedAdministratorsResponse);
    }

    @Test
    void findAdministratorById_withAdministratorId_shouldReturnAdministrator() {
        // Arrange
        final Long administratorId = 1L;
        final Optional<AdministratorEntity> administratorEntity =
                Optional.of(Instancio.of(AdministratorEntity.class).create());
        final Administrator expectedAdministrator =
                AdministratorMapper.INSTANCE.mapAdministratorEntityToAdministrator(administratorEntity.get());
        when(administratorRepository.findById(administratorId)).thenReturn(administratorEntity);
        // Act
        final Administrator administrator = administratorGateway.findAdministratorById(administratorId);
        // Assert
        assertThat(administrator)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedAdministrator);
    }

    @Test
    void findAdministratorById_withInvalidAdministratorId_shouldThrowException() {
        // Arrange
        final Long administratorId = 1L;
        when(administratorRepository.findById(administratorId))
                .thenThrow(new EntityNotFoundException(ExceptionConstantMessages.ADMINISTRATOR_NOT_FOUND));
        // Act
        final EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class,
                        () -> administratorGateway.findAdministratorById(administratorId));
        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstantMessages.ADMINISTRATOR_NOT_FOUND);
    }

    @Test
    void updateAdministrator_withAdministratorId_shouldRunSuccessfully() {
        // Arrange
        final Long administratorId = 1L;
        final Administrator administrator = Instancio.of(Administrator.class).create();
        administrator.setId(administratorId);
        final AdministratorEntity administratorEntity =
                AdministratorMapper.INSTANCE.mapAdministratorToAdministratorEntity(administrator);
        when(administratorRepository.findById(administratorId)).thenReturn(Optional.of(administratorEntity));
        when(administratorRepository.save(administratorEntity)).thenReturn(administratorEntity);
        // Act
        final Administrator response = administratorGateway.updateAdministrator(administrator, administratorId);
        // Assert
        assertThat(response)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(administrator);
    }

    @Test
    void updateAdministrator_withInvalidAdministratorId_shouldThrowException() {
        // Arrange
        final Long administratorId = 1L;
        final Administrator administrator = Instancio.of(Administrator.class).create();
        when(administratorRepository.findById(administratorId))
                .thenThrow(new EntityNotFoundException(ExceptionConstantMessages.ADMINISTRATOR_NOT_FOUND));
        // Act
        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> administratorGateway.updateAdministrator(administrator, administratorId));
        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstantMessages.ADMINISTRATOR_NOT_FOUND);
        verify(administratorRepository, never()).save(any());
    }

    @Test
    void removeAdministrator_withValidAdministratorId_shouldRunSuccessfully() {
        // Arrange
        final Long administratorId = 1L;
        final Administrator administrator = Instancio.of(Administrator.class).create();
        administrator.setId(administratorId);
        final AdministratorEntity administratorEntity =
                AdministratorMapper.INSTANCE.mapAdministratorToAdministratorEntity(administrator);
        when(administratorRepository.findById(administratorId)).thenReturn(Optional.of(administratorEntity));
        // Act - Assert
        assertDoesNotThrow(() -> administratorGateway.removeAdministrator(administratorId));
        // Assert
        verify(administratorRepository).removeById(administratorId);
    }

    @Test
    void removeAdministrator_withInvalidAdministratorId_shouldRunSuccessfully() {
        // Arrange
        final Long administratorId = 1L;
        when(administratorRepository.findById(administratorId))
                .thenThrow(new EntityNotFoundException(ExceptionConstantMessages.ADMINISTRATOR_NOT_FOUND));
        // Act
        final EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class,
                        () -> administratorGateway.removeAdministrator(administratorId));
        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage())
                .isNotNull()
                .isEqualTo(ExceptionConstantMessages.ADMINISTRATOR_NOT_FOUND);
        verify(administratorRepository, never()).removeById(administratorId);
    }

    @Test
    void searchAdministratorByName_withValidName_shouldRunReturnAdministratorList() {
        // Arrange
        final String administratorName = "admin";
        final List<AdministratorEntity> administratorEntities = Collections.nCopies(3,
                Instancio.of(AdministratorEntity.class)
                        .withMaxDepth(1)
                        .set(field(AdministratorEntity::getName), administratorName)
                        .create());
        final ParametersRequest parametersRequest = new ParametersRequest();
        final Pageable pageable = getBuildPageable(parametersRequest.getPageNumber(),
                parametersRequest.getPageSize(), parametersRequest.getSortBy());
        when(administratorRepository.findAdministratorByNameContains(pageable, administratorName))
                .thenReturn(new PageImpl<>(administratorEntities));
        // Act
        final Page<Administrator> administrators =
                administratorGateway.searchAdministratorByName(parametersRequest, administratorName);
        // Assert
        assertThat(administrators.getTotalElements()).isEqualTo(3L);
        assertThat(administrators.getTotalPages()).isEqualTo(1L);
        assertThat(administrators.getContent()).isNotNull()
                .hasSize(3)
                .usingDefaultElementComparator()
                .allSatisfy(administrator ->
                        assertThat(administrator.getName())
                                .isNotNull()
                                .isEqualTo(administratorName));

    }
}