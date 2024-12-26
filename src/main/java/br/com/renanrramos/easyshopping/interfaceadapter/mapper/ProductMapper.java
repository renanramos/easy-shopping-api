package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.Product;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductForm;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Named("mapProductToProductDTO")
    @Mapping(target = "storeId", source = "product.store.id")
    @Mapping(target = "subCategoryId", source = "product.subCategory.id")
    @Mapping(target = "subCategoryName", source = "product.subCategory.name")
    ProductDTO mapProductToProductDTO(final Product product);

    @Named("mapProductListToProductDTOList")
    default List<ProductDTO> mapProductListToProductDTOList(final List<Product> products) {
        return products
                .stream()
                .map(ProductMapper.INSTANCE::mapProductToProductDTO)
                .collect(Collectors.toList());
    }

    @Named("mapPublishedProductListToProductDTOList")
    default List<ProductDTO> mapPublishedProductListToProductDTOList(final List<Product> publishedProduct) {
        return publishedProduct.stream()
                .filter(Product::isPublished)
                .map(ProductMapper.INSTANCE::mapProductToProductDTO)
                .collect(Collectors.toList());
    }

    @Named("mapProductFormToProduct")
    @Mapping(target = "store.id", source = "storeId")
    @Mapping(target = "subCategory.id", source = "productSubCategoryId")
    Product mapProductFormToProduct(final ProductForm productForm);

    @Named("mapProductFormToUpdateProduct")
    @Mapping(target = "store.id", source = "storeId")
    @Mapping(target = "subCategory.id", source = "productSubCategoryId")
    void mapProductFormToUpdateProduct(@MappingTarget Product product, final ProductForm productForm);
}

