package br.com.renanrramos.easyshopping.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PurchaseStatistic {

	private Order order;

	private Purchase purchase;

	public PurchaseStatistic() {
		// Intentionally empty
	}
}
