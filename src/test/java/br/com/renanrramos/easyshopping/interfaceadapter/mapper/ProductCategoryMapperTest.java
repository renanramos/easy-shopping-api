package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.ProductCategory;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductCategoryDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductCategoryForm;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCategoryMapperTest {

    @Test
    void mapProductCategoryToProductCategoryDTO_withProductCategory_shouldMapToProductCategoryDTO() {

        final ProductCategory productCategory = Instancio.of(ProductCategory.class)
                .withMaxDepth(10)
                .create();

        final ProductCategoryDTO productCategoryDTO = ProductCategoryMapper.INSTANCE
                .mapProductCategoryToProductCategoryDTO(productCategory);

        assertProductCategoryDTO(productCategoryDTO, productCategory);
    }

    @Test
    void mapProductCategoryListToProductCategoryDTOList_withProductCategoryList_shouldMapProductCategoryDTOList() {
        final List<ProductCategory> productCategories = Instancio.ofList(ProductCategory.class)
                .size(2)
                .withMaxDepth(10)
                .create();

        final List<ProductCategoryDTO> productCategoryDTOS = ProductCategoryMapper.INSTANCE
                .mapProductCategoryListToProductCategoryDTOList(productCategories);

        assertProductCategoryDTOList(productCategoryDTOS, productCategories);
    }

    @Test
    void mapProductCategoryFormToProductCategory_withProductCategoryForm_shouldMapToProductCategory() {
        final ProductCategoryForm productCategoryForm = Instancio.of(ProductCategoryForm.class).create();

        final ProductCategory productCategory = ProductCategoryMapper.INSTANCE
                .mapProductCategoryFormToProductCategory(productCategoryForm);

        assertProductCategory(productCategory, productCategoryForm);
    }

    @Test
    void mapProductCategoryFormToUpdateProductCategory_whenProductCategoryFormUpdateOperation_shouldMapToProductCategoryOnlyDifferentFields() {
        // Arrange
        final ProductCategoryForm productCategoryForm = Instancio.of(ProductCategoryForm.class).create();
        productCategoryForm.setName(null);
        final String productCategoryName = "productCategoryName";
        final ProductCategory productCategory = new ProductCategory();
        productCategory.setName(productCategoryName);
        // Act
        ProductCategoryMapper.INSTANCE.mapProductCategoryFormToUpdateProductCategory(productCategory, productCategoryForm);
        // Assert
        assertThat(productCategory.getName()).isNotNull();
        assertThat(productCategory.getName()).isEqualTo(productCategoryName);
    }

    private void assertProductCategory(final ProductCategory productCategory,
                                       final ProductCategoryForm productCategoryForm) {
        assertThat(productCategory).isNotNull();
        assertThat(productCategory.getName()).isEqualTo(productCategoryForm.getName());
    }

    private void assertProductCategoryDTO(final ProductCategoryDTO productCategoryDTO,
                                          final ProductCategory productCategory) {

        assertThat(productCategoryDTO).isNotNull();
        assertThat(productCategoryDTO.getId()).isEqualTo(productCategory.getId());
        assertThat(productCategoryDTO.getName()).isEqualTo(productCategory.getName());
    }

    private void assertProductCategoryDTOList(final List<ProductCategoryDTO> productCategoryDTOS,
                                              final List<ProductCategory> productCategories) {

        assertThat(productCategoryDTOS).hasSize(productCategories.size());
        int index = 0;
        for (final ProductCategoryDTO productCategoryDTO : productCategoryDTOS) {
            assertProductCategoryDTO(productCategoryDTO, productCategories.get(index));
            index++;
        }

    }
}