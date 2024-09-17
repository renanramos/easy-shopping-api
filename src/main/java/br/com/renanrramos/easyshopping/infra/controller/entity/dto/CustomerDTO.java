/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

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
public class CustomerDTO {

	private Long id;

	private String name;

	private String cpf;

	private String email;

	private String tokenId;

	private Profile profile;

	private boolean isSync;

	public CustomerDTO() {
		// Intentionally empty
	}
}
