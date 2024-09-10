package br.com.renanrramos.easyshopping.interfaceadapter.mapper;


import br.com.renanrramos.easyshopping.model.ProductImage;
import br.com.renanrramos.easyshopping.model.dto.ProductImageDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductImageMapperTest {

    @Test
    void mapProductImageToProductImageDTO_withProductImage_shouldMapToProductImageDTO() {
        final ProductImage productImage = Instancio.of(ProductImage.class)
                .create();

        final ProductImageDTO productImageDTO = ProductImageMapper.INSTANCE
                .mapProductImageToProductImageDTO(productImage);

        assertThat(productImageDTO).isNotNull();
        assertProductImageDTO(productImageDTO, productImage);
    }

    @Test
    void mapProductImageListToProductImageDTOList_withProductImageList_shouldMapToProductImageDTOList() {
        final List<ProductImage> productImageList = Instancio.ofList(ProductImage.class)
                .create();

        final List<ProductImageDTO> productImageDTOList = ProductImageMapper.INSTANCE
                .mapProductImageListToProductImageDTOList(productImageList);

        assertThat(productImageDTOList).isNotNull();
        assertProductImageDTOList(productImageDTOList, productImageList);
    }

    private void assertProductImageDTOList(final List<ProductImageDTO> productImageDTOList,
                                           final List<ProductImage> productImageList) {
        assertThat(productImageDTOList).hasSize(productImageList.size());
        int index = 0;
        for (final ProductImageDTO productImageDTO : productImageDTOList) {
            assertProductImageDTO(productImageDTO, productImageList.get(index));
            index++;
        }
    }

    private void assertProductImageDTO(final ProductImageDTO productImageDTO, final ProductImage productImage) {
        assertThat(productImageDTO.getProductId()).isEqualTo(productImage.getProduct().getId());
        assertThat(productImageDTO.getDescription()).isEqualTo(productImage.getDescription());
        assertThat(productImageDTO.getPicture()).isEqualTo(productImage.getPicture());
    }

}