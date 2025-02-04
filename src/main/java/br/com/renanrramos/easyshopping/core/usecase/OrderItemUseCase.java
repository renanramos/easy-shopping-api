package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.OrderItemDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.OrderItemForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;

public interface OrderItemUseCase {

    OrderItemDTO save(final OrderItemForm orderItemForm);

    PageResponse<OrderItemDTO> findAll(final ParametersRequest parametersRequest,
                                       final String searchKey);

    OrderItemDTO findById(final Long orderItemId);

    OrderItemDTO update(final OrderItemForm orderItemForm, final Long orderItemId);

    void remove(final Long orderItemId);

    PageResponse<OrderItemDTO> findOrderItemByOrderId(final Long orderId);

    PageResponse<OrderItemDTO> getOrderItemStatistic();
}
