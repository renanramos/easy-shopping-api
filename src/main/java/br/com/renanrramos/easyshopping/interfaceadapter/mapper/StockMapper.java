package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Stock;
import br.com.renanrramos.easyshopping.model.StockItem;
import br.com.renanrramos.easyshopping.model.dto.StockDTO;
import br.com.renanrramos.easyshopping.model.form.StockForm;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StockMapper {

    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    @Named("mapStockToStockDTO")
    @Mapping(target = "storeId", source = "store.id")
    @Mapping(target = "storeName", source = "store.name")
    @Mapping(target = "totalItems", source = "items", qualifiedByName = "mapStockItems")
    StockDTO mapStockToStockDTO(Stock stock);

    @Named("mapStockItems")
    default int mapStockItems(final List<StockItem> items) {
        return items.isEmpty() ? 0 : items.size();
    }

    @Named("mapStockListToStockDTOList")
    default List<StockDTO> mapStockListToStockDTOList(final List<Stock> stockList) {
        return stockList
                .stream()
                .map(StockMapper.INSTANCE::mapStockToStockDTO)
                .collect(Collectors.toList());
    }

    @Mapping(target = "store.id", source = "storeId")
    @Mapping(target = "store.name", source = "name")
    Stock mapStockFormToStock(final StockForm stockForm);

    @Named("mapStockFormToUpdateStock")
    @Mapping(target = "store.id", source = "storeId")
    void mapStockFormToUpdateStock(@MappingTarget Stock stock, final StockForm stockForm);
}