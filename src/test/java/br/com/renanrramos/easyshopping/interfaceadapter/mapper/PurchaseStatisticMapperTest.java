package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.PurchaseStatisticDTO;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.OrderItemEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PurchaseStatisticMapperTest {


    @Test
    void mapOrderItemToPurchaseStatisticDTO_withOrderItem_shouldMapToPurchaseStatisticDTO() {
        final OrderItemEntity orderItemEntity = Instancio.of(OrderItemEntity.class).create();

        final PurchaseStatisticDTO purchaseStatisticDTO = PurchaseStatisticMapper.INSTANCE
                .mapOrderItemToPurchaseStatisticDTO(orderItemEntity);

        assertPurchaseStatisticDTO(purchaseStatisticDTO, orderItemEntity);

    }

    @Test
    void mapOrderItemListToPurchaseStatisticDTOList_withOrderItemList_shouldMapToPurchaseStatisticDTOList() {
        final List<OrderItemEntity> orderItemEntityList = Instancio.ofList(OrderItemEntity.class).create();

        final List<PurchaseStatisticDTO> purchaseStatisticDTOList = PurchaseStatisticMapper.INSTANCE
                .mapOrderItemListToPurchaseStatisticDTOList(orderItemEntityList);

        assertPurchaseStatisticDTOList(purchaseStatisticDTOList, orderItemEntityList);

    }

    private void assertPurchaseStatisticDTOList(final List<PurchaseStatisticDTO> purchaseStatisticDTOList,
                                                final List<OrderItemEntity> orderItemEntityList) {
        assertThat(purchaseStatisticDTOList).hasSize(orderItemEntityList.size());
        int index = 0;
        for (final PurchaseStatisticDTO purchaseStatisticDTO : purchaseStatisticDTOList) {
            assertPurchaseStatisticDTO(purchaseStatisticDTO, orderItemEntityList.get(index));
            index++;
        }
    }

    private void assertPurchaseStatisticDTO(final PurchaseStatisticDTO purchaseStatisticDTO,
                                            final OrderItemEntity orderItemEntity) {

        assertThat(purchaseStatisticDTO).isNotNull();
        assertThat(purchaseStatisticDTO.getPurchase()).isNotNull();
        assertThat(purchaseStatisticDTO.getOrder()).isNotNull();
        assertThat(purchaseStatisticDTO.getOrder().getId()).isEqualTo(orderItemEntity.getOrder().getId());
        assertThat(purchaseStatisticDTO.getOrder().getOrderNumber())
                .isEqualTo(orderItemEntity.getOrder().getOrderNumber());
    }

}