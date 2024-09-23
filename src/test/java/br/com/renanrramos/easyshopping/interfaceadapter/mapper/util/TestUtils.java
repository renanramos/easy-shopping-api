package br.com.renanrramos.easyshopping.interfaceadapter.mapper.util;

import br.com.renanrramos.easyshopping.core.domain.Address;
import br.com.renanrramos.easyshopping.core.domain.Company;
import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CompanyDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CompanyForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.model.AddressEntity;
import br.com.renanrramos.easyshopping.model.CompanyEntity;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {



    public static void assertAddressDTOList(final PageResponse<Address> addressDTOPageResponse,
                                      final Page<AddressEntity> addressPage) {
        assertThat(addressDTOPageResponse).isNotNull();
        assertThat(addressPage).isNotNull();
        assertThat(addressDTOPageResponse.getTotalElements()).isEqualTo(addressPage.getTotalElements());
        assertThat(addressDTOPageResponse.getTotalPages()).isEqualTo(addressPage.getTotalPages());

        assertAddressList(addressDTOPageResponse.getResponseItems(), addressPage.getContent());
    }

    public static void assertAddressList(final List<Address> addressPageResponse,
                                          final List<AddressEntity> addresses) {
        assertThat(addressPageResponse).hasSize(addresses.size());
        int index = 0;
        for(final Address address : addressPageResponse) {
            final AddressEntity addressEntity = addresses.get(index);

            assertThat(address).isNotNull();
            assertThat(address.getCep()).isEqualTo(addressEntity.getCep());
            assertThat(address.getCity()).isEqualTo(addressEntity.getCity());
            assertThat(address.getDistrict()).isEqualTo(addressEntity.getDistrict());
            assertThat(address.getNumber()).isEqualTo(addressEntity.getNumber());
            assertThat(address.getState()).isEqualTo(addressEntity.getState());
            assertThat(address.getStreetName()).isEqualTo(addressEntity.getStreetName());
            index++;
        }
    }

    public static void assertAddress(final AddressDTO addressDTO, final AddressForm addressForm) {
        assertThat(addressDTO).isNotNull();
        assertThat(addressDTO.getCep()).isEqualTo(addressForm.getCep());
        assertThat(addressDTO.getCity()).isEqualTo(addressForm.getCity());
        assertThat(addressDTO.getDistrict()).isEqualTo(addressForm.getDistrict());
        assertThat(addressDTO.getNumber()).isEqualTo(addressForm.getNumber());
        assertThat(addressDTO.getState()).isEqualTo(addressForm.getState());
        assertThat(addressDTO.getStreetName()).isEqualTo(addressForm.getStreetName());
    }
    public static void assertAddress(final Address address, final AddressEntity addressEntity) {
        assertThat(address).isNotNull();
        assertThat(address.getCity()).isEqualTo(addressEntity.getCity());
        assertThat(address.getState()).isEqualTo(addressEntity.getState());
        assertThat(address.getNumber()).isEqualTo(addressEntity.getNumber());
        assertThat(address.getDistrict()).isEqualTo(addressEntity.getDistrict());
        assertThat(address.getCep()).isEqualTo(addressEntity.getCep());
        assertThat(address.getStreetName()).isEqualTo(addressEntity.getStreetName());
        assertThat(address.getState()).isEqualTo(addressEntity.getState());
    }

    public static void assertAddress(final Address address, final AddressForm addressForm) {
        assertThat(address).isNotNull();
        assertThat(address.getCity()).isEqualTo(addressForm.getCity());
        assertThat(address.getState()).isEqualTo(addressForm.getState());
        assertThat(address.getNumber()).isEqualTo(addressForm.getNumber());
        assertThat(address.getDistrict()).isEqualTo(addressForm.getDistrict());
        assertThat(address.getCep()).isEqualTo(addressForm.getCep());
        assertThat(address.getStreetName()).isEqualTo(addressForm.getStreetName());
        assertThat(address.getState()).isEqualTo(addressForm.getState());
    }

    public static void assertAddressDTOList(final List<AddressDTO> addressDTOS, final List<Address> addressList) {

        assertThat(addressDTOS).hasSize(addressList.size());
        int index = 0;
        for (final AddressDTO addressDTO: addressDTOS) {
            assertAddressDTO(addressDTO, addressList.get(index));
            index++;
        }
    }

    public static void assertAddressDTO(final AddressDTO addressDTO, final Address address) {
        assertThat(addressDTO).isNotNull();
        assertThat(addressDTO.getId()).isEqualTo(address.getId());
        assertThat(addressDTO.getNumber()).isEqualTo(address.getNumber());
        assertThat(addressDTO.getDistrict()).isEqualTo(address.getDistrict());
        assertThat(addressDTO.getCity()).isEqualTo(address.getCity());
        assertThat(addressDTO.getCep()).isEqualTo(address.getCep());
        assertThat(addressDTO.getCustomerId()).isEqualTo(address.getCustomerId());
        assertThat(addressDTO.getState()).isEqualTo(address.getState());
        assertThat(addressDTO.getStreetName()).isEqualTo(address.getStreetName());
    }

    public static void assertCompanyListAndCompanyEntityList(final List<Company> companies,
                                                       final List<CompanyEntity> companyEntities) {
        assertThat(companies)
                .isNotNull()
                .hasSize(companyEntities.size());
        int index = 0;
        for (final Company company : companies) {
            assertCompanyAndCompanyEntity(company, companyEntities.get(index));
            index++;
        }
    }

    public static void assertCompanyAndCompanyEntity(final Company company, final CompanyEntity companyEntity) {
        assertThat(companyEntity).isNotNull();
        assertThat(companyEntity.getId()).isEqualTo(company.getId());
        assertThat(companyEntity.getEmail()).isEqualTo(company.getEmail());
        assertThat(companyEntity.getName()).isEqualTo(company.getName());
        assertThat(companyEntity.getRegisteredNumber()).isEqualTo(company.getRegisteredNumber());
        assertThat(companyEntity.getPhone()).isEqualTo(company.getPhone());
        assertThat(companyEntity.getProfile()).isEqualTo(Profile.COMPANY);
    }

    public static void assertCompany(final Company company, final CompanyForm companyForm) {
        assertThat(company).isNotNull();
        assertThat(company.getEmail()).isEqualTo(companyForm.getEmail());
        assertThat(company.getName()).isEqualTo(companyForm.getName());
        assertThat(company.getRegisteredNumber()).isEqualTo(companyForm.getRegisteredNumber());
        assertThat(company.getPhone()).isEqualTo(companyForm.getPhone());
        assertThat(company.getProfile()).isEqualTo(Profile.COMPANY);
    }

    public static void assertCompanyDTOList(final List<CompanyDTO> companyDTOS, final List<Company> companyList) {
        assertThat(companyDTOS).hasSize(companyList.size());
        int index = 0;

        for(final CompanyDTO companyDTO : companyDTOS) {
            assertCompanyDTO(companyDTO, companyList.get(index));
            index++;
        }
    }

    public static void assertCompanyDTO(final CompanyDTO companyDTO, final Company company) {
        assertThat(companyDTO).isNotNull();
        assertThat(companyDTO.getId()).isEqualTo(company.getId());
        assertThat(companyDTO.getProfile().name()).isEqualTo(company.getProfile().name());
        assertThat(companyDTO.getName()).isEqualTo(company.getName());
        assertThat(companyDTO.getPhone()).isEqualTo(company.getPhone());
        assertThat(companyDTO.getEmail()).isEqualTo(company.getEmail());
        assertThat(companyDTO.getRegisteredNumber()).isEqualTo(company.getRegisteredNumber());
    }

}