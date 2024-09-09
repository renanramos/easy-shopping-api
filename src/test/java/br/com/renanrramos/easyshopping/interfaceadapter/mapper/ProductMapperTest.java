package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.ProductImage;
import br.com.renanrramos.easyshopping.model.dto.ProductDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

class ProductMapperTest {

    @Test
    void mapProductToProductDTO_withProduct_shouldMapToProductDTO() {
        final Product product = Instancio.of(Product.class)
                .withMaxDepth(2)
                .ignore(field(Product::getImages))
                .create();

        final ProductDTO productDTO = ProductMapper.INSTANCE.mapProductToProductDTO(product);

        assertProductDTO(productDTO, product);
    }

    @Test
    void mapProductListToProductDTOList_withProductList_shouldMapToProductDTOList() {
        final List<Product> products = Instancio.ofList(Product.class)
                .withMaxDepth(2)
                .ignore(field(Product::getImages))
                .create();

        final List<ProductDTO> productDTOList = ProductMapper.INSTANCE.mapProductListToProductDTOList(products);

        assertProductDTOList(productDTOList, products);
    }

    @Test
    void mapPublishedProductListToProductDTOList_withProductList_shouldMapOnlyPublishedProductDTOList() {
        final List<Product> products = Instancio.ofList(Product.class)
                .withMaxDepth(2)
                .ignore(field(Product::getImages))
                .create();

        final List<Product> publishedProduct = products.stream()
                .filter(Product::isPublished)
                .collect(Collectors.toList());

        final List<ProductDTO> productDTOList = ProductMapper.INSTANCE
                .mapPublishedProductListToProductDTOList(publishedProduct);

        assertProductDTOList(productDTOList, publishedProduct);
    }

    private void assertProductDTOList(final List<ProductDTO> productDTOList, final List<Product> products) {
        assertThat(productDTOList).hasSize(products.size());
        int index = 0;
        for(final ProductDTO productDTO : productDTOList) {
            assertProductDTO(productDTO, products.get(index));
            index++;
        }
    }

    private void assertProductDTO(final ProductDTO productDTO, final Product product) {
        assertThat(productDTO).isNotNull();
        assertThat(productDTO.getId()).isEqualTo(product.getId());
        assertThat(productDTO.getDescription()).isEqualTo(product.getDescription());
        assertThat(productDTO.getName()).isEqualTo(product.getName());
        assertThat(productDTO.getPrice()).isEqualTo(product.getPrice());
        assertThat(productDTO.getStoreId()).isEqualTo(product.getStore().getId());
        assertThat(productDTO.getSubcategoryId()).isEqualTo(product.getSubcategory().getId());
        assertThat(productDTO.getSubcategoryName()).isEqualTo(product.getSubcategory().getName());
    }

}
