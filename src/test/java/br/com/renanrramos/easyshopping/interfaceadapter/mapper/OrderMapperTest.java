package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.model.dto.OrderDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderMapperTest {

    @Test
    void mapOrderToOrderDTO_withOrder_shouldMapOrderDTO() {
        final Order order = Instancio.create(Order.class);

        final OrderDTO orderDTO = OrderMapper.INSTANCE.mapOrderToOrderDTO(order);

        assertOrderDTO(orderDTO, order);
    }

    @Test
    void mapOrderListToOrderDTOList_withOrderList_shouldMapOrderDTOList() {
        final List<Order> orders = Instancio.ofList(Order.class).size(10).create();

        final List<OrderDTO> orderDTOS = OrderMapper.INSTANCE.mapOrderListToOrderDTOList(orders);

        assertOrderDTOList(orderDTOS, orders);
    }

    private void assertOrderDTOList(final List<OrderDTO> orderDTOS, final List<Order> orders) {
        assertThat(orderDTOS).hasSize(orders.size());
        int index = 0;
        for(final OrderDTO orderDTO : orderDTOS) {
            assertOrderDTO(orderDTO, orders.get(index));
            index++;
        }
    }

    private static void assertOrderDTO(final OrderDTO orderDTO, final Order order) {
        assertThat(orderDTO).isNotNull();
        assertThat(orderDTO.getOrderNumber()).isEqualTo(order.getOrderNumber());
        assertThat(orderDTO.getId()).isEqualTo(order.getId());
        assertThat(orderDTO.getCustomerId()).isEqualTo(order.getCustomerId());
        assertThat(orderDTO.isFinished()).isEqualTo(order.isFinished());
    }
}