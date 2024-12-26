package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.interfaceadapter.domain.OrderItem;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.OrderItemDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.OrderItemForm;
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

    @Test
    void mapOrderItemFormToOrderItem_withOrderItemForm_shouldMapOrderItemFormToOrderItem() {
        final OrderItemForm orderItemForm = Instancio.of(OrderItemForm.class).create();

        final OrderItem orderItem = OrderItemMapper.INSTANCE.mapOrderItemFormToOrderItem(orderItemForm);

        assertOrderItem(orderItem, orderItemForm);
    }

    private void assertOrderItem(final OrderItem orderItem, final OrderItemForm orderItemForm) {
        assertThat(orderItem).isNotNull();
        assertThat(orderItem.getOrder().getId()).isEqualTo(orderItemForm.getOrderId());
        assertThat(orderItem.getProductName()).isEqualTo(orderItemForm.getProductName());
        assertThat(orderItem.getTotal()).isEqualTo(orderItemForm.getTotal());
        assertThat(orderItem.getAmount()).isEqualTo(orderItemForm.getAmount());
        assertThat(orderItem.getPrice()).isEqualTo(orderItemForm.getPrice());
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