/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import br.com.renanrramos.easyshopping.enums.Profile;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author renan.ramos
 *
 */
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Customer extends User{

	private static final long serialVersionUID = 7345201836941654514L;

	public Customer() {
	}
	
	public Customer(String cpf, String email) {
		this.cpf = cpf;
		this.email = email;
	}

	@CPF
	@NotBlank
	private String cpf;
	
	@Email
	@NotBlank
	private String email;


	@ApiModelProperty(hidden = true)
	@Enumerated(EnumType.STRING)
	private Profile profile;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (profile != other.profile)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [cpf=" + cpf + ", email=" + email + ", profile=" + profile + "]";
	}
}
