package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.Order;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import org.springframework.data.domain.Page;

public interface OrderGateway {

    Order save(final Order order);

    Page<Order> findAllPageable(final ParametersRequest parametersRequest,
                                final String searchKey);

    Order findById(final Long orderId);

    Order update(final Order order, final Long orderId);

    void remove(final Long orderId);

    Page<Order> findCustomerOrders(final String orderCustomerId);
}
