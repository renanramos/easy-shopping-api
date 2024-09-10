package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.dto.StoreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
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
}
