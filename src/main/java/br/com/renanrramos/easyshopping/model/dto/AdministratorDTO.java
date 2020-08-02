/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Administrator;
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
public class AdministratorDTO {
	
	private Long id;

	private String name;
	
	private Profile profile;
	
	public AdministratorDTO() {
		// Intentionally empty
	}
	
	public AdministratorDTO(Administrator administrator) {
		this.id = administrator.getId();
		this.name = administrator.getName();
		this.profile = administrator.getProfile();
	}

	public static AdministratorDTO converterAdministratorToAdministratorDTO(Administrator administrator) {
		return new AdministratorDTO(administrator);
	}

	public static List<AdministratorDTO> converterAdministratorListToAdministratorDTO(List<Administrator> administrators) {
		return administrators.stream().map(AdministratorDTO::new).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "AdministratorDTO [id=" + id + ", name=" + name + ", profile=" + profile + "]";
	}
}
