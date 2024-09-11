package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.StockItem;
import br.com.renanrramos.easyshopping.model.dto.StockItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockItemMapper {

    StockItemMapper INSTANCE = Mappers.getMapper(StockItemMapper.class);

    @Named("mapStockItemToStockItemDTO")
    @Mapping(target = "stockId", source = "stock.id")
    @Mapping(target = "stockName", source = "stock.name")
    @Mapping(target = "storeId", source = "stock.store.id")
    StockItemDTO mapStockItemToStockItemDTO(final StockItem stockItem);

    @Named("mapStockItemListToStockItemDTOList")
    default List<StockItemDTO> mapStockItemListToStockItemDTOList(final List<StockItem> stockItems) {
        return stockItems
                .stream()
                .map(StockItemMapper.INSTANCE::mapStockItemToStockItemDTO)
                .collect(Collectors.toList());
    }
}