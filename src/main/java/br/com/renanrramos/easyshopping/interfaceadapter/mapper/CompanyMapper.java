package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CompanyDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CompanyForm;
import br.com.renanrramos.easyshopping.model.CompanyEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    @Named("mapCompanyToCompanyDTO")
    CompanyDTO mapCompanyToCompanyDTO(final CompanyEntity company);

    @Named("mapCompanyListToCompanyDTOList")
    default List<CompanyDTO> mapCompanyListToCompanyDTOList(final List<CompanyEntity> companies) {
        return companies.stream()
                .map(CompanyMapper.INSTANCE::mapCompanyToCompanyDTO)
                .collect(Collectors.toList());
    }

    @Named("mapCompanyFormToCompany")
    @Mapping(target = "profile", constant = "COMPANY")
    CompanyEntity mapCompanyFormToCompany(final CompanyForm companyForm);

    @Named("mapCompanyFormToUpdateCompany")
    void mapCompanyFormToUpdateCompany(@MappingTarget CompanyEntity company, CompanyForm companyForm);
}