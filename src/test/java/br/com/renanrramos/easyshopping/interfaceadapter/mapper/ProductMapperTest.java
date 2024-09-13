package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.ProductImage;
import br.com.renanrramos.easyshopping.model.dto.ProductDTO;
import br.com.renanrramos.easyshopping.model.form.ProductForm;
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

    @Test
    void mapProductFormToProduct_withProductForm_shouldMapToProduct() {
        final ProductForm productForm = Instancio.of(ProductForm.class).create();

        final Product product = ProductMapper.INSTANCE.mapProductFormToProduct(productForm);

        assertProduct(product, productForm);
    }

    @Test
    void mapProductFormToUpdateProduct_whenProductFormUpdateOperation_shouldMapToProductOnlyDifferentFields() {
        // Arrange
        final ProductForm productForm = Instancio.of(ProductForm.class).create();
        final Product product = ProductMapper.INSTANCE.mapProductFormToProduct(productForm);
        final String productName = "productName";
        productForm.setName(null);
        product.setName(productName);
        // Act
        ProductMapper.INSTANCE.mapProductFormToUpdateProduct(product, productForm);
        // Assert
        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo(productName);
        assertThat(product.getPrice()).isEqualTo(productForm.getPrice());
        assertThat(product.getDescription()).isEqualTo(productForm.getDescription());
        assertThat(product.getSubCategory().getId()).isEqualTo(productForm.getProductSubCategoryId());
    }

    private void assertProduct(final Product product, final ProductForm productForm) {
        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo(productForm.getName());
        assertThat(product.getPrice()).isEqualTo(productForm.getPrice());
        assertThat(product.getDescription()).isEqualTo(productForm.getDescription());
        assertThat(product.getSubCategory().getId()).isEqualTo(productForm.getProductSubCategoryId());
        assertThat(product.getStore().getId()).isEqualTo(productForm.getStoreId());
        assertThat(product.getCompanyId()).isEqualTo(productForm.getCompanyId());

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
        assertThat(productDTO.getSubCategoryId()).isEqualTo(product.getSubCategory().getId());
        assertThat(productDTO.getSubCategoryName()).isEqualTo(product.getSubCategory().getName());
    }

}
