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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import br.com.renanrramos.easyshopping.enums.Profile;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Customer extends User{

	private static final long serialVersionUID = 7345201836941654514L;

	@EqualsAndHashCode.Include
	@NotBlank
	private String cpf;
	
	@Email
	@NotBlank
	private String email;

	@ApiModelProperty(hidden = true)
	@Enumerated(EnumType.STRING)
	private Profile profile = Profile.CUSTOMER;

	@OneToMany(targetEntity = Address.class, cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.EAGER)
	private Set<Address> address;
	
	public Customer() {
		// Intentionally empty
	}
	
	public Customer(String cpf, String email) {
		this.cpf = cpf;
		this.email = email;
	}

	@Override
	public String toString() {
		return "Customer [cpf=" + cpf + ", email=" + email + ", profile=" + profile + "]";
	}
}
