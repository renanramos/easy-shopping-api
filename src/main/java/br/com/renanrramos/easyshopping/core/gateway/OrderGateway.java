package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.Order;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;

public interface OrderGateway {

    Order save(final Order order);

    PageResponse<Order> findAllPageable(final Integer pageNumber,
                                        final Integer pageSize,
                                        final String sortBy,
                                        final String searchKey);

    Order findById(final Long orderId);

    Order update(final Order order, final Long orderId);

    void remove(final Long orderId);

    PageResponse<Order> findCustomerOrders(final String orderCustomerId);
}
