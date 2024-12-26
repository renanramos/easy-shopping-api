package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.Company;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CompanyDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CompanyForm;
import br.com.renanrramos.easyshopping.interfaceadapter.domain.CompanyEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    @Named("mapCompanyToCompanyDTO")
    CompanyDTO mapCompanyToCompanyDTO(final Company company);

    @Named("mapCompanyListToCompanyDTOList")
    default List<CompanyDTO> mapCompanyListToCompanyDTOList(final List<Company> companies) {
        return companies.stream()
                .map(CompanyMapper.INSTANCE::mapCompanyToCompanyDTO)
                .collect(Collectors.toList());
    }

    @Named("mapCompanyFormToCompany")
    @Mapping(target = "profile", constant = "COMPANY")
    Company mapCompanyFormToCompany(final CompanyForm companyForm);

    @Named("mapCompanyFormToUpdateCompany")
    void mapCompanyFormToUpdateCompany(@MappingTarget Company company, CompanyForm companyForm);

    @Named("mapCompanyToCompanyEntity")
    CompanyEntity mapCompanyToCompanyEntity(final Company company);

    @Named("mapCompanyEntityToCompany")
    Company mapCompanyEntityToCompany(final CompanyEntity companyEntity);

    @Named("mapCompanyEntityListToCompanyList")
    default List<Company> mapCompanyEntityListToCompanyList(final List<CompanyEntity> companyEntities) {
        return companyEntities
                .stream()
                .map(CompanyMapper.INSTANCE::mapCompanyEntityToCompany)
                .collect(Collectors.toList());
    }

    @Named("mapCompanyToUpdateCompanyEntity")
    void mapCompanyToUpdateCompanyEntity(@MappingTarget CompanyEntity companyToBeUpdated, final Company company);
}