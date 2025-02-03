package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.OrderDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.OrderForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;

public interface OrderDelegate {
    OrderDTO save(final OrderForm orderForm);

    PageResponse<OrderDTO> findAllPageable(final ParametersRequest parametersRequest,
                                           final String searchKey);

    OrderDTO findById(final Long orderId);

    OrderDTO update(final OrderForm orderForm, final Long orderId);

    void remove(final Long orderId);

    PageResponse<OrderDTO> findCustomerOrders();
}
