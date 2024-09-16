package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Administrator;
import br.com.renanrramos.easyshopping.infra.controller.rest.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.infra.controller.rest.form.AdministratorForm;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
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

    @Named("mapAdministratorFormToAdministrator")
    @Mapping(target = "profile", constant = "ADMINISTRATOR")
    Administrator mapAdministratorFormToAdministrator(final AdministratorForm administratorForm);

    @Named("mapAdministratorFormToUpdateAdministrator")
    void mapAdministratorFormToUpdateAdministrator(@MappingTarget Administrator administrator,
                                                   final AdministratorForm administratorFormUpdate);
}
