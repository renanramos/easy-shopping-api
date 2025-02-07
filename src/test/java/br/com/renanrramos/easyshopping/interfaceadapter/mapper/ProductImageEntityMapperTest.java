package br.com.renanrramos.easyshopping.interfaceadapter.mapper;


import br.com.renanrramos.easyshopping.core.domain.ProductImage;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductImageDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductImageForm;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductImageEntityMapperTest {

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

    @Test
    void mapProductImageFormToProductImage_withProductImage_shouldMapToProductImage() {
        final ProductImageForm productImageForm = Instancio.of(ProductImageForm.class).create();

        final ProductImage productImage = ProductImageMapper.INSTANCE.mapProductImageFormToProductImage(productImageForm);

        assertProductImage(productImage, productImageForm);
    }

    private void assertProductImage(final ProductImage productImage, final ProductImageForm productImageForm) {
        assertThat(productImage).isNotNull();
        assertThat(productImage.isCoverImage()).isEqualTo(productImageForm.isCoverImage());
        assertThat(productImage.getProduct().getId()).isEqualTo(productImageForm.getProductId());
        assertThat(productImage.getDescription()).isEqualTo(productImageForm.getDescription());
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