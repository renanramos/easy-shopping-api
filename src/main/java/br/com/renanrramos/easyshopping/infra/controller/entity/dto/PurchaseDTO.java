/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 02/12/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class PurchaseDTO {

	@ApiModelProperty(hidden = true)
	private Long id;

	private String customerId;

	private Long orderId;

	private Long addressId;

	private Long creditCardId;

	private LocalDateTime date;

}
