package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.UserEntity;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.UserDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.UserForm;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO mapUserToUserDTO(final UserEntity user);

    @Named("mapUserFormToUser")
    UserEntity mapUserFormToUser(final UserForm userForm);
}
