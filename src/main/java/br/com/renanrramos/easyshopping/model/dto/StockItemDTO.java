/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.model.StockItem;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author renan.ramos
 *
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StockItemDTO {

	private Long id;

	private Long productId;

	private Double maxAmount;

	private Double minAmount;

	private Integer currentAmount;

	private Long stockId;

	private String stockName;

	private String productName;

	public StockItemDTO() {
		// Intentionally empty
	}

	public StockItemDTO(StockItem item) {
		this.id = item.getId();
		this.productId = item.getProductId();
		this.maxAmount = item.getMaxAmount();
		this.minAmount = item.getMinAmount();
		this.currentAmount = item.getCurrentAmount();
		this.stockId = item.getStock().getId();
		this.stockName = item.getStock().getName();
		this.productName = item.getProductName();
	}

	public static List<StockItemDTO> converterStockItemListToStockItemDTOList(List<StockItem> items) {
		return items.stream().map(StockItemDTO::new).collect(Collectors.toList());
	}

	public static StockItemDTO converterStockItemToStockItemDTO(StockItem item) {
		return new StockItemDTO(item);
	}
}
