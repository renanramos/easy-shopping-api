package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.infra.controller.rest.dto.CompanyDTO;
import br.com.renanrramos.easyshopping.infra.controller.rest.form.CompanyForm;
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
}
