/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
public class Customer extends User{

	private static final long serialVersionUID = 7345201836941654514L;

	@EqualsAndHashCode.Include
	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String cpf;

	@OneToMany(targetEntity = Address.class, cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Address> address;

	@OneToMany(targetEntity = CreditCard.class, cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<CreditCard> creditCards;

	public Customer() {
		// Intentionally empty
	}
	
	public Customer(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "Customer [cpf=" + cpf + ", address=" + address + ", name= " + getName() + "]";
	}	
}
