/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.renanrramos.easyshopping.model.Administrator;
import br.com.renanrramos.easyshopping.model.builder.AdministratorBuilder;
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
public class AdministratorForm {

	@NotBlank
	@Size(max = 50)
	private String name;
	
	public AdministratorForm() {
		// Intentionally empty
	}

	public AdministratorForm(String name) {
		this.name = name;
	}

	public static Administrator converterAdministratorFormToAdministrator(AdministratorForm administrator) {
		return AdministratorBuilder.builder()
				.withName(administrator.getName())
				.build();
	}

	@Override
	public String toString() {
		return "AdministratorForm [name=" + name + "]";
	}
}
