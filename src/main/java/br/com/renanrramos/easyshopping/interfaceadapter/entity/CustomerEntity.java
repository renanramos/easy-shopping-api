/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import jakarta.persistence.Entity;
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
@Entity
@PrimaryKeyJoinColumn(name="id")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString
@RequiredArgsConstructor
public class CustomerEntity extends UserEntity {

	private static final long serialVersionUID = 7345201836941654514L;

	@EqualsAndHashCode.Include
	private String cpf;

}
