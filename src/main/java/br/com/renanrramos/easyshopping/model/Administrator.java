/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.renanrramos.easyshopping.enums.Profile;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author renan.ramos
 *
 */
@Entity
public class Administrator extends User{

	private static final long serialVersionUID = 8713673214822893155L;

	@ApiModelProperty(hidden = true)
	@Enumerated(EnumType.STRING)
	private Profile profile;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
}
