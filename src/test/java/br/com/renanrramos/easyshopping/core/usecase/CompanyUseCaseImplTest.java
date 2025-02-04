package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.Company;
import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
import br.com.renanrramos.easyshopping.core.gateway.CompanyGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CompanyDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CompanyForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CompanyMapper;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationService;
import lombok.SneakyThrows;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyUseCaseImplTest {

    @Mock
    private CompanyGateway companyGateway;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private CompanyUseCaseImpl companyUseCase;

    @Test
    @SneakyThrows
    void saveCompany_withCompany_shouldRunSuccessfully() {
        // Arrange
        final CompanyForm companyForm = Instancio.of(CompanyForm.class)
                .create();
        final Company company = CompanyMapper.INSTANCE.mapCompanyFormToCompany(companyForm);
        when(companyGateway.saveCompany(any(Company.class))).thenReturn(company);
        // Act
        final CompanyDTO response = companyUseCase.saveCompany(companyForm);
        // Assert
        assertThat(response)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(company);
    }

    @Test
    void findCompanies_withParameterRequest_shouldRunSuccessfully() {
        // Arrange
        final List<Company> companies = Instancio.ofList(Company.class)
                .size(3)
                .set(field(Company::getProfile), Profile.COMPANY)
                .create();
        final ParametersRequest parameterRequest = new ParametersRequest(1, 3, "asc");
        final Page<Company> companyEntitiesResponse = new PageImpl<>(companies);
        final Page<Company> expectedPageResponse = new PageImpl<>(companies, companyEntitiesResponse.getPageable(),
                companies.size());
        when(companyGateway.findCompanies(parameterRequest)).thenReturn(expectedPageResponse);
        // Act
        final PageResponse<CompanyDTO> pageResponse = companyUseCase.findCompanies(parameterRequest);
        // Assert
        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getTotalPages()).isEqualTo(companyEntitiesResponse.getTotalPages());
        assertThat(pageResponse.getTotalElements()).isEqualTo(companyEntitiesResponse.getTotalElements());
        final List<CompanyDTO> responseItems = pageResponse.getResponseItems();
        assertThat(responseItems)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(companies);
    }

    @Test
    void getCompanyByNameRegisteredNumberOrEmail_withParameterRequestAndCompanyName_shouldRunSuccessfully() {
        // Arrange
        final String companyName = "companyName";
        final List<Company> companyEntities = Instancio.ofList(Company.class)
                .size(3)
                .set(field(Company::getProfile), Profile.COMPANY)
                .set(field(Company::getName), companyName)
                .create();
        final ParametersRequest parameterRequest = new ParametersRequest(1, 1, "asc");
        final Page<Company> companyEntitiesResponse = new PageImpl<>(companyEntities);
        final Page<Company> expectedPageResponse = new PageImpl<>(companyEntities, companyEntitiesResponse.getPageable(),
                companyEntities.size());
        when(companyGateway.getCompanyByNameRegisteredNumberOrEmail(parameterRequest, companyName))
                .thenReturn(expectedPageResponse);
        // Act
        final PageResponse<CompanyDTO> pageResponse = companyUseCase
                .getCompanyByNameRegisteredNumberOrEmail(parameterRequest, companyName);
        // Assert
        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getTotalPages()).isEqualTo(companyEntitiesResponse.getTotalPages());
        assertThat(pageResponse.getTotalElements()).isEqualTo(companyEntitiesResponse.getTotalElements());
        final List<CompanyDTO> responseItems = pageResponse.getResponseItems();
        assertThat(responseItems)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(companyEntities);
    }

    @Test
    void findCompanyByTokenId_withTokenId_shouldRunSuccessfully() {
        // Arrange
        final String tokenId = "tokenId";
        final Company company = Instancio.of(Company.class)
                .set(field(Company::getProfile), Profile.COMPANY)
                .create();
        when(companyGateway.findCompanyByTokenId(tokenId))
                .thenReturn(company);
        // Act
        final CompanyDTO companyResponse = companyUseCase.findCompanyByTokenId(tokenId);
        // Assert
        assertThat(companyResponse)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(company);
    }

    @Test
    void findCompanyByTokenId_withInvalidTokenId_shouldThrowException() {
        // Arrange
        final String invalidTokenId = "invalidTokenId";
        when(companyGateway.findCompanyByTokenId(invalidTokenId))
                .thenThrow(new EntityNotFoundException(ExceptionConstantMessages.COMPANY_NOT_FOUND));
        // Act
        final EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> companyUseCase.findCompanyByTokenId(invalidTokenId));
        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isNotNull().isEqualTo(ExceptionConstantMessages.COMPANY_NOT_FOUND);
    }

    @Test
    void updateCompany_withCompanyId_shouldRunSuccessfully() {
        // Arrange
        final Long companyId = 1L;
        final String tokenId = "tokenId";
        final CompanyForm companyForm = Instancio.of(CompanyForm.class)
                .create();
        final Company company = CompanyMapper.INSTANCE.mapCompanyFormToCompany(companyForm);
        company.setId(companyId);
        company.setTokenId(tokenId);
        when(authenticationService.getName()).thenReturn(tokenId);
        when(companyGateway.updateCompany(any(Company.class), anyLong())).thenReturn(company);
        // Act
        final CompanyDTO companyResponse = companyUseCase.updateCompany(companyForm, companyId);
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
        final CompanyForm companyForm = Instancio.of(CompanyForm.class)
                .create();
        final Company company = CompanyMapper.INSTANCE.mapCompanyFormToCompany(companyForm);
        company.setId(companyId);
        when(authenticationService.getName()).thenReturn("tokenId");
        when(companyGateway.updateCompany(any(Company.class), anyLong()))
                .thenThrow(new EntityNotFoundException(ExceptionConstantMessages.COMPANY_NOT_FOUND));
        // Act
        final EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class,
                        () -> companyUseCase.updateCompany(companyForm, companyId));
        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isNotNull().isEqualTo(ExceptionConstantMessages.COMPANY_NOT_FOUND);
    }

    @Test
    void removeCompany_withCompanyId_shouldRunSuccessfully() {
        // Arrange
        final Long companyId = 1L;
        // Act
        companyUseCase.removeCompany(companyId);
        // Assert
        verify(companyGateway).removeCompany(companyId);
    }
}