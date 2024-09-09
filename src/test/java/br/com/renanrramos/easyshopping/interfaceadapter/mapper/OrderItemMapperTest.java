package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.OrderItem;
import br.com.renanrramos.easyshopping.model.dto.OrderItemDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemMapperTest {

    @Test
    void mapOrderItemToOrderItemDTO_withOrderItem_shouldMapOrderItemDTO() {
        final OrderItem orderItem = Instancio.create(OrderItem.class);

        final OrderItemDTO orderItemDTO= OrderItemMapper.INSTANCE.mapOrderItemToOrderItemDTO(orderItem);

        assertOrderItemDTO(orderItemDTO, orderItem);
    }

    @Test
    void mapOrderItemListToOrderItemDTOList_withOrderItemList_shouldMapOrderItemDTOList() {
        final List<OrderItem> orderItemList = Instancio.ofList(OrderItem.class).size(10).create();

        final List<OrderItemDTO> orderItemDTOList = OrderItemMapper.INSTANCE
                .mapOrderItemListToOrderItemDTOList(orderItemList);

        assertOrderItemDTOList(orderItemDTOList, orderItemList);
    }

    private void assertOrderItemDTOList(final List<OrderItemDTO> orderItemDTOList,
                                        final List<OrderItem> orderItemList) {

        assertThat(orderItemDTOList).hasSize(orderItemList.size());
        int index = 0;

        for(final OrderItemDTO orderItemDTO : orderItemDTOList) {
            assertOrderItemDTO(orderItemDTO, orderItemList.get(index));
            index++;
        }
    }


    private void assertOrderItemDTO(final OrderItemDTO orderItemDTO, final OrderItem orderItem) {
        assertThat(orderItemDTO).isNotNull();
        assertThat(orderItemDTO.getOrderId()).isEqualTo(orderItem.getOrder().getId());
        assertThat(orderItemDTO.getTotal()).isEqualTo(orderItem.getTotal());
        assertThat(orderItemDTO.getPrice()).isEqualTo(orderItem.getPrice());
        assertThat(orderItemDTO.getAmount()).isEqualTo(orderItem.getAmount());
        assertThat(orderItemDTO.getProductId()).isEqualTo(orderItem.getProductId());
        assertThat(orderItemDTO.getProductName()).isEqualTo(orderItem.getProductName());
        assertThat(orderItemDTO.getId()).isEqualTo(orderItem.getId());
    }

}