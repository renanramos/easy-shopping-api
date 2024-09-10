package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.ProductImage;
import br.com.renanrramos.easyshopping.model.dto.ProductImageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
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
}
