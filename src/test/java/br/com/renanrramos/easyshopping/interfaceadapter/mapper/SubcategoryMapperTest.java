package br.com.renanrramos.easyshopping.interfaceadapter.mapper;


import br.com.renanrramos.easyshopping.model.Subcategory;
import br.com.renanrramos.easyshopping.model.dto.SubcategoryDTO;
import br.com.renanrramos.easyshopping.model.form.SubcategoryForm;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SubcategoryMapperTest {

    @Test
    void mapSubcategoryToSubcategoryDTO_withSubcategory_shouldMapToSubcategoryDTO() {
        final Subcategory subcategory = Instancio.of(Subcategory.class)
                .withMaxDepth(3)
                .create();

        final SubcategoryDTO subcategoryDTO = SubcategoryMapper.INSTANCE.mapSubcategoryToSubcategoryDTO(subcategory);

        assertSubcategoryDTO(subcategoryDTO, subcategory);
    }

    @Test
    void mapSubcategoryListToSubcategoryDTOList_withSubcategoryList_shouldMapToSubcategoryDTOList() {
        final List<Subcategory> subcategoryList = Instancio.ofList(Subcategory.class)
                .withMaxDepth(3)
                .create();

        final List<SubcategoryDTO> subcategoryDTOList = SubcategoryMapper.INSTANCE
                .mapSubcategoryListToSubcategoryDTOList(subcategoryList);

        assertSubcategoryDTOList(subcategoryDTOList, subcategoryList);
    }

    @Test
    void mapSubcategoryFormToSubcategory_withSubcategoryForm_shouldMapToSubcategory() {
        final SubcategoryForm subcategoryForm = Instancio.of(SubcategoryForm.class).create();
        final Subcategory subcategory = SubcategoryMapper.INSTANCE.mapSubcategoryFormToSubcategory(subcategoryForm);
        asserSubcategory(subcategory, subcategoryForm);
    }

    @Test
    void mapSubcategoryFormToUpdateSubcategory_whenSubcategoryFormUpdateOperation_shouldMapToSubcategoryOnlyDifferentFields() {
        // Arrange
        final SubcategoryForm subcategoryForm = Instancio.of(SubcategoryForm.class).create();
        final Subcategory subcategory = Instancio.of(Subcategory.class)
                .withMaxDepth(2)
                .create();
        final String subcategoryName = "subcategoryName";
        subcategoryForm.setName(null);
        subcategory.setName(subcategoryName);
        // Act
        SubcategoryMapper.INSTANCE.mapSubcategoryFormToUpdateSubcategory(subcategory, subcategoryForm);
        // Assert
        assertThat(subcategory).isNotNull();
        assertThat(subcategory.getName()).isEqualTo(subcategoryName);
        assertThat(subcategory.getProductCategory().getId()).isEqualTo(subcategoryForm.getProductCategoryId());
    }

    private void asserSubcategory(final Subcategory subcategory, final SubcategoryForm subcategoryForm) {
        assertThat(subcategory).isNotNull();
        assertThat(subcategory.getName()).isEqualTo(subcategoryForm.getName());
        assertThat(subcategory.getProductCategory().getId()).isEqualTo(subcategoryForm.getProductCategoryId());
    }

    private void assertSubcategoryDTOList(final List<SubcategoryDTO> subcategoryDTOList,
                                          final List<Subcategory> subcategoryList) {
        assertThat(subcategoryDTOList).hasSize(subcategoryList.size());
        int index = 0;
        for(final SubcategoryDTO subcategoryDTO : subcategoryDTOList) {
            assertSubcategoryDTO(subcategoryDTO, subcategoryList.get(index));
            index++;
        }
    }

    private void assertSubcategoryDTO(final SubcategoryDTO subcategoryDTO, final Subcategory subcategory) {
        assertThat(subcategoryDTO).isNotNull();
        assertThat(subcategoryDTO.getId()).isEqualTo(subcategory.getId());
        assertThat(subcategoryDTO.getName()).isEqualTo(subcategory.getName());
        assertThat(subcategoryDTO.getProductCategoryId()).isEqualTo(subcategory.getProductCategory().getId());
        assertThat(subcategoryDTO.getProductCategoryName()).isEqualTo(subcategory.getProductCategory().getName());
    }
}