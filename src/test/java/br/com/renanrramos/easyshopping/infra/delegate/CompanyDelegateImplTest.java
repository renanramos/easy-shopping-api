package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.core.domain.Company;
import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
import br.com.renanrramos.easyshopping.core.usecase.CompanyUseCase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CompanyDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CompanyForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CompanyMapper;
import jakarta.persistence.EntityNotFoundException;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyDelegateImplTest {

    @Mock
    private CompanyUseCase companyUseCase;

    @InjectMocks
    private CompanyDelegateImpl companyDelegate;

    @Test
    @SneakyThrows
    void saveCompany_withCompany_shouldRunSuccessfully() {
        // Arrange
        final CompanyForm companyForm = Instancio.of(CompanyForm.class)
                .create();
        final Company company = CompanyMapper.INSTANCE.mapCompanyFormToCompany(companyForm);
        final CompanyDTO companyDTO = CompanyMapper.INSTANCE.mapCompanyToCompanyDTO(company);
        when(companyUseCase.saveCompany(companyForm)).thenReturn(companyDTO);
        // Act
        final CompanyDTO response = companyDelegate.saveCompany(companyForm);
        // Assert
        assertThat(response)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(company);
    }

    @Test
    void findCompanies_withParameterRequest_shouldRunSuccessfully() {
        // Arrange
        final List<CompanyDTO> companies = Instancio.ofList(CompanyDTO.class)
                .size(3)
                .set(field(CompanyDTO::getProfile), Profile.COMPANY)
                .create();
        final ParametersRequest parameterRequest = new ParametersRequest(1, 3, "asc");
        final Page<CompanyDTO> companyEntitiesResponse = new PageImpl<>(companies);
        final PageResponse<CompanyDTO> expectedPageResponse = new PageResponse<>(3L, 1, companies);
        when(companyUseCase.findCompanies(parameterRequest)).thenReturn(expectedPageResponse);
        // Act
        final PageResponse<CompanyDTO> pageResponse = companyDelegate.findCompanies(parameterRequest, "");
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
        final List<CompanyDTO> companyEntities = Instancio.ofList(CompanyDTO.class)
                .size(3)
                .set(field(CompanyDTO::getProfile), Profile.COMPANY)
                .set(field(CompanyDTO::getName), companyName)
                .create();
        final ParametersRequest parameterRequest = new ParametersRequest(1, 1, "asc");
        final Pageable page = new PageableFactory().buildPageable(parameterRequest);
        final Page<CompanyDTO> companyEntitiesResponse = new PageImpl<>(companyEntities);
        final PageResponse<CompanyDTO> expectedPageResponse =
                new PageResponse<>(3L, 1, companyEntities);
        when(companyUseCase.getCompanyByNameRegisteredNumberOrEmail(parameterRequest, companyName))
                .thenReturn(expectedPageResponse);
        // Act
        final PageResponse<CompanyDTO> pageResponse = companyDelegate.findCompanies(parameterRequest, companyName);
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
        final CompanyDTO company = Instancio.of(CompanyDTO.class)
                .set(field(CompanyDTO::getProfile), Profile.COMPANY)
                .create();
        when(companyUseCase.findCompanyByTokenId(tokenId))
                .thenReturn(company);
        // Act
        final CompanyDTO companyResponse = companyDelegate.findCompanyByTokenId(tokenId);
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
        when(companyUseCase.findCompanyByTokenId(invalidTokenId))
                .thenThrow(new EntityNotFoundException(ExceptionConstantMessages.COMPANY_NOT_FOUND));
        // Act
        final EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> companyDelegate.findCompanyByTokenId(invalidTokenId));
        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isNotNull().isEqualTo(ExceptionConstantMessages.COMPANY_NOT_FOUND);
    }

    @Test
    void updateCompany_withCompanyId_shouldRunSuccessfully() {
        // Arrange
        final Long companyId = 1L;
        final CompanyForm companyForm = Instancio.of(CompanyForm.class)
                .create();
        final Company company = CompanyMapper.INSTANCE.mapCompanyFormToCompany(companyForm);
        company.setId(companyId);
        final CompanyDTO companyDTO = CompanyMapper.INSTANCE.mapCompanyToCompanyDTO(company);
        when(companyUseCase.updateCompany(companyForm, companyId)).thenReturn(companyDTO);
        // Act
        final CompanyDTO companyResponse = companyDelegate.updateCompany(companyForm, companyId);
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
        when(companyUseCase.updateCompany(companyForm, companyId))
                .thenThrow(new EntityNotFoundException(ExceptionConstantMessages.COMPANY_NOT_FOUND));
        // Act
        final EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> companyDelegate.updateCompany(companyForm, companyId));
        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isNotNull().isEqualTo(ExceptionConstantMessages.COMPANY_NOT_FOUND);
    }

    @Test
    void removeCompany_withCompanyId_shouldRunSuccessfully() {
        // Arrange
        final Long companyId = 1L;
        // Act
        companyDelegate.removeCompany(companyId);
        // Assert
        verify(companyUseCase).removeCompany(companyId);
    }
}