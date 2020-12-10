package br.com.renanrramos.easyshopping.model.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.model.OrderItem;
import br.com.renanrramos.easyshopping.model.Purchase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PurchaseStatisticDTO {

	private Order order;

	private Purchase purchase;

	public PurchaseStatisticDTO() {
		// Intentionally empty
	}

	public PurchaseStatisticDTO(OrderItem item) {
		this.order = Optional.ofNullable(item.getOrder()).orElse(new Order());
		this.purchase = Optional.ofNullable(item.getOrder().getPurchase()).orElse(new Purchase());
	}
	
	public static List<PurchaseStatisticDTO> converterOrderItemToOrderItemStatisticDTO(List<OrderItem> items) {
		return items.stream().map(PurchaseStatisticDTO::new).collect(Collectors.toList());
	}

	
}
