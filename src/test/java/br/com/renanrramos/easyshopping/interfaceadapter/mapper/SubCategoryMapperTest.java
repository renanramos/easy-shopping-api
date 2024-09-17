package br.com.renanrramos.easyshopping.interfaceadapter.mapper;


import br.com.renanrramos.easyshopping.model.SubCategory;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.SubCategoryDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.SubCategoryForm;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SubCategoryMapperTest {

    @Test
    void mapSubCategoryToSubCategoryDTO_withSubCategory_shouldMapToSubCategoryDTO() {
        final SubCategory subCategory = Instancio.of(SubCategory.class)
                .withMaxDepth(3)
                .create();

        final SubCategoryDTO subCategoryDTO = SubCategoryMapper.INSTANCE.mapSubCategoryToSubCategoryDTO(subCategory);

        assertSubCategoryDTO(subCategoryDTO, subCategory);
    }

    @Test
    void mapSubCategoryListToSubCategoryDTOList_withSubCategoryList_shouldMapToSubCategoryDTOList() {
        final List<SubCategory> subCategoryList = Instancio.ofList(SubCategory.class)
                .withMaxDepth(3)
                .create();

        final List<SubCategoryDTO> subCategoryDTOList = SubCategoryMapper.INSTANCE
                .mapSubCategoryListToSubCategoryDTOList(subCategoryList);

        assertSubCategoryDTOList(subCategoryDTOList, subCategoryList);
    }

    @Test
    void mapSubCategoryFormToSubCategory_withSubCategoryForm_shouldMapToSubCategory() {
        final SubCategoryForm subCategoryForm = Instancio.of(SubCategoryForm.class).create();
        final SubCategory subCategory = SubCategoryMapper.INSTANCE.mapSubCategoryFormToSubCategory(subCategoryForm);
        asserSubCategory(subCategory, subCategoryForm);
    }

    @Test
    void mapSubCategoryFormToUpdateSubCategory_whenSubCategoryFormUpdateOperation_shouldMapToSubCategoryOnlyDifferentFields() {
        // Arrange
        final SubCategoryForm subCategoryForm = Instancio.of(SubCategoryForm.class).create();
        final SubCategory subCategory = Instancio.of(SubCategory.class)
                .withMaxDepth(2)
                .create();
        final String subCategoryName = "subcategoryName";
        subCategoryForm.setName(null);
        subCategory.setName(subCategoryName);
        // Act
        SubCategoryMapper.INSTANCE.mapSubCategoryFormToUpdateSubCategory(subCategory, subCategoryForm);
        // Assert
        assertThat(subCategory).isNotNull();
        assertThat(subCategory.getName()).isEqualTo(subCategoryName);
        assertThat(subCategory.getProductCategory().getId()).isEqualTo(subCategoryForm.getProductCategoryId());
    }

    private void asserSubCategory(final SubCategory subCategory, final SubCategoryForm subCategoryForm) {
        assertThat(subCategory).isNotNull();
        assertThat(subCategory.getName()).isEqualTo(subCategoryForm.getName());
        assertThat(subCategory.getProductCategory().getId()).isEqualTo(subCategoryForm.getProductCategoryId());
    }

    private void assertSubCategoryDTOList(final List<SubCategoryDTO> subCategoryDTOList,
                                          final List<SubCategory> subCategoryList) {
        assertThat(subCategoryDTOList).hasSize(subCategoryList.size());
        int index = 0;
        for(final SubCategoryDTO subCategoryDTO : subCategoryDTOList) {
            assertSubCategoryDTO(subCategoryDTO, subCategoryList.get(index));
            index++;
        }
    }

    private void assertSubCategoryDTO(final SubCategoryDTO subCategoryDTO, final SubCategory subCategory) {
        assertThat(subCategoryDTO).isNotNull();
        assertThat(subCategoryDTO.getId()).isEqualTo(subCategory.getId());
        assertThat(subCategoryDTO.getName()).isEqualTo(subCategory.getName());
        assertThat(subCategoryDTO.getProductCategoryId()).isEqualTo(subCategory.getProductCategory().getId());
        assertThat(subCategoryDTO.getProductCategoryName()).isEqualTo(subCategory.getProductCategory().getName());
    }
}