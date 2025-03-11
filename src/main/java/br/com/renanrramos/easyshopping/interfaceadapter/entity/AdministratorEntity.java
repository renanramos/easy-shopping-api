/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

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
