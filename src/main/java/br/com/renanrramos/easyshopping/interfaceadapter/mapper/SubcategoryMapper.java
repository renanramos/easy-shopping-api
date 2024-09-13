package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Subcategory;
import br.com.renanrramos.easyshopping.model.dto.SubcategoryDTO;
import br.com.renanrramos.easyshopping.model.form.SubcategoryForm;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
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

    @Named("mapSubcategoryFormToSubcategory")
    @Mapping(target = "productCategory.id", source = "productCategoryId")
    Subcategory mapSubcategoryFormToSubcategory(final SubcategoryForm subcategoryForm);

    @Named("mapSubcategoryFormToUpdateSubcategory")
    @Mapping(target = "productCategory.id", source = "productCategoryId")
    void mapSubcategoryFormToUpdateSubcategory(@MappingTarget Subcategory subcategory, SubcategoryForm subcategoryForm);
}
