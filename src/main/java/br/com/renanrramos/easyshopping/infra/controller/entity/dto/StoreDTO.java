/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

import lombok.EqualsAndHashCode;
import lombok.Data;
import lombok.ToString;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StoreDTO {

	private Long id;

	private String name;

	private String registeredNumber;

	private String corporateName;

	private String companyId;

	private String companyName;

	public StoreDTO() {
		// Intentionally empty
	}
}
