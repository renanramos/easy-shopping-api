/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

/**
 * @author renan.ramos
 *
 */
public class Customer extends User {

	private String cpf;

	public Customer() {
		// Intentionally empty
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "Customer [cpf=" + cpf + ", name= " + getName() + "]";
	}
}
