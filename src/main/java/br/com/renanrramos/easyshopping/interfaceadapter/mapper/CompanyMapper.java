package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.model.dto.CompanyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
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
}
