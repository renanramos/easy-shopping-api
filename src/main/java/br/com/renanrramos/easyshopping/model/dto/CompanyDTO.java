/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 30/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import br.com.renanrramos.easyshopping.enums.Profile;
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
public class CompanyDTO {

	private Long id;

	private String name;

	private String registeredNumber;

	private String email;

	private String phone;

	private Profile profile;

	private boolean isSync;

	private String tokenId;

	public CompanyDTO() {
		// Intentionally empty
	}
}
