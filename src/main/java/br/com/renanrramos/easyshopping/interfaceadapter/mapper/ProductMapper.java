package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Named("mapProductToProductDTO")
    @Mapping(target = "storeId", source = "product.store.id")
    @Mapping(target = "subcategoryId", source = "product.subcategory.id")
    @Mapping(target = "subcategoryName", source = "product.subcategory.name")
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
}

