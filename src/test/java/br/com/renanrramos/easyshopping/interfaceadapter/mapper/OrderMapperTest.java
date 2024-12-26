package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.interfaceadapter.domain.Order;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.OrderDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.OrderForm;
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

    @Test
    void mapOrderFormToOrder_withOrderForm_shouldMapToOrder() {
        final OrderForm orderForm = Instancio.of(OrderForm.class).create();

        final Order order = OrderMapper.INSTANCE.mapOrderFormToOrder(orderForm);

        assertOrder(order, orderForm);
    }

    @Test
    void mapOrderFormToUpdateOrder_whenOrderFormUpdateOperation_shouldMapToOrderOnlyDifferentFields() {
        // Arrange
        final OrderForm orderForm = Instancio.of(OrderForm.class)
                .create();
        final Order order = OrderMapper.INSTANCE.mapOrderFormToOrder(orderForm);
        orderForm.setOrderNumber(null);
        // Act
        OrderMapper.INSTANCE.mapOrderFormToUpdateOrder(order, orderForm);
        // Assert
        assertThat(order).isNotNull();
        assertThat(order.getCustomerId()).isEqualTo(orderForm.getCustomerId());
        assertThat(order.getOrderNumber()).isNotNull();
        assertThat(order.getId()).isEqualTo(orderForm.getId());
    }

    private void assertOrder(final Order order, final OrderForm orderForm) {
        assertThat(order).isNotNull();
        assertThat(order.getCustomerId()).isEqualTo(orderForm.getCustomerId());
        assertThat(order.getOrderNumber()).isEqualTo(orderForm.getOrderNumber());
        assertThat(order.getId()).isEqualTo(orderForm.getId());
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