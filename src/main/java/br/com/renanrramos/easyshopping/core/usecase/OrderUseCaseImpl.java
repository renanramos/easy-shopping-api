package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.Order;
import br.com.renanrramos.easyshopping.core.gateway.OrderGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.OrderDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.OrderForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.OrderMapper;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class OrderUseCaseImpl implements OrderUseCase {

    private final OrderGateway orderGateway;

    private final AuthenticationService authenticationService;

    @Override
    public OrderDTO save(final OrderForm orderForm) {
        final Order order = orderGateway.save(OrderMapper.INSTANCE.mapOrderFormToOrder(orderForm));
        return OrderMapper.INSTANCE.mapOrderToOrderDTO(order);
    }

    @Override
    public PageResponse<OrderDTO> findAllPageable(final ParametersRequest parametersRequest, final String searchKey) {
        final Page<Order> orderPage = orderGateway.findAllPageable(parametersRequest, searchKey);
        return PageResponse.buildPageResponse(orderPage,
                OrderMapper.INSTANCE.mapOrderListToOrderDTOList(orderPage.getContent()));
    }

    @Override
    public OrderDTO findById(final Long orderId) {
        final Order order = orderGateway.findById(orderId);
        return OrderMapper.INSTANCE.mapOrderToOrderDTO(order);
    }

    @Override
    public OrderDTO update(final OrderForm orderForm, final Long orderId) {
        final Order updatedOrder = orderGateway.update(OrderMapper.INSTANCE.mapOrderFormToOrder(orderForm), orderId);
        return OrderMapper.INSTANCE.mapOrderToOrderDTO(updatedOrder);
    }

    @Override
    public void remove(final Long orderId) {
        orderGateway.remove(orderId);
    }

    @Override
    public PageResponse<OrderDTO> findCustomerOrders() {
        final Page<Order> customerOrders = orderGateway.findCustomerOrders(authenticationService.getName());
        return PageResponse.buildPageResponse(customerOrders,
                OrderMapper.INSTANCE.mapOrderListToOrderDTOList(customerOrders.getContent()));
    }
}
