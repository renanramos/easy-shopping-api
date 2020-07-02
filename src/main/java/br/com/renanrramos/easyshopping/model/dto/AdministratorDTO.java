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

/**
 * @author renan.ramos
 *
 */
public class AdministratorDTO {

	private String name;
	
	private Profile profile;
	
	public AdministratorDTO() {
	}
	
	public AdministratorDTO(Administrator administrator) {
		this.name = administrator.getName();
		this.profile = administrator.getProfile();
	}
	
	public String getName() {
		return name;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public static AdministratorDTO converterAdministratorToAdministratorDTO(Administrator administrator) {
		return new AdministratorDTO(administrator);
	}

	public static List<AdministratorDTO> converterAdministratorListToAdministratorDTO(List<Administrator> administrators) {
		return administrators.stream().map(AdministratorDTO::new).collect(Collectors.toList());
	}
	
	@Override
	public String toString() {
		return "AdministratorDTO [name=" + name + ", profile=" + profile + "]";
	}
}
