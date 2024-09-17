package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.OrderItem;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.PurchaseStatisticDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PurchaseStatisticMapperTest {


    @Test
    void mapOrderItemToPurchaseStatisticDTO_withOrderItem_shouldMapToPurchaseStatisticDTO() {
        final OrderItem orderItem = Instancio.of(OrderItem.class).create();

        final PurchaseStatisticDTO purchaseStatisticDTO = PurchaseStatisticMapper.INSTANCE
                .mapOrderItemToPurchaseStatisticDTO(orderItem);

        assertPurchaseStatisticDTO(purchaseStatisticDTO, orderItem);

    }

    @Test
    void mapOrderItemListToPurchaseStatisticDTOList_withOrderItemList_shouldMapToPurchaseStatisticDTOList() {
        final List<OrderItem> orderItemList = Instancio.ofList(OrderItem.class).create();

        final List<PurchaseStatisticDTO> purchaseStatisticDTOList = PurchaseStatisticMapper.INSTANCE
                .mapOrderItemListToPurchaseStatisticDTOList(orderItemList);

        assertPurchaseStatisticDTOList(purchaseStatisticDTOList, orderItemList);

    }

    private void assertPurchaseStatisticDTOList(final List<PurchaseStatisticDTO> purchaseStatisticDTOList,
                                                final List<OrderItem> orderItemList) {
        assertThat(purchaseStatisticDTOList).hasSize(orderItemList.size());
        int index = 0;
        for (final PurchaseStatisticDTO purchaseStatisticDTO : purchaseStatisticDTOList) {
            assertPurchaseStatisticDTO(purchaseStatisticDTO, orderItemList.get(index));
            index++;
        }
    }

    private void assertPurchaseStatisticDTO(final PurchaseStatisticDTO purchaseStatisticDTO,
                                            final OrderItem orderItem) {

        assertThat(purchaseStatisticDTO).isNotNull();
        assertThat(purchaseStatisticDTO.getPurchase()).isNotNull();
        assertThat(purchaseStatisticDTO.getOrder()).isNotNull();
        assertThat(purchaseStatisticDTO.getOrder().getId()).isEqualTo(orderItem.getOrder().getId());
        assertThat(purchaseStatisticDTO.getOrder().getOrderNumber())
                .isEqualTo(orderItem.getOrder().getOrderNumber());
    }

}