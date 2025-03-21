package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.ProductCategory;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductCategoryDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductCategoryForm;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductCategoryEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductCategoryMapper {

    ProductCategoryMapper INSTANCE = Mappers.getMapper(ProductCategoryMapper.class);

    @Named("mapProductCategoryToProductCategoryDTO")
    ProductCategoryDTO mapProductCategoryToProductCategoryDTO(final ProductCategory productCategory);

    @Named("mapProductCategoryListToProductCategoryDTOList")
    default List<ProductCategoryDTO> mapProductCategoryListToProductCategoryDTOList(
            final List<ProductCategory> productCategories) {
        return productCategories
                .stream()
                .map(ProductCategoryMapper.INSTANCE::mapProductCategoryToProductCategoryDTO)
                .collect(Collectors.toList());
    }

    @Named("mapProductCategoryFormToProductCategory")
    ProductCategory mapProductCategoryFormToProductCategory(final ProductCategoryForm productCategoryForm);

    @Named("mapProductCategoryFormToUpdateProductCategory")
    void mapProductCategoryFormToUpdateProductCategory(@MappingTarget ProductCategory productCategory,
                                                       final ProductCategoryForm productCategoryForm);

    @Named("mapProductCategoryToProductCategoryEntity")
    ProductCategoryEntity mapProductCategoryToProductCategoryEntity(final ProductCategory productCategory);

    @Named("mapProductCategoryEntityToProductCategory")
    ProductCategory mapProductCategoryEntityToProductCategory(final ProductCategoryEntity productCategoryEntityCreated);

    @Named("mapProductCategoryEntityListToProductCategoryList")
    default List<ProductCategory> mapProductCategoryEntityListToProductCategoryList(final List<ProductCategoryEntity> content) {
        return content
                .stream()
                .map(ProductCategoryMapper.INSTANCE::mapProductCategoryEntityToProductCategory)
                .collect(Collectors.toList());
    }

    @Named("mapProductCategoryToUpdateProductCategoryEntity")
    void mapProductCategoryToUpdateProductCategoryEntity(@MappingTarget ProductCategoryEntity productCategoryEntity,
                                                         final ProductCategory productCategory);
}
