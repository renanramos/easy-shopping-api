/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 23/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.renanrramos.easyshopping.infra.config.util.EasyShoppingUtils;
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
public class ProductImageDTO {

	@JsonIgnore
	private EasyShoppingUtils utils = new EasyShoppingUtils();

	@ApiModelProperty(hidden = true)
	private Long id;

	private String description;

	private byte[] picture;

	private Long productId;

}
