package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Subcategory;
import br.com.renanrramos.easyshopping.model.dto.SubcategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubcategoryMapper {

    SubcategoryMapper INSTANCE = Mappers.getMapper(SubcategoryMapper.class);

    @Named("mapSubcategoryToSubcategoryDTO")
    @Mapping(target = "productCategoryId", source = "productCategory.id")
    @Mapping(target = "productCategoryName", source = "productCategory.name")
    SubcategoryDTO mapSubcategoryToSubcategoryDTO(final Subcategory subcategory);

    @Named("mapSubcategoryListToSubcategoryDTOList")
    default List<SubcategoryDTO> mapSubcategoryListToSubcategoryDTOList(final List<Subcategory> subcategoryList) {
        return subcategoryList
                .stream()
                .map(SubcategoryMapper.INSTANCE::mapSubcategoryToSubcategoryDTO)
                .collect(Collectors.toList());
    }

}
