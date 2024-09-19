package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
import br.com.renanrramos.easyshopping.model.AdministratorEntity;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

class AdministratorEntityMapperTest {

    private static final String UPDATED_EMAIL = "another_email@mail.com";

    @Test
    void mapAdministratorToAdministratorDTO_withAddress_shouldMapAdministratorDTO() {
        final AdministratorEntity administratorEntity = Instancio.create(AdministratorEntity.class);

        final AdministratorDTO administratorDTO = AdministratorMapper.INSTANCE
                .mapAdministratorToAdministratorDTO(administratorEntity);

        assertAdministratorDTO(administratorDTO, administratorEntity);
    }

    @Test
    void mapAdministratorListToAdministratorDTOList_withAdministratorList_shouldMapToAdministratorDTO() {
        final List<AdministratorEntity> administratorEntityList = Instancio.ofList(AdministratorEntity.class).size(10).create();

        final List<AdministratorDTO> administratorDTOs = AdministratorMapper.INSTANCE
                .mapAdministratorListToAdministratorDTOList(administratorEntityList);

        assertAdministratorDTOList(administratorDTOs, administratorEntityList);
    }

    @Test
    void mapAdministratorFormToAdministrator_withAdministratorForm_shouldMapAdministrator() {
        final AdministratorForm administratorForm = Instancio.of(AdministratorForm.class).create();

        final AdministratorEntity administratorEntity = AdministratorMapper.INSTANCE.mapAdministratorFormToAdministrator(administratorForm);

        assertAdministrator(administratorForm, administratorEntity);
    }

    @Test
    void mapAdministratorFormToUpdateAdministrator_whenAdministratorFormUpdateOperation_shouldMapAdministratorOnlyDifferentFields() {
        // Arrange
        final AdministratorForm administratorFormUpdate = Instancio.of(AdministratorForm.class)
                .set(field("profile"), Profile.ADMINISTRATOR)
                .ignore(field("email"))
                .create();
        final AdministratorEntity administratorEntity = AdministratorMapper.INSTANCE.mapAdministratorFormToAdministrator(administratorFormUpdate);
        // Act
        AdministratorMapper.INSTANCE.mapAdministratorFormToUpdateAdministrator(administratorEntity, administratorFormUpdate);
        // Assert
        assertAdministrator(administratorFormUpdate, administratorEntity);
    }

    private void assertAdministrator(final AdministratorForm administratorForm, final AdministratorEntity administratorEntity) {
        assertThat(administratorEntity).isNotNull();
        assertThat(administratorEntity.getName()).isEqualTo(administratorForm.getName());
        assertThat(administratorEntity.getProfile().name()).isEqualTo(Profile.ADMINISTRATOR.name());
        assertThat(administratorEntity.getEmail()).isEqualTo(administratorForm.getEmail());
    }

    private void assertAdministratorDTOList(final List<AdministratorDTO> administratorDTOs,
                                            final List<AdministratorEntity> administratorEntityList) {
        assertThat(administratorDTOs).hasSize(administratorEntityList.size());
        int index = 0;
        for(final AdministratorDTO administratorDTO : administratorDTOs) {
            assertAdministratorDTO(administratorDTO, administratorEntityList.get(index));
            index++;
        }
    }

    private static void assertAdministratorDTO(final AdministratorDTO administratorDTO,
                                               final AdministratorEntity administratorEntity) {
        assertThat(administratorDTO).isNotNull();
        assertThat(administratorDTO.getId()).isEqualTo(administratorEntity.getId());
        assertThat(administratorDTO.getName()).isEqualTo(administratorEntity.getName());
        assertThat(administratorDTO.getEmail()).isEqualTo(administratorEntity.getEmail());
        assertThat(administratorDTO.getProfile().name()).isEqualTo(administratorEntity.getProfile().name());
    }
}