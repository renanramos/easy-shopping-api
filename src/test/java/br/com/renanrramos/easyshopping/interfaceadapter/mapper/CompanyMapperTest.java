package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CompanyDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CompanyForm;
import br.com.renanrramos.easyshopping.model.CompanyEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyMapperTest {

    @Test
    void mapCompanyToCompanyDTO_withCompany_shouldMapToCompanyDTO() {

        final CompanyEntity company = Instancio.create(CompanyEntity.class);

        final CompanyDTO companyDTO = CompanyMapper.INSTANCE.mapCompanyToCompanyDTO(company);

        assertCompanyDTO(companyDTO, company);
    }

    @Test
    void mapCompanyListToCompanyDTOList() {
        final List<CompanyEntity> companyList = Instancio.ofList(CompanyEntity.class).size(10).create();

        final List<CompanyDTO> companyDTOS = CompanyMapper.INSTANCE.mapCompanyListToCompanyDTOList(companyList);

        assertCompanyDTOList(companyDTOS, companyList);
    }

    @Test
    void mapCompanyFormToCompany_withCompanyForm_shouldMapToCompany() {
        final CompanyForm companyForm = Instancio.of(CompanyForm.class).create();

        final CompanyEntity company = CompanyMapper.INSTANCE.mapCompanyFormToCompany(companyForm);

        assertCompany(company, companyForm);
    }

    @Test
    void mapCompanyFormToUpdateCompany_whenCompanyFormUpdateOperation_shouldMapCompanyOnlyDifferentFields() {
        // Arrange
        final String companyEmail = "company@mail.com";
        final CompanyForm companyForm = Instancio.of(CompanyForm.class)
                .create();
        CompanyEntity company = CompanyMapper.INSTANCE.mapCompanyFormToCompany(companyForm);
        company.setEmail(companyEmail);
        companyForm.setEmail(null);
        // Act
        CompanyMapper.INSTANCE
                .mapCompanyFormToUpdateCompany(company, companyForm);
        // Assert
        assertThat(company.getEmail()).isEqualTo(companyEmail);
    }

    private void assertCompany(final CompanyEntity company, final CompanyForm companyForm) {
        assertThat(company).isNotNull();
        assertThat(company.getEmail()).isEqualTo(companyForm.getEmail());
        assertThat(company.getName()).isEqualTo(companyForm.getName());
        assertThat(company.getRegisteredNumber()).isEqualTo(companyForm.getRegisteredNumber());
        assertThat(company.getPhone()).isEqualTo(companyForm.getPhone());
        assertThat(company.getProfile()).isEqualTo(Profile.COMPANY);
    }

    private void assertCompanyDTOList(final List<CompanyDTO> companyDTOS, final List<CompanyEntity> companyList) {
        assertThat(companyDTOS).hasSize(companyList.size());
        int index = 0;

        for(final CompanyDTO companyDTO : companyDTOS) {
            assertCompanyDTO(companyDTO, companyList.get(index));
            index++;
        }
    }

    private static void assertCompanyDTO(final CompanyDTO companyDTO, final CompanyEntity company) {
        assertThat(companyDTO).isNotNull();
        assertThat(companyDTO.getId()).isEqualTo(company.getId());
        assertThat(companyDTO.getProfile().name()).isEqualTo(company.getProfile().name());
        assertThat(companyDTO.getName()).isEqualTo(company.getName());
        assertThat(companyDTO.getPhone()).isEqualTo(company.getPhone());
        assertThat(companyDTO.getEmail()).isEqualTo(company.getEmail());
        assertThat(companyDTO.getRegisteredNumber()).isEqualTo(company.getRegisteredNumber());
    }
}