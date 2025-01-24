package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.Company;
import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.CompanyEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CompanyMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.CompanyRepository;
import lombok.SneakyThrows;
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

import static br.com.renanrramos.easyshopping.interfaceadapter.mapper.util.TestUtils.assertCompanyAndCompanyEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyGatewayImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyGatewayImpl companyGateway;

    @Test
    @SneakyThrows
    void saveCompany_withCompany_shouldRunSuccessfully() {
        // Arrange
        final Company company = Instancio.of(Company.class)
                .set(field(Company::getProfile), Profile.COMPANY)
                .create();
        final CompanyEntity companyEntity = CompanyMapper.INSTANCE.mapCompanyToCompanyEntity(company);
        when(companyRepository.save(companyEntity)).thenReturn(companyEntity);
        // Act
        final Company response = companyGateway.saveCompany(company);
        // Assert
        assertCompanyAndCompanyEntity(response, companyEntity);
    }

    @Test
    void findCompanies_withParameterRequest_shouldRunSuccessfully() {
        // Arrange
        final List<CompanyEntity> companyEntities = Instancio.ofList(CompanyEntity.class)
                .size(3)
                .set(field(CompanyEntity::getProfile), Profile.COMPANY)
                .create();
        final ParametersRequest parameterRequest = new ParametersRequest(1, 1, "asc");
        final Pageable page = new PageableFactory().buildPageable(parameterRequest);
        final Page<CompanyEntity> companyEntitiesResponse = new PageImpl<>(companyEntities);
        when(companyRepository.findAll(page)).thenReturn(companyEntitiesResponse);
        // Act
        final Page<Company> pageResponse = companyGateway.findCompanies(parameterRequest);
        // Assert
        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getTotalPages()).isEqualTo(companyEntitiesResponse.getTotalPages());
        assertThat(pageResponse.getTotalElements()).isEqualTo(companyEntitiesResponse.getTotalElements());
        final List<Company> responseItems = pageResponse.getContent();
        assertThat(responseItems)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(companyEntities);
    }

    @Test
    void getCompanyByNameRegisteredNumberOrEmail_withParameterRequestAndCompanyName_shouldRunSuccessfully() {
        // Arrange
        final String companyName = "companyName";
        final List<CompanyEntity> companyEntities = Instancio.ofList(CompanyEntity.class)
                .size(3)
                .set(field(CompanyEntity::getProfile), Profile.COMPANY)
                .set(field(CompanyEntity::getName), companyName)
                .create();
        final ParametersRequest parameterRequest = new ParametersRequest(1, 1, "asc");
        final Pageable page = new PageableFactory().buildPageable(parameterRequest);
        final Page<CompanyEntity> companyEntitiesResponse = new PageImpl<>(companyEntities);
        when(companyRepository.getCompanyByNameRegisteredNumberOrEmail(page, companyName))
                .thenReturn(companyEntitiesResponse);
        // Act
        final Page<Company> pageResponse = companyGateway
                .getCompanyByNameRegisteredNumberOrEmail(parameterRequest, companyName);
        // Assert
        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getTotalPages()).isEqualTo(companyEntitiesResponse.getTotalPages());
        assertThat(pageResponse.getTotalElements()).isEqualTo(companyEntitiesResponse.getTotalElements());
        final List<Company> responseItems = pageResponse.getContent();
        assertThat(responseItems)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(companyEntities);
    }

    @Test
    void findCompanyByTokenId_withTokenId_shouldRunSuccessfully() {
        // Arrange
        final String tokenId = "tokenId";
        final CompanyEntity companyResponse = Instancio.of(CompanyEntity.class)
                .set(field(CompanyEntity::getProfile), Profile.COMPANY)
                .create();
        when(companyRepository.findCompanyByTokenId(tokenId))
                .thenReturn(Optional.of(companyResponse));
        // Act
        final Company company = companyGateway.findCompanyByTokenId(tokenId);
        // Assert
        assertCompanyAndCompanyEntity(company, companyResponse);
    }

    @Test
    void findCompanyByTokenId_withInvalidTokenId_shouldThrowException() {
        // Arrange
        final String invalidTokenId = "invalidTokenId";
        when(companyRepository.findCompanyByTokenId(invalidTokenId))
                .thenThrow(new EntityNotFoundException(ExceptionMessagesConstants.COMPANY_NOT_FOUND));
        // Act
        final EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> companyGateway.findCompanyByTokenId(invalidTokenId));
        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isNotNull().isEqualTo(ExceptionMessagesConstants.COMPANY_NOT_FOUND);
    }

    @Test
    void updateCompany_withCompanyId_shouldRunSuccessfully() {
        // Arrange
        final Long companyId = 1L;
        final Company company = Instancio.of(Company.class)
                .set(field(Company::getId), companyId)
                .set(field(Company::getProfile), Profile.COMPANY)
                .create();
        final CompanyEntity companyEntity = CompanyMapper.INSTANCE.mapCompanyToCompanyEntity(company);
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(companyEntity));
        when(companyRepository.save(companyEntity)).thenReturn(companyEntity);
        // Act
        final Company companyResponse = companyGateway.updateCompany(company, companyId);
        // Assert
        assertThat(companyResponse)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(company);
    }

    @Test
    void updateCompany_withInvalidCompanyId_shouldThrowException() {
        // Arrange
        final Long companyId = 1L;
        final Company company = Instancio.of(Company.class)
                .set(field(Company::getId), companyId)
                .set(field(Company::getProfile), Profile.COMPANY)
                .create();
        when(companyRepository.findById(companyId))
                .thenThrow(new EntityNotFoundException(ExceptionMessagesConstants.COMPANY_NOT_FOUND));
        // Act
        final EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> companyGateway.updateCompany(company, companyId));
        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isNotNull().isEqualTo(ExceptionMessagesConstants.COMPANY_NOT_FOUND);
        verify(companyRepository, never()).save(any());
    }

    @Test
    void removeCompany_withCompanyId_shouldRunSuccessfully() {
        // Arrange
        final Long companyId = 1L;
        final Company company = Instancio.of(Company.class)
                .set(field(Company::getId), companyId)
                .set(field(Company::getProfile), Profile.COMPANY)
                .create();
        when(companyRepository.findById(companyId))
                .thenReturn(Optional.of(CompanyMapper.INSTANCE.mapCompanyToCompanyEntity(company)));
        // Act
        companyGateway.removeCompany(companyId);
        // Assert
        verify(companyRepository).deleteById(companyId);
    }

    @Test
    void removeCompany_withInvalidCompanyId_shouldThrowException() {
        // Arrange
        final Long companyId = 1L;
        final Company company = Instancio.of(Company.class)
                .set(field(Company::getId), companyId)
                .set(field(Company::getProfile), Profile.COMPANY)
                .create();
        when(companyRepository.findById(companyId))
                .thenThrow(new EntityNotFoundException(ExceptionMessagesConstants.COMPANY_NOT_FOUND));
        // Act
        final EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> companyGateway.removeCompany(companyId));
        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isNotNull().isEqualTo(ExceptionMessagesConstants.COMPANY_NOT_FOUND);
        verify(companyRepository, never()).deleteById(companyId);
    }
}