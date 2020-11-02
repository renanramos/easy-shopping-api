/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import java.util.Optional;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Administrator;
import br.com.renanrramos.easyshopping.model.builder.AdministratorBuilder;
import io.swagger.annotations.ApiModelProperty;
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

	private String name;

	private String email;
	@ApiModelProperty(hidden = true)
	private Profile profile;

	public AdministratorForm() {
		// Intentionally empty
	}

	public AdministratorForm(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.profile = Profile.ADMINISTRATOR;
	}

	public static Administrator converterAdministratorFormToAdministrator(AdministratorForm administrator) {
		return AdministratorBuilder.builder()
				.withName(administrator.getName())
				.withEmail(administrator.getEmail())
				.build();
	}

	public static Administrator converterAdministratorFormUpdateToAmdinistrator(AdministratorForm administratorForm, Administrator administrator) {
		return AdministratorBuilder.builder()
				.withName(Optional.ofNullable(administratorForm.getName()).orElse(administrator.getName()))
				.withEmail(Optional.ofNullable(administratorForm.getEmail()).orElse(administrator.getEmail()))
				.build();
	}

	@Override
	public String toString() {
		return "AdministratorForm [name=" + name + ", email=" + email + ", profile=" + profile.name() + "]";
	}
}
