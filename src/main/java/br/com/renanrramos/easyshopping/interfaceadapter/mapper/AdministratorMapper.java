package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.model.AdministratorEntity;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AdministratorMapper {

    AdministratorMapper INSTANCE = Mappers.getMapper(AdministratorMapper.class);

    @Named("mapAdministratorToAdministratorDTO")
    AdministratorDTO mapAdministratorToAdministratorDTO(final AdministratorEntity administratorEntity);

    @Named("mapAdministratorListToAdministratorDTOList")
    default List<AdministratorDTO> mapAdministratorListToAdministratorDTOList(final List<AdministratorEntity> administratorEntityList) {
        return administratorEntityList
                .stream().map(AdministratorMapper.INSTANCE::mapAdministratorToAdministratorDTO)
                .collect(Collectors.toList());
    }

    @Named("mapAdministratorFormToAdministrator")
    @Mapping(target = "profile", constant = "ADMINISTRATOR")
    AdministratorEntity mapAdministratorFormToAdministrator(final AdministratorForm administratorForm);

    @Named("mapAdministratorFormToUpdateAdministrator")
    void mapAdministratorFormToUpdateAdministrator(@MappingTarget AdministratorEntity administratorEntity,
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

    @Named("mapAdministratorEntityToUpdateAdministrator")
    void mapAdministratorEntityToUpdateAdministrator(@MappingTarget final AdministratorEntity administratorEntity,
                                                     final Administrator administrator);
}
