package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.User;
import br.com.renanrramos.easyshopping.model.dto.UserDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {


    @Test
    void mapUserToUserDTO_withUser_shouldMapToUserDTO() {
        final User user = Instancio.of(User.class).create();

        final UserDTO userDto = UserMapper.INSTANCE.mapUserToUserDTO(user);

        assertThat(userDto).isNotNull();
        assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDto.getName()).isEqualTo(user.getName());
    }
}