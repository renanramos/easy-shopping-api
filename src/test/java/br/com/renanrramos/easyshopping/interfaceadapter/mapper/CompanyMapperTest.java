package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.Company;
import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CompanyDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CompanyForm;
import br.com.renanrramos.easyshopping.model.CompanyEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static br.com.renanrramos.easyshopping.interfaceadapter.mapper.util.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

class CompanyMapperTest {

    @Test
    void mapCompanyToCompanyDTO_withCompany_shouldMapToCompanyDTO() {

        final Company company = Instancio.create(Company.class);

        final CompanyDTO companyDTO = CompanyMapper.INSTANCE.mapCompanyToCompanyDTO(company);

        assertCompanyDTO(companyDTO, company);
    }

    @Test
    void mapCompanyListToCompanyDTOList() {
        final List<Company> companyList = Instancio
                .ofList(Company.class)
                .size(10)
                .create();

        final List<CompanyDTO> companyDTOS = CompanyMapper.INSTANCE.mapCompanyListToCompanyDTOList(companyList);

        assertCompanyDTOList(companyDTOS, companyList);
    }

    @Test
    void mapCompanyFormToCompany_withCompanyForm_shouldMapToCompany() {
        final CompanyForm companyForm = Instancio.of(CompanyForm.class).create();

        final Company company = CompanyMapper.INSTANCE.mapCompanyFormToCompany(companyForm);

        assertCompany(company, companyForm);
    }

    @Test
    void mapCompanyFormToUpdateCompany_whenCompanyFormUpdateOperation_shouldMapCompanyOnlyDifferentFields() {
        // Arrange
        final String companyEmail = "company@mail.com";
        final CompanyForm companyForm = Instancio.of(CompanyForm.class)
                .create();
        Company company = CompanyMapper.INSTANCE.mapCompanyFormToCompany(companyForm);
        company.setEmail(companyEmail);
        companyForm.setEmail(null);
        // Act
        CompanyMapper.INSTANCE
                .mapCompanyFormToUpdateCompany(company, companyForm);
        // Assert
        assertThat(company.getEmail()).isEqualTo(companyEmail);
    }

    @Test
    void mapCompanyToCompanyEntity_withCompany_shouldMapToCompanyEntity() {
        // Arrange
        final Company company = Instancio.of(Company.class).create();
        company.setProfile(Profile.COMPANY);
        // Act
        final CompanyEntity companyEntity = CompanyMapper.INSTANCE.mapCompanyToCompanyEntity(company);
        // Assert
        assertCompanyAndCompanyEntity(company, companyEntity);
    }

    @Test
    void mapCompanyEntityToCompany_withCompany_shouldMapToCompany() {
        // Arrange
        final CompanyEntity companyEntity = Instancio.of(CompanyEntity.class)
                .set(field(CompanyEntity::getProfile), Profile.COMPANY)
                .create();
        // Act
        final Company company = CompanyMapper.INSTANCE.mapCompanyEntityToCompany(companyEntity);
        // Assert
        assertCompanyAndCompanyEntity(company, companyEntity);;
    }

    @Test
    void mapCompanyEntityListToCompany_withCompanyEntityList_shouldMapToCompanyList() {
        // Arrange
        final List<CompanyEntity> companyEntities = Instancio.ofList(CompanyEntity.class)
                .size(3)
                .set(field(CompanyEntity::getProfile), Profile.COMPANY)
                .create();
        // Act
        final List<Company> companies = CompanyMapper.INSTANCE.mapCompanyEntityListToCompanyList(companyEntities);
        // Assert
        assertCompanyListAndCompanyEntityList(companies, companyEntities);
    }

    @Test
    void mapCompanyToUpdateCompanyEntity_withCompany_shouldUpdateCompanyEntity() {
        // Arrange
        final String companyEmail = "company@mail.com";
        final Company company = Instancio.of(Company.class)
                .create();
        final CompanyEntity companyEntity = CompanyMapper.INSTANCE.mapCompanyToCompanyEntity(company);
        company.setEmail(companyEmail);
        companyEntity.setEmail(null);
        // Act
        CompanyMapper.INSTANCE
                .mapCompanyToUpdateCompanyEntity(companyEntity, company);
        // Assert
        assertThat(company.getEmail()).isEqualTo(companyEmail);
    }
}