package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.ProductImage;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductImageDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductImageForm;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductImageEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductImageMapper {

    ProductImageMapper INSTANCE = Mappers.getMapper(ProductImageMapper.class);

    @Named("mapProductImageToProductImageDTO")
    @Mapping(target = "productId", source = "product.id")
    ProductImageDTO mapProductImageToProductImageDTO(final ProductImage productImage);

    @Named("mapProductImageListToProductImageDTOList")
    default List<ProductImageDTO> mapProductImageListToProductImageDTOList(final List<ProductImage> productImageList) {
        return productImageList
                .stream()
                .map(ProductImageMapper.INSTANCE::mapProductImageToProductImageDTO)
                .collect(Collectors.toList());
    }

    @Named("mapProductImageFormToProductImage")
    @Mapping(target = "product.id", source = "productId")
    ProductImage mapProductImageFormToProductImage(final ProductImageForm productImageForm);

    @Named("mapProductImageEntityToProductImage")
    ProductImage mapProductImageEntityToProductImage(final ProductImageEntity productImageEntity);

    @Named("mapProductImageToUpdateProductImageEntity")
    void mapProductImageToUpdateProductImageEntity(@MappingTarget final ProductImageEntity productImageEntity, final ProductImage productImage);

    @Named("mapProductImageToProductImageEntity")
    ProductImageEntity mapProductImageToProductImageEntity(final ProductImage productImage);

    @Named("mapProductImageEntityListToProductImageList")
    default List<ProductImage> mapProductImageEntityListToProductImageList(final List<ProductImageEntity> productImageEntities) {
        return productImageEntities
                .stream()
                .map(ProductImageMapper.INSTANCE::mapProductImageEntityToProductImage)
                .collect(Collectors.toList());
    }

    ;
}
