package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.UserEntity;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.UserDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.UserForm;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {


    @Test
    void mapUserToUserDTO_withUser_shouldMapToUserDTO() {
        final UserEntity user = Instancio.of(UserEntity.class).create();

        final UserDTO userDto = UserMapper.INSTANCE.mapUserToUserDTO(user);

        assertThat(userDto).isNotNull();
        assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDto.getName()).isEqualTo(user.getName());
    }

    @Test
    void mapUserFormToUser_withUserForm_shouldMapToUser() {
        final UserForm userForm = Instancio.of(UserForm.class).create();

        final UserEntity user = UserMapper.INSTANCE.mapUserFormToUser(userForm);

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(userForm.getEmail());
        assertThat(user.getName()).isEqualTo(userForm.getName());
        assertThat(user.getProfile().name()).isEqualTo(userForm.getProfile().name());
    }

}