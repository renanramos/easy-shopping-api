package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Stock;
import br.com.renanrramos.easyshopping.infra.controller.rest.dto.StockDTO;
import br.com.renanrramos.easyshopping.infra.controller.rest.form.StockForm;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StockMapperTest {


    @Test
    void mapStockToStockDTO_withStock_shouldMapToStockDTO() {
        final Stock stock = Instancio.of(Stock.class)
                .withMaxDepth(2)
                .create();

        final StockDTO stockDTO = StockMapper.INSTANCE.mapStockToStockDTO(stock);

        assertStockDTO(stockDTO, stock);
    }

    @Test
    void mapStockListToStockDTOList_withStockList_shouldMapToStockDTOList() {
        final List<Stock> stockList = Instancio.ofList(Stock.class)
                .withMaxDepth(2)
                .create();

        final List<StockDTO> stockDTOList = StockMapper.INSTANCE.mapStockListToStockDTOList(stockList);

        assertStockDTOList(stockDTOList, stockList);
    }

    @Test
    void mapStockFormToStock_withStockForm_shouldMapToStock() {
        final StockForm stockForm = Instancio.of(StockForm.class).create();

        final Stock stock = StockMapper.INSTANCE.mapStockFormToStock(stockForm);

        assertStock(stock, stockForm);
    }

    @Test
    void mapStockFormToUpdateStock_withStockForm_shouldMapToStockOnlyDifferentFields() {
        final StockForm stockForm = Instancio.of(StockForm.class).create();
        stockForm.setName(null);
        final Stock stock = Instancio.of(Stock.class).create();
        final String stockName = "stockName";
        stock.setName(stockName);
        // Act
        StockMapper.INSTANCE.mapStockFormToUpdateStock(stock, stockForm);
        // Assert
        assertThat(stock).isNotNull();
        assertThat(stock.getStore().getId()).isEqualTo(stockForm.getStoreId());
        assertThat(stock.getName()).isEqualTo(stockName);
    }

    private void assertStock(final Stock stock, final StockForm stockForm) {
        assertThat(stock).isNotNull();
        assertThat(stock.getName()).isEqualTo(stockForm.getName());
        assertThat(stock.getStore().getId()).isEqualTo(stockForm.getStoreId());
    }

    private void assertStockDTOList(final List<StockDTO> stockDTOList,
                                    final List<Stock> stockList) {
        assertThat(stockDTOList).hasSize(stockList.size());
        int index = 0;
        for(final StockDTO stockDTO : stockDTOList) {
            assertStockDTO(stockDTO, stockList.get(index));
            index++;
        }
    }

    private void assertStockDTO(final StockDTO stockDTO, final Stock stock) {
        assertThat(stockDTO).isNotNull();
        assertThat(stockDTO.getId()).isEqualTo(stock.getId());
        assertThat(stockDTO.getName()).isEqualTo(stock.getName());
        assertThat(stockDTO.getStoreId()).isEqualTo(stock.getStore().getId());
        assertThat(stockDTO.getStoreName()).isEqualTo(stock.getStore().getName());
        assertThat(stockDTO.getTotalItems()).isEqualTo(stock.getItems().size());
    }


}