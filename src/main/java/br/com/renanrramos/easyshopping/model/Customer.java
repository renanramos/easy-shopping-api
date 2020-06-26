/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

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

	private String cpf;
	private String email;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		return true;
	}
	
	@Override
	public String toString() {
		return "Customer [cpf=" + cpf + ", email=" + email + "]";
	}
		
}
