package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.StockItem;
import br.com.renanrramos.easyshopping.model.dto.StockItemDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StockItemMapperTest {

    @Test
    void mapStockItemToStockItemDTO_withStockItem_shouldMapStockItemDTO() {
        final StockItem stockItem = Instancio.of(StockItem.class)
                .withMaxDepth(3)
                .create();

        final StockItemDTO stockItemDTO = StockItemMapper.INSTANCE.mapStockItemToStockItemDTO(stockItem);

        assertStockItemDTO(stockItemDTO, stockItem);
    }

    @Test
    void mapStockItemListToStockItemDTOList_withStockItemList_shouldMapStockItemDTOList() {
        final List<StockItem> stockItems = Instancio.ofList(StockItem.class)
                .withMaxDepth(3)
                .create();

        final List<StockItemDTO> stockItemDTOList = StockItemMapper.INSTANCE.mapStockItemListToStockItemDTOList(stockItems);

        assertStockItemDTOList(stockItemDTOList, stockItems);
    }

    private void assertStockItemDTOList(final List<StockItemDTO> stockItemDTOList,
                                        final List<StockItem> stockItems) {
        assertThat(stockItemDTOList).hasSize(stockItems.size());
        int index = 0;
        for (final StockItemDTO stockItemDTO : stockItemDTOList) {
            assertStockItemDTO(stockItemDTO, stockItems.get(index));
            index++;
        }
    }

    private void assertStockItemDTO(final StockItemDTO stockItemDTO, final StockItem stockItem) {
        assertThat(stockItemDTO).isNotNull();
        assertThat(stockItemDTO.getStockId()).isEqualTo(stockItem.getStock().getId());
        assertThat(stockItemDTO.getStockName()).isEqualTo(stockItem.getStock().getName());
        assertThat(stockItemDTO.getMinAmount()).isEqualTo(stockItem.getMinAmount());
        assertThat(stockItemDTO.getProductId()).isEqualTo(stockItem.getProductId());
        assertThat(stockItemDTO.getMaxAmount()).isEqualTo(stockItem.getMaxAmount());
        assertThat(stockItemDTO.getCurrentAmount()).isEqualTo(stockItem.getCurrentAmount());
        assertThat(stockItemDTO.getProductName()).isEqualTo(stockItem.getProductName());
        assertThat(stockItemDTO.getStoreId()).isEqualTo(stockItem.getStock().getStore().getId());
    }
}