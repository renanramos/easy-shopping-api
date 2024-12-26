/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;

import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author renan.ramos
 *
 */
@Data
@Entity(name = "AdministratorEntity")
@PrimaryKeyJoinColumn(name="id")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@ToString
public class AdministratorEntity extends UserEntity {

	private static final long serialVersionUID = 8713673214822893155L;

	@Enumerated(EnumType.STRING)
	private Profile profile = Profile.ADMINISTRATOR;
}
