/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

/**
 * @author renan.ramos
 *
 */
public class Company extends User {

	private String registeredNumber;
	private String phone;

	public Company() {
		// Intentionally empty
	}

	public String getRegisteredNumber() {
		return registeredNumber;
	}

	public void setRegisteredNumber(String registeredNumber) {
		this.registeredNumber = registeredNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Company [registeredNumber=" + registeredNumber + "]";
	}
}
