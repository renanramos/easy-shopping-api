package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.model.dto.CompanyDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CompanyMapperTest {

    @Test
    void mapCompanyToCompanyDTO_withCompany_shouldMapToCompanyDTO() {

        final Company company = Instancio.create(Company.class);

        final CompanyDTO companyDTO = CompanyMapper.INSTANCE.mapCompanyToCompanyDTO(company);

        assertCompanyDTO(companyDTO, company);
    }

    @Test
    void mapCompanyListToCompanyDTOList() {
        final List<Company> companyList = Instancio.ofList(Company.class).size(10).create();

        final List<CompanyDTO> companyDTOS = CompanyMapper.INSTANCE.mapCompanyListToCompanyDTOList(companyList);

        assertCompanyDTOList(companyDTOS, companyList);
    }

    private void assertCompanyDTOList(final List<CompanyDTO> companyDTOS, final List<Company> companyList) {
        assertThat(companyDTOS).hasSize(companyList.size());
        int index = 0;

        for(final CompanyDTO companyDTO : companyDTOS) {
            assertCompanyDTO(companyDTO, companyList.get(index));
            index++;
        }
    }

    private static void assertCompanyDTO(final CompanyDTO companyDTO, final Company company) {
        assertThat(companyDTO).isNotNull();
        assertThat(companyDTO.getId()).isEqualTo(company.getId());
        assertThat(companyDTO.getProfile().name()).isEqualTo(company.getProfile().name());
        assertThat(companyDTO.getName()).isEqualTo(company.getName());
        assertThat(companyDTO.getPhone()).isEqualTo(company.getPhone());
        assertThat(companyDTO.getEmail()).isEqualTo(company.getEmail());
        assertThat(companyDTO.getRegisteredNumber()).isEqualTo(company.getRegisteredNumber());
    }
}