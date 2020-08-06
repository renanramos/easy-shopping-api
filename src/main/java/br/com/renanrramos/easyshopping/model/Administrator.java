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
import javax.persistence.PrimaryKeyJoinColumn;

import br.com.renanrramos.easyshopping.enums.Profile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author renan.ramos
 *
 */
@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Administrator extends User{

	private static final long serialVersionUID = 8713673214822893155L;

	@ApiModelProperty(hidden = true)
	@Enumerated(EnumType.STRING)
	private Profile profile = Profile.ADMINISTRATOR;

	public Administrator() {
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "Administrator [profile=" + profile + "]";
	}
}
