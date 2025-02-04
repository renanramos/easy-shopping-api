package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.OrderItem;
import br.com.renanrramos.easyshopping.core.gateway.OrderGateway;
import br.com.renanrramos.easyshopping.core.gateway.OrderItemGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.OrderItemDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.OrderItemForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.OrderItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class OrderItemUseCaseImpl implements OrderItemUseCase {

    private final OrderItemGateway orderItemGateway;

    private final OrderGateway orderGateway;

    @Override
    public OrderItemDTO save(final OrderItemForm orderItemForm) {
        orderGateway.validateOrderId(orderItemForm.getOrderId());
        final OrderItem orderItem =
                orderItemGateway.save(OrderItemMapper.INSTANCE.mapOrderItemFormToOrderItem(orderItemForm));
        return OrderItemMapper.INSTANCE.mapOrderItemToOrderItemDTO(orderItem);
    }

    @Override
    public PageResponse<OrderItemDTO> findAll(final ParametersRequest parametersRequest, final String searchKey) {
        Page<OrderItem> orderItemPage = orderItemGateway.findAll(parametersRequest, searchKey);
        return new PageResponse<>(orderItemPage.getTotalElements(), orderItemPage.getTotalPages(),
                OrderItemMapper.INSTANCE.mapOrderItemListToOrderItemDTOList(orderItemPage.getContent()));
    }

    @Override
    public OrderItemDTO findById(final Long orderItemId) {
        return OrderItemMapper.INSTANCE.mapOrderItemToOrderItemDTO(orderItemGateway.findById(orderItemId));
    }

    @Override
    public OrderItemDTO update(final OrderItemForm orderItemForm, final Long orderItemId) {
        final OrderItem orderItem = OrderItemMapper.INSTANCE.mapOrderItemFormToOrderItem(orderItemForm);
        final OrderItem orderItemUpdated = orderItemGateway.update(orderItem, orderItemId);
        return OrderItemMapper.INSTANCE.mapOrderItemToOrderItemDTO(orderItemUpdated);
    }

    @Override
    public void remove(final Long orderItemId) {
        orderItemGateway.remove(orderItemId);
    }

    @Override
    public PageResponse<OrderItemDTO> findOrderItemByOrderId(final Long orderId) {
        Page<OrderItem> orderItemByOrderId = orderItemGateway.findOrderItemByOrderId(orderId);
        return new PageResponse<>(orderItemByOrderId.getTotalElements(), orderItemByOrderId.getTotalPages(),
                OrderItemMapper.INSTANCE.mapOrderItemListToOrderItemDTOList(orderItemByOrderId.getContent()));
    }

    @Override
    public PageResponse<OrderItemDTO> getOrderItemStatistic() {
        final Page<OrderItem> orderItemPage = orderItemGateway.getOrderItemStatistic();
        return new PageResponse<>(orderItemPage.getTotalElements(), orderItemPage.getTotalPages(),
                OrderItemMapper.INSTANCE.mapOrderItemListToOrderItemDTOList(orderItemPage.getContent()));
    }

}
