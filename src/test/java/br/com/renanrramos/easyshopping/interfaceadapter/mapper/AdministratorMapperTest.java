package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Administrator;
import br.com.renanrramos.easyshopping.model.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.model.form.AdministratorForm;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

class AdministratorMapperTest {

    private static final String UPDATED_EMAIL = "another_email@mail.com";

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

    @Test
    void mapAdministratorFormToAdministrator_withAdministratorForm_shouldMapAdministrator() {
        final AdministratorForm administratorForm = Instancio.of(AdministratorForm.class).create();

        final Administrator administrator = AdministratorMapper.INSTANCE.mapAdministratorFormToAdministrator(administratorForm);

        assertAdministrator(administratorForm, administrator);
    }

    @Test
    void mapAdministratorFormToUpdateAdministrator_whenAdministratorFormUpdateOperation_shouldMapAdministratorOnlyDifferentFields() {
        // Arrange
        final AdministratorForm administratorFormUpdate = Instancio.of(AdministratorForm.class)
                .set(field("profile"), Profile.ADMINISTRATOR)
                .ignore(field("email"))
                .create();
        final Administrator administrator = AdministratorMapper.INSTANCE.mapAdministratorFormToAdministrator(administratorFormUpdate);
        // Act
        AdministratorMapper.INSTANCE.mapAdministratorFormToUpdateAdministrator(administrator, administratorFormUpdate);
        // Assert
        assertAdministrator(administratorFormUpdate, administrator);
    }

    private void assertAdministrator(final AdministratorForm administratorForm, final Administrator administrator) {
        assertThat(administrator).isNotNull();
        assertThat(administrator.getName()).isEqualTo(administratorForm.getName());
        assertThat(administrator.getProfile().name()).isEqualTo(Profile.ADMINISTRATOR.name());
        assertThat(administrator.getEmail()).isEqualTo(administratorForm.getEmail());
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