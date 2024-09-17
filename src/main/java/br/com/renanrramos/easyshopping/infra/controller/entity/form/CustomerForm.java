/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.form;

import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
import io.swagger.annotations.ApiModelProperty;

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
public class CustomerForm {

	private String name;

	private String email;
	
	private String cpf;
	
	@ApiModelProperty(hidden = true)
	private Profile profile;

	private String password;

	public CustomerForm() {
		// Intentionally empty
	}
}
