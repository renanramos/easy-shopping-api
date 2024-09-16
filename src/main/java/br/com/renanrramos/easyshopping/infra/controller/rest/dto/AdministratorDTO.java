/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest.dto;

import br.com.renanrramos.easyshopping.core.domain.enums.Profile;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AdministratorDTO {
	
	private Long id;

	private String name;

	private String email;

	private boolean isActive;

	private Profile profile;
	
	public AdministratorDTO() {
		// Intentionally empty
	}
}
