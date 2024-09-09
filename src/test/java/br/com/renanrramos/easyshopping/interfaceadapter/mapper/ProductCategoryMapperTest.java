package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.model.dto.ProductCategoryDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryMapperTest {

    @Test
    void mapProductCategoryToProductCategoryDTO_withProductCategory_shouldMapToProductCategoryDTO() {

        final ProductCategory productCategory = Instancio.of(ProductCategory.class)
                .ignore(field(Product::getImages))
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
                .ignore(field(Product::getImages))
                .create();

        final List<ProductCategoryDTO> productCategoryDTOS = ProductCategoryMapper.INSTANCE
                .mapProductCategoryListToProductCategoryDTOList(productCategories);

        assertProductCategoryDTOList(productCategoryDTOS, productCategories);
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
        for(final ProductCategoryDTO productCategoryDTO : productCategoryDTOS) {
            assertProductCategoryDTO(productCategoryDTO, productCategories.get(index));
            index++;
        }

    }
}