/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotBlank;

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Company extends User implements Serializable{

	private static final long serialVersionUID = -5594496999476155657L;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String registeredNumber;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String phone;

	public Company() {		
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "Company [registeredNumber=" + registeredNumber + "]";
	}
}
