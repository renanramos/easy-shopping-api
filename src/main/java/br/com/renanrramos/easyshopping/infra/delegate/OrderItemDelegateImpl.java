package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.core.usecase.OrderItemUseCase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.OrderItemDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.OrderItemForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderItemDelegateImpl implements OrderItemDelegate {

    private final OrderItemUseCase orderItemUseCase;

    @Override
    public OrderItemDTO save(final OrderItemForm orderItemForm) {
        return orderItemUseCase.save(orderItemForm);
    }

    @Override
    public PageResponse<OrderItemDTO> findAll(final ParametersRequest parametersRequest,
                                              final String searchKey) {
        return orderItemUseCase.findAll(parametersRequest, searchKey);
    }

    @Override
    public OrderItemDTO findById(final Long orderItemId) {
        return orderItemUseCase.findById(orderItemId);
    }

    @Override
    public OrderItemDTO update(final OrderItemForm orderItemForm, final Long orderItemId) {
        return orderItemUseCase.update(orderItemForm, orderItemId);
    }

    @Override
    public void remove(final Long orderItemId) {
        orderItemUseCase.remove(orderItemId);
    }

    @Override
    public PageResponse<OrderItemDTO> findOrderItemByOrderId(final Long orderId) {
        return orderItemUseCase.findOrderItemByOrderId(orderId);
    }

    @Override
    public PageResponse<OrderItemDTO> getOrderItemStatistic() {
        return orderItemUseCase.getOrderItemStatistic();
    }
}
