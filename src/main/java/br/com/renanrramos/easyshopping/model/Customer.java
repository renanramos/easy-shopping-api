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
public class Customer extends User{

	private static final long serialVersionUID = 7345201836941654514L;

	@EqualsAndHashCode.Include
	private String cpf;

	@OneToMany(targetEntity = Address.class, cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.EAGER)
	private Set<Address> address;
	
	public Customer() {
		// Intentionally empty
	}
	
	public Customer(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "Customer [cpf=" + cpf + "]";
	}
}
