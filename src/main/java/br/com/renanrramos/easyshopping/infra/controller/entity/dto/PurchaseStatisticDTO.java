package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.Order;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.Purchase;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PurchaseStatisticDTO {

	private Order order;

	private Purchase purchase;

	public PurchaseStatisticDTO() {
		// Intentionally empty
	}
}
