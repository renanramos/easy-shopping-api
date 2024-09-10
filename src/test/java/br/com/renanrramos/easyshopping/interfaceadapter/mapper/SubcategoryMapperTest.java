package br.com.renanrramos.easyshopping.interfaceadapter.mapper;


import br.com.renanrramos.easyshopping.model.Subcategory;
import br.com.renanrramos.easyshopping.model.dto.SubcategoryDTO;
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