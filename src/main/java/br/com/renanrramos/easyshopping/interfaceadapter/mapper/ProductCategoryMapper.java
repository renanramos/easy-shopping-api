package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.model.dto.ProductCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
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
}
