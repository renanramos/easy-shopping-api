/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class StockItemDTO {

	@ApiModelProperty(hidden = true)
	private Long id;

	private Long productId;

	private Double maxAmount;

	private Double minAmount;

	private Integer currentAmount;

	private Long stockId;

	private String stockName;

	private String productName;

	private Long storeId;
}
