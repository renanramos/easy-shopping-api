package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.SubCategory;
import br.com.renanrramos.easyshopping.infra.controller.rest.dto.SubCategoryDTO;
import br.com.renanrramos.easyshopping.infra.controller.rest.form.SubCategoryForm;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubCategoryMapper {

    SubCategoryMapper INSTANCE = Mappers.getMapper(SubCategoryMapper.class);

    @Named("mapSubCategoryToSubCategoryDTO")
    @Mapping(target = "productCategoryId", source = "productCategory.id")
    @Mapping(target = "productCategoryName", source = "productCategory.name")
    SubCategoryDTO mapSubCategoryToSubCategoryDTO(final SubCategory subcategory);

    @Named("mapSubCategoryListToSubCategoryDTOList")
    default List<SubCategoryDTO> mapSubCategoryListToSubCategoryDTOList(final List<SubCategory> subcategoryList) {
        return subcategoryList
                .stream()
                .map(SubCategoryMapper.INSTANCE::mapSubCategoryToSubCategoryDTO)
                .collect(Collectors.toList());
    }

    @Named("mapSubCategoryFormToSubCategory")
    @Mapping(target = "productCategory.id", source = "productCategoryId")
    SubCategory mapSubCategoryFormToSubCategory(final SubCategoryForm subCategoryForm);

    @Named("mapSubCategoryFormToUpdateSubCategory")
    @Mapping(target = "productCategory.id", source = "productCategoryId")
    void mapSubCategoryFormToUpdateSubCategory(@MappingTarget SubCategory subCategory, SubCategoryForm subCategoryForm);
}
