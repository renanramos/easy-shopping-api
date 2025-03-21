package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.AdministratorEntity;
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

    @Named("mapAdministratorToAdministratorEntity")
    AdministratorEntity mapAdministratorToAdministratorEntity(final Administrator administrator);

    @Named("mapAdministratorEntityToAdministrator")
    Administrator mapAdministratorEntityToAdministrator(final AdministratorEntity administratorEntity);

    @Named("mapAdministratorEntityListToAdministratorList")
    default List<Administrator> mapAdministratorEntityListToAdministratorList(final List<AdministratorEntity> entities) {
        return entities
                .stream()
                .map(AdministratorMapper.INSTANCE::mapAdministratorEntityToAdministrator)
                .collect(Collectors.toList());
    }

    @Named("mapAdministratorEntityToAdministratorDTO")
    AdministratorDTO mapAdministratorEntityToAdministratorDTO(final AdministratorEntity administratorEntity);

    @Named("mapAdministratorEntityListToAdministratorDTOList")
    default List<AdministratorDTO> mapAdministratorEntityListToAdministratorDTOList(final List<AdministratorEntity> content) {
        return content
                .stream()
                .map(this::mapAdministratorEntityToAdministratorDTO)
                .collect(Collectors.toList());
    }

    @Named("mapAdministratorToUpdateAdministratorEntity")
    void mapAdministratorToUpdateAdministratorEntity(@MappingTarget final AdministratorEntity administratorEntity,
                                                     final Administrator administrator);
}
