package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.infra.controller.rest.dto.StoreDTO;
import br.com.renanrramos.easyshopping.infra.controller.rest.form.StoreForm;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StoreMapper {

    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    @Named("mapStoreToStoreDTO")
    StoreDTO mapStoreToStoreDTO(final Store store);

    @Named("mapStoreListToStoreDTOList")
    default List<StoreDTO> mapStoreListToStoreDTOList(final List<Store> storeList) {
        return storeList
                .stream()
                .map(StoreMapper.INSTANCE::mapStoreToStoreDTO)
                .collect(Collectors.toList());
    }

    @Named("mapStoreFormToStore")
    Store mapStoreFormToStore(final StoreForm storeForm);

    @Named("mapStoreFormToUpdateStore")
    void mapStoreFormToUpdateStore(@MappingTarget Store store, final StoreForm storeForm);
}
