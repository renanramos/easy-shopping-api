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

import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
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
@Entity(name = "AdministratorEntity")
@PrimaryKeyJoinColumn(name="id")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class AdministratorEntity extends UserEntity {

	private static final long serialVersionUID = 8713673214822893155L;

	@ApiModelProperty(hidden = true)
	@Enumerated(EnumType.STRING)
	private Profile profile = Profile.ADMINISTRATOR;

	public AdministratorEntity() {
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "AdministratorEntity [profile=" + profile + "]";
	}
}
