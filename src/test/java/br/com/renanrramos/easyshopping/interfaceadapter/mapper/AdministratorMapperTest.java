package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Administrator;
import br.com.renanrramos.easyshopping.model.dto.AdministratorDTO;
import org.apache.tomcat.jni.Address;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AdministratorMapperTest {

    @Test
    void mapAdministratorToAdministratorDTO_withAddress_shouldMapAdministratorDTO() {
        final Administrator administrator = Instancio.create(Administrator.class);

        final AdministratorDTO administratorDTO = AdministratorMapper.INSTANCE
                .mapAdministratorToAdministratorDTO(administrator);

        assertAdministratorDTO(administratorDTO, administrator);
    }

    @Test
    void mapAdministratorListToAdministratorDTOList_withAdministratorList_shouldMapToAdministratorDTO() {
        final List<Administrator> administratorList = Instancio.ofList(Administrator.class).size(10).create();

        final List<AdministratorDTO> administratorDTOs = AdministratorMapper.INSTANCE
                .mapAdministratorListToAdministratorDTOList(administratorList);

        assertAdministratorDTOList(administratorDTOs, administratorList);
    }

    private void assertAdministratorDTOList(final List<AdministratorDTO> administratorDTOs,
                                            final List<Administrator> administratorList) {
        assertThat(administratorDTOs).hasSize(administratorList.size());
        int index = 0;
        for(final AdministratorDTO administratorDTO : administratorDTOs) {
            assertAdministratorDTO(administratorDTO, administratorList.get(index));
            index++;
        }
    }

    private static void assertAdministratorDTO(final AdministratorDTO administratorDTO,
                                               final Administrator administrator) {
        assertThat(administratorDTO).isNotNull();
        assertThat(administratorDTO.getId()).isEqualTo(administrator.getId());
        assertThat(administratorDTO.getName()).isEqualTo(administrator.getName());
        assertThat(administratorDTO.getEmail()).isEqualTo(administrator.getEmail());
        assertThat(administratorDTO.getProfile().name()).isEqualTo(administrator.getProfile().name());
    }
}