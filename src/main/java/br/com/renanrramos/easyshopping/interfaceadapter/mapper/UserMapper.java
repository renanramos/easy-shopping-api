package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.User;
import br.com.renanrramos.easyshopping.infra.controller.rest.dto.UserDTO;
import br.com.renanrramos.easyshopping.infra.controller.rest.form.UserForm;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO mapUserToUserDTO(final User user);

    @Named("mapUserFormToUser")
    User mapUserFormToUser(final UserForm userForm);
}
