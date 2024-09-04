package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Administrator;
import br.com.renanrramos.easyshopping.model.dto.AdministratorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdministratorMapper {

    AdministratorMapper INSTANCE = Mappers.getMapper(AdministratorMapper.class);

    @Named("mapAdministratorToAdministratorDTO")
    AdministratorDTO mapAdministratorToAdministratorDTO(final Administrator administrator);

    @Named("mapAdministratorListToAdministratorDTOList")
    default List<AdministratorDTO> mapAdministratorListToAdministratorDTOList(final List<Administrator> administratorList) {
        return administratorList
                .stream().map(AdministratorMapper.INSTANCE::mapAdministratorToAdministratorDTO)
                .collect(Collectors.toList());
    }
}
