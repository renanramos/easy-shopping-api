package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.OrderItem;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import org.springframework.data.domain.Page;

public interface OrderItemGateway {

    OrderItem save(final OrderItem orderItem);

    Page<OrderItem> findAll(final ParametersRequest parametersRequest,
                            final String searchKey);

    OrderItem findById(final Long orderItemId);

    OrderItem update(final OrderItem orderItem, final Long orderItemId);

    void remove(final Long orderItemId);

    Page<OrderItem> findOrderItemByOrderId(final Long orderId);

    Page<OrderItem> getOrderItemStatistic();
}
