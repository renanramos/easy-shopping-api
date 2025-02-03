package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.core.usecase.OrderUseCase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.OrderDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.OrderForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderDelegateImpl implements OrderDelegate {

    private final OrderUseCase orderUseCase;

    @Override
    public OrderDTO save(final OrderForm orderForm) {
        return orderUseCase.save(orderForm);
    }

    @Override
    public PageResponse<OrderDTO> findAllPageable(final ParametersRequest parametersRequest, final String searchKey) {
        return orderUseCase.findAllPageable(parametersRequest, searchKey);
    }

    @Override
    public OrderDTO findById(final Long orderId) {
        return orderUseCase.findById(orderId);
    }

    @Override
    public OrderDTO update(final OrderForm orderForm, final Long orderId) {
        return orderUseCase.update(orderForm, orderId);
    }

    @Override
    public void remove(final Long orderId) {
        orderUseCase.remove(orderId);
    }

    @Override
    public PageResponse<OrderDTO> findCustomerOrders() {
        return orderUseCase.findCustomerOrders();
    }
}
