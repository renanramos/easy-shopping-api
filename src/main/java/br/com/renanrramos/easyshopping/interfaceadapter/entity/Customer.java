/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotBlank;

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
import lombok.*;

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
public class Customer extends UserEntity {

	private static final long serialVersionUID = 7345201836941654514L;

	@EqualsAndHashCode.Include
	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String cpf;

	public Customer(String cpf) {
		this.cpf = cpf;
	}
}
